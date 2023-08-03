package com.example.kakao.user;

import com.example.kakao.MyRestDoc;
import com.example.kakao._core.security.JWTProvider;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserRestControllerTest extends MyRestDoc {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @DisplayName("회원가입 성공")
    @Test
    void join_success_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("xcelxlorx@nate.com");
        requestDTO.setPassword("xcelxlorx1234!");
        requestDTO.setUsername("xcelxlorx");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(jsonPath("$.success").value(true));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("회원가입 실패 - 이메일 형식 오류")
    @Test
    void join_fail_test1() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("xcelxlorxnate.com");
        requestDTO.setPassword("xcelxlorx1234!");
        requestDTO.setUsername("xcelxlorx");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("회원가입 실패 - 비밀번호 형식 오류")
    @Test
    void join_fail_test2() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("xcelxlorx@nate.com");
        requestDTO.setPassword("xcelxlorx1234");
        requestDTO.setUsername("xcelxlorx");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("회원가입 실패 - 이름 길이 오류")
    @Test
    void join_fail_test3() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("xcelxlorx@nate.com");
        requestDTO.setPassword("xcelxlorx1234!");
        requestDTO.setUsername("xx");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("8에서 45자 이내여야 합니다.:username"));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("회원가입 실패 - 비밀번호 길이 오류")
    @Test
    void join_fail_test4() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("xcelxlorx@nate.com");
        requestDTO.setPassword("x12!");
        requestDTO.setUsername("xcelxlorx");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("로그인 성공")
    @Test
    void login_success_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions result = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("responseBody=" + responseBody);
        System.out.println("responseHeader=" + responseHeader);

        // then
        result.andExpect(jsonPath("$.success").value(true));
        assertTrue(responseHeader.startsWith(JWTProvider.TOKEN_PREFIX));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("로그인 실패 - 비밀번호 오류")
    @Test
    void login_fail_test1() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("ssar1234!");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions result = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("responseBody=" + responseBody);
        System.out.println("responseHeader=" + responseHeader);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("패스워드가 잘못입력되었습니다."));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("로그인 실패 - 이메일 형식 오류")
    @Test
    void login_fail_test2() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmangonate.com");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions result = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("responseBody=" + responseBody);
        System.out.println("responseHeader=" + responseHeader);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("로그인 실패 - 비밀번호 형식 오류")
    @Test
    void login_fail_test3() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta1234");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions result = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("responseBody=" + responseBody);
        System.out.println("responseHeader=" + responseHeader);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("로그인 실패 - 이메일 없음")
    @Test
    void login_fail_test4() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("xcelxlorx@nate.com");
        loginDTO.setPassword("xcelxlorx1234!");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions result = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("responseBody=" + responseBody);
        System.out.println("responseHeader=" + responseHeader);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : xcelxlorx@nate.com"));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("로그인 실패 - 비밀번호 길이 오류")
    @Test
    void login_fail_test5() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta12!");
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions result = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("responseBody=" + responseBody);
        System.out.println("responseHeader=" + responseHeader);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("이메일 중복 확인 성공")
    @Test
    void checkEmail_success_test() throws Exception {
        //given
        UserRequest.EmailCheckDTO checkEmailDTO = new UserRequest.EmailCheckDTO();
        checkEmailDTO.setEmail("xcelxlorx@nate.com");
        String requestBody = om.writeValueAsString(checkEmailDTO);

        //when
        ResultActions result = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("이메일 중복 확인 실패 - 동일한 이메일 존재")
    @Test
    void checkEmail_fail_test1() throws Exception {
        //given
        UserRequest.EmailCheckDTO checkEmailDTO = new UserRequest.EmailCheckDTO();
        checkEmailDTO.setEmail("ssarmango@nate.com");
        String requestBody = om.writeValueAsString(checkEmailDTO);

        //when
        ResultActions result = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("이메일 중복 확인 실패 - 이메일 형식 오류")
    @Test
    void checkEmail_fail_test2() throws Exception {
        //given
        UserRequest.EmailCheckDTO checkEmailDTO = new UserRequest.EmailCheckDTO();
        checkEmailDTO.setEmail("xcelxlorxnate.com");
        String requestBody = om.writeValueAsString(checkEmailDTO);

        //when
        ResultActions result = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요.:email"));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }
}