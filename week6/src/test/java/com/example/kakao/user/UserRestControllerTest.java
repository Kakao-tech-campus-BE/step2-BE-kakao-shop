package com.example.kakao.user;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    @Test
    public void join_test() throws Exception {
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setUsername("ssarmango");
        joinDTO.setEmail("gobeumsu@gmail.com");
        joinDTO.setPassword("meta123!");
        String requestBody = om.writeValueAsString(joinDTO);
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Join Test: " + responseBody);
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response").isEmpty());
        resultActions.andExpect(jsonPath("$.error").isEmpty());
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("회원 가입 실패 예시 1")
    @Test
    public void join_test_wrong_email() throws Exception {
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setUsername("ssarmango");
        joinDTO.setEmail("gobeumsugmail.com");
        joinDTO.setPassword("meta123!");
        String requestBody = om.writeValueAsString(joinDTO);
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Join Test Wrong Email: " + responseBody);
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("회원 가입 실패 예시 2")
    @Test
    public void join_test_wrong_format_password() throws Exception {
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setUsername("ssarmango");
        joinDTO.setEmail("gobeumsu@gmail.com");
        joinDTO.setPassword("meta1234");
        String requestBody = om.writeValueAsString(joinDTO);
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Join Test Wrong Email: " + responseBody);
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").isEmpty());
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("회원 가입 실패 예시 3")
    @Test
    public void join_test_same_email() throws Exception {
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setUsername("metamata");
        joinDTO.setEmail("ssarmango@nate.com");
        joinDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(joinDTO);
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Join Test Same Email: " + responseBody);
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").isEmpty());
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("회원 가입 실패 예시 4")
    @Test
    public void join_test_wrong_password() throws Exception {
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setUsername("ssarmango");
        joinDTO.setEmail("gobeumsu@gmail.com");
        joinDTO.setPassword("meta1!");
        String requestBody = om.writeValueAsString(joinDTO);
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Join Test Wrong Password: " + responseBody);
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").isEmpty());
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_test() throws Exception {
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Login Test: " + responseBody);
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response").isEmpty());
        resultActions.andExpect(jsonPath("$.error").isEmpty());

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("로그인 실패 예시 1")
    @Test
    public void login_test_invalid_email() throws Exception {
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("invalidemail");
        loginDTO.setPassword("ValidPassword123!");
        String requestBody = om.writeValueAsString(loginDTO);
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").isEmpty());
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("로그인 실패 예시 2")
    @Test
    public void login_test_invalid_format_password() throws Exception {
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("invalidpassword");
        String requestBody = om.writeValueAsString(loginDTO);
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").isEmpty());
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    //
//    @DisplayName("로그인 실패 예시 3")
//    @Test
//    public void login_test_invalid_email_password() throws Exception {
//        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
//        loginDTO.setEmail("ssarmango@nate.com");
//        loginDTO.setPassword("meta1234!");
//        String requestBody = om.writeValueAsString(loginDTO);
//        ResultActions resultActions = mvc.perform(
//                post("/login")
//                        .content(requestBody)
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//        resultActions.andExpect(jsonPath("$.success").value("false"));
//        resultActions.andExpect(jsonPath("$.response").isEmpty());
//        resultActions.andExpect(jsonPath("$.error.status").value(401));
//        resultActions.andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"));
//
//        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
//    }
    @DisplayName("로그인 실패 예시 4")
    @Test
    public void login_test_invalid_length_password() throws Exception {
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("inva12!");
        String requestBody = om.writeValueAsString(loginDTO);
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").isEmpty());
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
