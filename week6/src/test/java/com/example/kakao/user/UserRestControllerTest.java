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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@DisplayName("User 테스트")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("회원가입 테스트")
    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO request = new UserRequest.JoinDTO();
        request.setEmail("test@kakao.com");
        request.setPassword("test1234!");
        request.setUsername("testaccount");
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("실패 테스트 : 이메일 형식 불일치")
    @Test
    public void join_fail_test() throws Exception {
        // given
        UserRequest.JoinDTO request = new UserRequest.JoinDTO();
        request.setEmail("testkakao.com");
        request.setPassword("test1234!");
        request.setUsername("testaccount");
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("실패 테스트 : 유저 이름 길이 조건 불만족")
    @Test
    public void join_fail_test2() throws Exception {
        // given
        UserRequest.JoinDTO request = new UserRequest.JoinDTO();
        request.setEmail("test@kakao.com");
        request.setPassword("test1234!");
        request.setUsername("test");
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("실패 테스트 : 비밀번호 최소 길이 불만족")
    @Test
    public void join_fail_test3() throws Exception {
        // given
        UserRequest.JoinDTO request = new UserRequest.JoinDTO();
        request.setEmail("test@kakao.com");
        request.setPassword("test12!");
        request.setUsername("testaccount");
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("실패 테스트 : 비밀번호 특수문자 없음")
    @Test
    public void join_fail_test4() throws Exception {
        // given
        UserRequest.JoinDTO request = new UserRequest.JoinDTO();
        request.setEmail("test@kakao.com");
        request.setPassword("test1234");
        request.setUsername("testaccount");
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("실패 테스트 : 이메일 중복")
    @Test
    public void join_fail_test5() throws Exception {
        // given
        UserRequest.JoinDTO request = new UserRequest.JoinDTO();
        request.setEmail("ssarmango@nate.com");
        request.setPassword("test1234!");
        request.setUsername("testaccount");
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("로그인 테스트")
    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO request = new UserRequest.LoginDTO();
        request.setEmail("ssarmango@nate.com");
        request.setPassword("meta1234!");
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + responseHeader);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("실패 테스트 : 존재하지 않는 이메일")
    @Test
    public void login_fail_test() throws Exception {
        // given
        UserRequest.LoginDTO request = new UserRequest.LoginDTO();
        request.setEmail("ssar@nate.com");
        request.setPassword("meta1234!");
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + responseHeader);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("실패 테스트 : 이메일 형식 불일치")
    @Test
    public void login_fail_test1() throws Exception {
        // given
        UserRequest.LoginDTO request = new UserRequest.LoginDTO();
        request.setEmail("ssarmangonate.com");
        request.setPassword("meta1234!");
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + responseHeader);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("실패 테스트 : 비밀번호 길이 조건 불만족")
    @Test
    public void login_fail_test2() throws Exception {
        // given
        UserRequest.LoginDTO request = new UserRequest.LoginDTO();
        request.setEmail("ssarmango@nate.com");
        request.setPassword("meta12!");
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + responseHeader);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("실패 테스트 : 비밀번호 특수문자 조건 불만족")
    @Test
    public void login_fail_test3() throws Exception {
        // given
        UserRequest.LoginDTO request = new UserRequest.LoginDTO();
        request.setEmail("ssarmango@nate.com");
        request.setPassword("meta1234");
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + responseHeader);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("실패 테스트 : 비밀번호 불일치")
    @Test
    public void login_fail_test4() throws Exception {
        // given
        UserRequest.LoginDTO request = new UserRequest.LoginDTO();
        request.setEmail("ssarmango@nate.com");
        request.setPassword("meta1233!");
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + responseHeader);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
