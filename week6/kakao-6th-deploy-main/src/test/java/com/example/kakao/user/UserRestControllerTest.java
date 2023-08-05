package com.example.kakao.cart;

import com.example.kakao.MyRestDoc;
import com.example.kakao.user.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    public void emailCheck_test() throws Exception {

        UserRequest.EmailCheckDTO request = new UserRequest.EmailCheckDTO();
        request.setEmail("ssar@nate.com");

        String requestBody = om.writeValueAsString(request);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void emailCheckFail_test() throws Exception {
        UserRequest.JoinDTO joinrequest = new UserRequest.JoinDTO();
        joinrequest.setPassword("ssar1234!");
        joinrequest.setUsername("ssar1234");
        joinrequest.setEmail("ssar@nate.com");

        String joinRequestBody = om.writeValueAsString(joinrequest);

        UserRequest.EmailCheckDTO request = new UserRequest.EmailCheckDTO();
        request.setEmail("ssar@nate.com");

        String requestBody = om.writeValueAsString(request);

        // when
        mvc.perform(
                post("/join")
                        .content(joinRequestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );


        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void emailCheckFail_atMiss_test() throws Exception {

        UserRequest.EmailCheckDTO request = new UserRequest.EmailCheckDTO();
        request.setEmail("ssarnate.com");

        String requestBody = om.writeValueAsString(request);

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
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_test() throws Exception {
        UserRequest.JoinDTO request = new UserRequest.JoinDTO();
        request.setPassword("ssar1234!");
        request.setUsername("ssar1234");
        request.setEmail("ssar@nate.com");

        String requestBody = om.writeValueAsString(request);

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

    @Test
    public void joinFail_atMiss_test() throws Exception {
        UserRequest.JoinDTO request = new UserRequest.JoinDTO();
        request.setPassword("ssar1234!");
        request.setUsername("ssar1234");
        request.setEmail("ssarnate.com");

        String requestBody = om.writeValueAsString(request);

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
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void joinFail_passwordError_test() throws Exception {
        UserRequest.JoinDTO request = new UserRequest.JoinDTO();
        request.setPassword("ssar1234");
        request.setUsername("ssar1234");
        request.setEmail("ssar@nate.com");

        String requestBody = om.writeValueAsString(request);

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
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void joinFail_emailCheckFail_test() throws Exception {
        UserRequest.JoinDTO joinrequest = new UserRequest.JoinDTO();
        joinrequest.setPassword("ssar1234!");
        joinrequest.setUsername("ssar1234");
        joinrequest.setEmail("ssar@nate.com");

        String joinRequestBody = om.writeValueAsString(joinrequest);

        UserRequest.JoinDTO request = new UserRequest.JoinDTO();
        request.setPassword("ssar1234!");
        request.setUsername("ssar1234");
        request.setEmail("ssar@nate.com");

        String requestBody = om.writeValueAsString(request);

        // when
        mvc.perform(
                post("/join")
                        .content(joinRequestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void joinFail_passwordLengthError_test() throws Exception {
        UserRequest.JoinDTO request = new UserRequest.JoinDTO();
        request.setPassword("ssar");
        request.setUsername("ssar1234");
        request.setEmail("ssar@nate.com");

        String requestBody = om.writeValueAsString(request);

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
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_test() throws Exception {
        UserRequest.JoinDTO joinRequest = new UserRequest.JoinDTO();
        joinRequest.setPassword("ssar1234!");
        joinRequest.setUsername("ssar1234");
        joinRequest.setEmail("ssar@nate.com");

        String joinRequestBody = om.writeValueAsString(joinRequest);

        UserRequest.LoginDTO request = new UserRequest.LoginDTO();
        request.setPassword("ssar1234!");
        request.setEmail("ssar@nate.com");

        String requestBody = om.writeValueAsString(request);

        // when
        mvc.perform(
                post("/join")
                        .content(joinRequestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

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

    @Test
    public void loginFail_atMiss_test() throws Exception {
        UserRequest.JoinDTO joinRequest = new UserRequest.JoinDTO();
        joinRequest.setPassword("ssar1234!");
        joinRequest.setUsername("ssar1234");
        joinRequest.setEmail("ssar@nate.com");

        String joinRequestBody = om.writeValueAsString(joinRequest);

        UserRequest.LoginDTO request = new UserRequest.LoginDTO();
        request.setPassword("ssar1234!");
        request.setEmail("ssarnate.com");

        String requestBody = om.writeValueAsString(request);

        // when
        mvc.perform(
                post("/join")
                        .content(joinRequestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void loginFail_passwordError_test() throws Exception {
        UserRequest.JoinDTO joinRequest = new UserRequest.JoinDTO();
        joinRequest.setPassword("ssar1234!");
        joinRequest.setUsername("ssar1234");
        joinRequest.setEmail("ssar@nate.com");

        String joinRequestBody = om.writeValueAsString(joinRequest);

        UserRequest.LoginDTO request = new UserRequest.LoginDTO();
        request.setPassword("ssar1234");
        request.setEmail("ssar@nate.com");

        String requestBody = om.writeValueAsString(request);

        // when
        mvc.perform(
                post("/join")
                        .content(joinRequestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void loginFail_PasswordLength_test() throws Exception {
        UserRequest.JoinDTO joinRequest = new UserRequest.JoinDTO();
        joinRequest.setPassword("ssar1234!");
        joinRequest.setUsername("ssar1234");
        joinRequest.setEmail("ssar@nate.com");

        String joinRequestBody = om.writeValueAsString(joinRequest);

        UserRequest.LoginDTO request = new UserRequest.LoginDTO();
        request.setPassword("ssar1");
        request.setEmail("ssar@nate.com");

        String requestBody = om.writeValueAsString(request);

        // when
        mvc.perform(
                post("/join")
                        .content(joinRequestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}

