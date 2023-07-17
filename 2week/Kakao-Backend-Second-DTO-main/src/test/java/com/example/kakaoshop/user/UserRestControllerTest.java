package com.example.kakaoshop.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    // 회원가입
    public void join() throws Exception {
        String content = objectMapper.writeValueAsString(new UserRequest.JoinDTO("cha@naver.com", "12341234", "cah"));

        // when
        ResultActions resultActions = mvc.perform(
                post("/auth/join")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.errorDTO").value(IsNull.nullValue()));
    }

    @Test
    public void login() throws Exception {
        String content = objectMapper.writeValueAsString(new UserRequest.JoinDTO("cha@naver.com", "12341234", "cah"));

        // when
        ResultActions resultActions =  mvc.perform(
                post("/auth/join")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        content = objectMapper.writeValueAsString(new UserRequest.LoginDTO("cha@naver.com", "12341234"));

        // when
        resultActions = mvc.perform(
                post("/auth/login")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.errorDTO").value(IsNull.nullValue()));
    }
}