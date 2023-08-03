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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("이메일 중복 체크 테스트")
    public void emailCheck_test() throws Exception {
        //given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("ikyeong@naver.com");
        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(post("/check")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("이메일 중복 체크 실패 테스트(1) - 동일 이메일 존재 오류")
    public void emailCheck_fail_test1() throws Exception {
        //given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(post("/check")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("이메일 중복 체크 실패 테스트(2) - 이메일 형식 오류")
    public void emailCheck_fail_test2() throws Exception {
        //given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("ssarmangonate.com");
        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(post("/check")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void join_test() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ikyeong@naver.com");
        requestDTO.setPassword("password1234!");
        requestDTO.setUsername("kimikyeong");
        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("회원가입 실패 테스트(1) - 이메일 형식 오류")
    public void join_fail_test1() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ikyeongnaver.com");
        requestDTO.setPassword("password1234!");
        requestDTO.setUsername("kimikyeong");
        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("회원가입 실패 테스트(2) - 동일 이메일 존재 오류")
    public void join_fail_test2() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("password1234!");
        requestDTO.setUsername("kimikyeong");
        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("회원가입 실패 테스트(3) - 비밀번호 형식 오류")
    public void join_fail_test3() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ikyeong@naver.com");
        requestDTO.setPassword("password1234");
        requestDTO.setUsername("kimikyeong");
        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("회원가입 실패 테스트(4) - 비밀번호 길이 오류")
    public void join_fail_test4() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@naver.com");
        requestDTO.setPassword("a1!");
        requestDTO.setUsername("kimikyeong");
        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("로그인 테스트")
    public void login_test() throws Exception {
        //given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("로그인 실패 테스트(1) - 이메일 찾기 오류")
    public void login_fail_test1() throws Exception {
        //given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango1@nate.com");
        requestDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : ssarmango1@nate.com"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("로그인 실패 테스트(2) - 비밀번호 오류")
    public void login_fail_test2() throws Exception {
        //given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!!");
        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("패스워드가 잘못입력되었습니다 "));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}
