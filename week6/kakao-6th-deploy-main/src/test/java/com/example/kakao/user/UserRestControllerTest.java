package com.example.kakao.user;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    @DisplayName("회원가입 성공 테스트")
    @Test
    public void join_success_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("asdf1234@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("asdf1234");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("join_success_test : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("회원가입 실패 - 중복된 이메일")
    @Test
    public void join_fail_duplicated_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
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
        System.out.println("join_fail_duplicated_test : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("회원가입 실패 - 특수문자 없는 패스워드")
    @Test
    public void join_fail_invalid_password_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("qwer1234@nate.com");
        requestDTO.setPassword("meta1234");
        requestDTO.setUsername("qwer1234");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("join_fail_invalid_password_test : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @DisplayName("회원가입 실패 - 짧은 패스워드")
    @Test
    public void join_fail_too_short_password_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("asdfqwer@nate.com");
        requestDTO.setPassword("m1!");
        requestDTO.setUsername("asdfqwer");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("join_fail_too_short_password_test : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @DisplayName("회원가입 실패 - 짧은 사용자 이름")
    @Test
    public void join_fail_invalid_username_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("asdf@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("asdf");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("join_fail_invalid_username_test : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 45자 이내여야 합니다.:username"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @DisplayName("회원가입 실패 - 잘못된 형식의 이메일")
    @Test
    public void join_fail_invalid_email_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("asdfqasdf");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("asdfqasdf");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("join_fail_invalid_email_test : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @DisplayName("로그인 성공 테스트")
    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("login_test : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("로그인 실패 - 존재하지 않는 사용자")
    @Test
    public void login_fail_invalid_user_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("mangomango@nate.com");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("login_fail_invalid_user_test : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : mangomango@nate.com"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @DisplayName("로그인 실패 - 잘못된 이메일 형식")
    @Test
    public void login_fail_invalid_email_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("mangomango");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("login_fail_invalid_email_test : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @DisplayName("로그인 실패 - 길이가 짧은 패스워드")
    @Test
    public void login_fail_too_short_password_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarhello@nate.com");
        loginDTO.setPassword("m1!");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("login_fail_too_short_password_test : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @DisplayName("로그인 실패 - 특수문자가 없는 패스워드")
    @Test
    public void login_fail_invalid_password_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarhello@nate.com");
        loginDTO.setPassword("meta1234");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("login_fail_invalid_password_test : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}
