package com.example.kakao._core.user;

import com.example.kakao._core.MyRestDoc;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao.user.User;
import com.example.kakao.user.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssartest@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssartest");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                        post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

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
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String jwt = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);

        /*
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("테스트 : "+responseBody);
        System.out.println("테스트 : "+responseHeader);
        */

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void check_test() throws Exception {

        //given
        UserRequest.EmailCheckDTO emailCheckDTO = new UserRequest.EmailCheckDTO();
        emailCheckDTO.setEmail("ssartest@nate.com");

        String requestBody = om.writeValueAsString(emailCheckDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //console
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

}
