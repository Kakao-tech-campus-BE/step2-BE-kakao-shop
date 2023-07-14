package com.example.kakao.integration;

import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.example.kakao.user.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc{
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
    }

    @Test
    // (기능1) 회원가입
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setEmail("cos@nate.com");
        joinDTO.setPassword("cos1234!");
        joinDTO.setUsername("cos");

        String requestBody = om.writeValueAsString(joinDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 회원가입 실패 테스트 코드 추가
    @Test
    public void join_fail_test_1() throws Exception {
        // given
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setEmail("cosnate.com");
        joinDTO.setPassword("cos1234!");
        joinDTO.setUsername("cos");

        String requestBody = om.writeValueAsString(joinDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 회원가입 실패 테스트 코드 추가
    @Test
    public void join_fail_test_2() throws Exception {
        // given
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setEmail("cos@nate.com");
        joinDTO.setPassword("cos12345");
        joinDTO.setUsername("cos");

        String requestBody = om.writeValueAsString(joinDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 회원가입 실패 테스트 코드 추가
    @Test
    public void join_fail_test_3() throws Exception {
        // given
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setEmail("ssar@nate.com");
        joinDTO.setPassword("cos1234!");
        joinDTO.setUsername("cos");

        String requestBody = om.writeValueAsString(joinDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssar@nate.com"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_fail_test_4() throws Exception {
        // given
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setEmail("cos@nate.com");
        joinDTO.setPassword("meta12!");
        joinDTO.setUsername("cos");

        String requestBody = om.writeValueAsString(joinDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // (기능2) 로그인
    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));


        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 로그인 실패 테스트 코드 추가
    @Test
    public void login_fail_test_1() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarnate.com");
        loginDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));


        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 로그인 실패 테스트 코드 추가
    @Test
    public void login_fail_test_2() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1234");

        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 로그인 실패 테스트 코드 추가
    @Test
    public void login_fail_test_3() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar1@nate.com");
        loginDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"));
        resultActions.andExpect(jsonPath("$.error.status").value(401));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 로그인 실패 테스트 코드 추가
    @Test
    public void login_fail_test_4() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("mete12!");

        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void check_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO emailCheckDTO = new UserRequest.EmailCheckDTO();
        emailCheckDTO.setEmail("cos@nate.com");

        String requestBody = om.writeValueAsString(emailCheckDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response").value(nullValue()));
        resultActions.andExpect(jsonPath("$.error").value(nullValue()));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @Test
    public void check_fail_test_1() throws Exception {
        // given
        UserRequest.EmailCheckDTO emailCheckDTO = new UserRequest.EmailCheckDTO();
        emailCheckDTO.setEmail("ssar@nate.com");

        String requestBody = om.writeValueAsString(emailCheckDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssar@nate.com"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void check_fail_test_2() throws Exception {
        // given
        UserRequest.EmailCheckDTO emailCheckDTO = new UserRequest.EmailCheckDTO();
        emailCheckDTO.setEmail("codenate.com");

        String requestBody = om.writeValueAsString(emailCheckDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void admin_login_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("admin@nate.com");
        loginDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));


        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
