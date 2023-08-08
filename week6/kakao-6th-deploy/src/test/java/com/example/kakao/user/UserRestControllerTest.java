package com.example.kakao.user;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@AutoConfigureRestDocs
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setUsername("chaee813");
        requestDTO.setEmail("chaee813@gmail.com");
        requestDTO.setPassword("abcd1234!");

        String requestBody = om.writeValueAsString(requestDTO);
        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_fail_test() throws Exception {
        // 같은 이메일이 이미 존재하는 경우
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setUsername("ssarmango");
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(requestDTO);
        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_fail2_test() throws Exception {
        // 유저 네임이 조건에 맞지 않는 경우
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setUsername("chaee");
        requestDTO.setEmail("chaee813@gmail.com");
        requestDTO.setPassword("abcd1234!");

        String requestBody = om.writeValueAsString(requestDTO);
        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 45자 이내여야 합니다.:username"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_fail3_test() throws Exception {
        // 이메일이 형식에 맞지 않을 경우
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setUsername("chaee813");
        requestDTO.setEmail("chaee813@gmailcom");
        requestDTO.setPassword("abcd1234!");

        String requestBody = om.writeValueAsString(requestDTO);
        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_fail4_test() throws Exception {
        // 이메일이 형식에 맞지 않을 경우
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setUsername("chaee813");
        requestDTO.setEmail("chaee813@gmail.com");
        requestDTO.setPassword("abcd1234");

        String requestBody = om.writeValueAsString(requestDTO);
        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_fail1_test() throws Exception {
        // 이메일이 존재하지 않는 경우
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango3@nate.com");
        requestDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : ssarmango3@nate.com"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_fail2_test() throws Exception {
        // 비밀번호를 틀린 경우
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("abcd1234!");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("패스워드가 잘못입력되었습니다 "));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
