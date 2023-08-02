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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;

    @DisplayName("이메일 중복 테스트")
    @Test
    public void checkEmail_test() throws Exception {
        //given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("hanju@naver.com");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("이메일 중복 실패 테스트")
    @Test
    public void checkEmailError_test() throws Exception {
        //given
        String email = "ssarmango@nate.com";

        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail(email);

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : " + email))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("회원가입 테스트")
    @Test
    public void join_test() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("hanju@naver.com");
        requestDTO.setPassword("hanju1234!");
        requestDTO.setUsername("testhanju");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        //console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @DisplayName("회원가입 실패 테스트 - 이메일 형식")
    @Test
    public void joinCheckEmailError_test() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("hanjunaver.com");
        requestDTO.setPassword("hanju1234!");
        requestDTO.setUsername("testhanju");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        //console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"))
                .andExpect(jsonPath("$.error.status").value("400"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("회원가입 실패 테스트 - password 형식(영문, 숫자, 특수기호)")
    @Test
    public void joinCheckWrongPasswordError_test() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("hanju@naver.com");
        requestDTO.setPassword("hanju1234");
        requestDTO.setUsername("testhanju");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        //console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"))
                .andExpect(jsonPath("$.error.status").value("400"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("회원가입 실패 테스트 - username(8~20자)")
    @Test
    public void joinCheckWrongUsernameError_test() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("hanju1234!");
        requestDTO.setUsername("test");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        //console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.error.message").value("8에서 45자 이내여야 합니다.:username"))
                .andExpect(jsonPath("$.error.status").value("400"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("로그인 테스트")
    @Test
    public void login_test() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        //console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("로그인 실패 테스트 - 이메일 형식")
    @Test
    public void loginEmailError_test() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmangonate.com");
        requestDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        //console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"))
                .andExpect(jsonPath("$.error.status").value("400"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("로그인 실패 테스트 - 인증 실패")
    @Test
    public void loginPasswordError_test() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango1@nate.com");
        requestDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        //console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : ssarmango1@nate.com"))
                .andExpect(jsonPath("$.error.status").value("400"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }


}
