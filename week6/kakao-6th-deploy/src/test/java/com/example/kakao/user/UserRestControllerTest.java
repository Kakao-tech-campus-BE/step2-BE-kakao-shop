package com.example.kakao.user;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    // 동일한 이메일이 존재하는 경우
    @Test
    public void emailCheck_test() throws Exception {
        // given
        String email = "ssarmango@nate.com";
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail(email);

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : " + email));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 회원가입 성공
    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("testuser@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("testuser");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 로그인 성공
    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 가입되지 않은 이메일
    @Test
    public void emailNotFound_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("testuser@nate.com");
        requestDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : " + requestDTO.getEmail()));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 비밀번호가 일치하지 않는 경우
    @Test
    public void passwordNotMatch_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234@");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("비밀번호가 일치하지 않습니다"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // join - 이메일 형식이 올바르지 않을 경우
    @Test
    public void wrongEmail_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmangonate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssarmango");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // join - 비밀번호 형식이 올바르지 않을 경우
    @Test
    public void wrongPassword_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234");
        requestDTO.setUsername("ssarmango");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // join - 비밀번호 글자수 제한
    @Test
    public void wrongPasswordLength_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta12!");
        requestDTO.setUsername("ssarmango");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
