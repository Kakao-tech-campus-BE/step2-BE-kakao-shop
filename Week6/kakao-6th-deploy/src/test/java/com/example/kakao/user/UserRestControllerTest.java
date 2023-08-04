package com.example.kakao.user;

import com.example.kakao.MyRestDoc;
import com.example.kakao._core.security.JWTProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @Test
    public void check_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("ssar@nate.com");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void check_fail_test_email_overlap() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("ssarmango@nate.com");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void check_fail_test_email_error() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("ssarmangonate.com");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssar1234@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssar1234");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_fail_test_email_error1() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssar1234nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssar1234");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_fail_test_email_error2() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssar1234@natecom");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssar1234");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_fail_test_password_error1() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssar1234@nate.com");
        requestDTO.setPassword("meta1!");
        requestDTO.setUsername("ssar1234");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_fail_test_password_error2() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssar1234@nate.com");
        requestDTO.setPassword("meta1234");
        requestDTO.setUsername("ssar1234");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야 하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_fail_test_username_error() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssar1234@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssar");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("8에서 45자 이내여야 합니다.:username"));
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
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + responseHeader);

        // then
        Assertions.assertTrue(responseHeader.startsWith(JWTProvider.TOKEN_PREFIX));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_fail_test_email_error1() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarnate.com");
        loginDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(loginDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_fail_test_email_error2() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@natecom");
        loginDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(loginDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_fail_test_password_error1() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1!");

        String requestBody = om.writeValueAsString(loginDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_fail_test_password_error2() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1234");

        String requestBody = om.writeValueAsString(loginDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_fail_test_email_not_found() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(loginDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : ssar@nate.com"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_fail_test_password_wrong() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta1234!!");

        String requestBody = om.writeValueAsString(loginDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("비밀번호가 잘못 입력되었습니다 "));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}

