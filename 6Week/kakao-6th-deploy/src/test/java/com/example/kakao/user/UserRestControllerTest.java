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
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {
    private final ObjectMapper om;

    @Autowired
    public UserRestControllerTest(ObjectMapper om) {
        this.om = om;
    }

    @DisplayName("회원가입 테스트")
    @Test
    public void join_test() throws Exception {
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango2@nate.com");
        requestDTO.setPassword("meta1234!@");
        requestDTO.setUsername("ssarmango2");
        String requestBody = om.writeValueAsString(requestDTO);

        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("로그인 테스트")
    @Test
    public void login_test() throws Exception {
        //given teardown
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
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("이메일 중복 체크 테스트")
    @Test
    public void check_test() throws Exception{
        UserRequest.EmailCheckDTO emailCheckDTO = new UserRequest.EmailCheckDTO();
        emailCheckDTO.setEmail("ssarmango2@nate.com");
        String requestBody = om.writeValueAsString(emailCheckDTO);

        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("이메일 중복 체크 실패 테스트")
    @Test
    public void check_fail_test() throws Exception{
        UserRequest.EmailCheckDTO emailCheckDTO = new UserRequest.EmailCheckDTO();
        emailCheckDTO.setEmail("ssarmango@nate.com");
        String requestBody = om.writeValueAsString(emailCheckDTO);

        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : " + emailCheckDTO.getEmail()))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("로그인 시 이메일 조회 실패 테스트")
    @Test
    public void login_findEmail_fail_test() throws Exception{
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango2@nate.com");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);

        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : "+loginDTO.getEmail()))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("로그인 시 패스워드 실패 테스트")
    @Test
    public void login_password_fail_test() throws Exception{
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta1234!@");
        String requestBody = om.writeValueAsString(loginDTO);

        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("패스워드가 잘못입력되었습니다"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("회원가입 시 이메일 유효성 검사 실패 테스트")
    @Test
    public void join_validEmail_fail_test() throws Exception{
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmangonate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssarmango");
        String requestBody = om.writeValueAsString(requestDTO);

        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("회원가입 시 패스워드 size 유효성 검사 실패 테스트")
    @Test
    public void join_validPasswordSize_fail_test() throws Exception{
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("me1!");
        requestDTO.setUsername("ssarmango");
        String requestBody = om.writeValueAsString(requestDTO);

        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("회원가입 시 패스워드 유효성 검사 실패 테스트")
    @Test
    public void join_validPassword_fail_test() throws Exception{
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234");
        requestDTO.setUsername("ssarmango");
        String requestBody = om.writeValueAsString(requestDTO);

        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("회원가입 시 유저 이름 유효성 검사 실패 테스트")
    @Test
    public void join_validUsername_fail_test() throws Exception{
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssar");
        String requestBody = om.writeValueAsString(requestDTO);

        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("8에서 45자 이내여야 합니다.:username"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("로그인 시 이메일 유효성 검사 실패 테스트")
    @Test
    public void login_validEmail_fail_test() throws Exception{
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmangonate.com");
        loginDTO.setPassword("meta1234!@");
        String requestBody = om.writeValueAsString(loginDTO);

        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("로그인 시 패스워드 size 유효성 검사 실패 테스트")
    @Test
    public void login_validPasswordSize_fail_test() throws Exception{
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("me1!");
        requestDTO.setUsername("ssarmango");
        String requestBody = om.writeValueAsString(requestDTO);

        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("로그인 시 패스워드 유효성 검사 실패 테스트")
    @Test
    public void login_validPassword_fail_test() throws Exception{
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234");
        requestDTO.setUsername("ssarmango");
        String requestBody = om.writeValueAsString(requestDTO);

        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("이메일 중복 확인 시 이메일 유효성 검사 실패 테스트")
    @Test
    public void check_validEmail_fail_test() throws Exception{
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmangonate.com");
        requestDTO.setPassword("meta1234");
        requestDTO.setUsername("ssarmango");
        String requestBody = om.writeValueAsString(requestDTO);

        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}
