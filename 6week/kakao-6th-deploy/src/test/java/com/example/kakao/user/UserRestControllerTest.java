package com.example.kakao.user;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.HeaderResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
class UserRestControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    @Test
    void check_success_test() throws Exception {
        // given teardown.sql

        UserRequest.EmailCheckDTO emailCheckDTO = new UserRequest.EmailCheckDTO();
        emailCheckDTO.setEmail("sonny1234@nate.com");

        String requestBody = om.writeValueAsString(emailCheckDTO);
        System.out.println("테스트 : " + requestBody);


        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify

        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @Test
    void check_fail_test() throws Exception {
        // given teardown.sql

        UserRequest.EmailCheckDTO emailCheckDTO = new UserRequest.EmailCheckDTO();
        emailCheckDTO.setEmail("ssarmango@nate.com");

        String requestBody = om.writeValueAsString(emailCheckDTO);
        System.out.println("테스트 : " + requestBody);


        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify

        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @Test
    void join_success_test() throws Exception {
        // given teardown.sql

        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setEmail("sonny1234@nate.com");
        joinDTO.setUsername("sonnyrara");
        joinDTO.setPassword("sonnyrara1234@");

        String requestBody = om.writeValueAsString(joinDTO);
        System.out.println("테스트 : " + requestBody);


        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify

        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    void join_fail_test_username() throws Exception {
        // given teardown.sql

        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setEmail("sonny1234@nate.com");
        joinDTO.setUsername("sonny");
        joinDTO.setPassword("sonny1234@");

        String requestBody = om.writeValueAsString(joinDTO);
        System.out.println("테스트 : " + requestBody);


        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify

        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 45자 이내여야 합니다.:username"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    void join_fail_test_email() throws Exception {
        // given teardown.sql

        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setEmail("sonny1234nate.com");
        joinDTO.setUsername("sonnyaaaa");
        joinDTO.setPassword("sonny1234@");

        String requestBody = om.writeValueAsString(joinDTO);
        System.out.println("테스트 : " + requestBody);


        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify

        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    void join_fail_test_pw() throws Exception {
        // given teardown.sql

        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setEmail("sonny1234@nate.com");
        joinDTO.setUsername("sonnyaaaa");
        joinDTO.setPassword("sonny1234");

        String requestBody = om.writeValueAsString(joinDTO);
        System.out.println("테스트 : " + requestBody);


        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify

        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    void login_success_test() throws Exception {

        // given teardown.sql

        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(loginDTO);
        System.out.println("테스트 : " + requestBody);


        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        Object value = resultActions.andReturn().getResponse().getHeaderValue("Authorization");
        System.out.println("테스트 : " + value);
        System.out.println("테스트 : " + responseBody);

        // verify

        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(header().string("Authorization", (String) value));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    void login_fail_test_id() throws Exception {

        // given teardown.sql

        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmangoa@nate.com");
        loginDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(loginDTO);
        System.out.println("테스트 : " + requestBody);


        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        Object value = resultActions.andReturn().getResponse().getHeaderValue("Authorization");
        System.out.println("테스트 : " + value);
        System.out.println("테스트 : " + responseBody);

        // verify

        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : ssarmangoa@nate.com"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    void login_fail_test_pw() throws Exception {

        // given teardown.sql

        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta123455!");

        String requestBody = om.writeValueAsString(loginDTO);
        System.out.println("테스트 : " + requestBody);


        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        Object value = resultActions.andReturn().getResponse().getHeaderValue("Authorization");
        System.out.println("테스트 : " + value);
        System.out.println("테스트 : " + responseBody);

        // verify

        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("패스워드가 잘못 입력되었습니다 "));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}