package com.example.kakao.user;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.matchesPattern;


@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;


    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("metamango@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("metamango");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_withSameEmail_test() throws Exception {
        // given
        String email = "ssarmango@nate.com";
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail(email);
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("metamango");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : " + email));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_withWrongPatternEmail_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("metamangonate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("metamango");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_withWrongPatternPassword_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("metamango@nate.com");
        requestDTO.setPassword("meta1234"); // 특수문자가 없는 경우
        requestDTO.setUsername("metamango");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_withWrongSizePassword_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("metamango@nate.com");
        requestDTO.setPassword("meta1234@sdfasdfasdfasdasdfasdf");
        // 지나치게 긴 경우 -> 문제가 하나 있다. 길이가 길고 특수문자도 없는 경우 둘 다 지적해야 하는데 둘 중 하나를 랜덤으로 지적한다.
        // 그래서 globalValidationHandler를 좀 손봤다.
        requestDTO.setUsername("metamango");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_withWrongSizeUsername_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("metamango@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("metamangoasdfasfhaskjdfhlasdhfaskjfklsadjfaklsfjd"); // 지나치게 긴 경우
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("8에서 45자 이내여야 합니다.:username"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_withMultipleErrors_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("metamangonate.com"); // 잘못된 형식인 경우
        requestDTO.setPassword("meta123423423432432434234234234324324234"); // 지나치게 길고 특수문자가 없는 경우
        requestDTO.setUsername("metamangoasdfasfhaskjdfhlasdhfaskjfklsadjfaklsfjd"); // 지나치게 긴 경우
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        String regexPattern = "^(?=.*8에서 20자 이내여야 합니다.:password|"
                + ".*이메일 형식으로 작성해주세요:email|"
                + ".*영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password|"
                + ".*8에서 45자 이내여야 합니다.:username)"
                + "(?!.*\\1)(?:.*?(,\\s*)?\2?){4}$";

/*        String inputString = "8에서 20자 이내여야 합니다.:password, 8에서 45자 이내여야 합니다.:username, 영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password, 이메일 형식으로 작성해주세요:email";

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(inputString);
        System.out.println("---->" + matcher.matches());*/

        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message", matchesPattern(regexPattern)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);


        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_withWrongPassword_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta1234!wrong");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);


        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("패스워드가 잘못입력되었습니다"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_withWrongEmail_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        String email = "abcdefgh@nate.com";
        loginDTO.setEmail(email);
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);


        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : " + email));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_withWrongPatternEmail_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        String email = "ssarmangonate.com";
        loginDTO.setEmail(email);
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);


        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_withWrongPatternPassword_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta 1234!"); //공백이 있는 경우
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);


        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_withWrongSizePassword_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta@1234asdfasdfasdfasdfasdfasdfasdf"); //지나치게 긴 경우
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);


        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
