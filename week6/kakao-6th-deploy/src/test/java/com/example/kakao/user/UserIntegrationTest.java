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
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserIntegrationTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("회원가입 테스트")
    void join_test() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("gominseok@naver.com");
        requestDTO.setUsername("gominssuukk");
        requestDTO.setPassword("mmmiiinnn12!!!!");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("로그인 테스트")
    void login_test() throws Exception {
        //given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("rhalstjr1999@naver.com");
        requestDTO.setPassword("meta1234!");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void logi_failed_test() throws Exception {
        //given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("rhalstjr1999@naver.com");
        requestDTO.setPassword("meta1234!!!");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("중복 이메일 가입 테스트")
    void duplicate_email_join_test() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("rhalstjr1999@naver.com");
        requestDTO.setUsername("gominssuukk");
        requestDTO.setPassword("mmmiiinnn12!!!!");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("이메일 형식 미충족 테스트")
    void incorrect_email_format_test() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("rhalstjr1999naver.com");
        requestDTO.setUsername("gominssuukk");
        requestDTO.setPassword("mmmiiinnn12!!!!");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("유저 이름 길이 미충족 테스트")
    void incorrect_username_format_test() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("rhalstjr19991@naver.com");
        requestDTO.setUsername("go");
        requestDTO.setPassword("mmmiiinnn12!!!!");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("패스워드 길이 미충족 테스트")
    void incorrect_password_format_test1() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("rhalstjr19991@naver.com");
        requestDTO.setUsername("goasdasdasd");
        requestDTO.setPassword("12@a");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("패스워드 형식 미충족 테스트")
    void incorrect_password_format_test2() throws Exception {
        //given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("rhalstjr19991@naver.com");
        requestDTO.setUsername("goasdasdasd");
        requestDTO.setPassword("12@@@@@1234");

        String requestBody = om.writeValueAsString(requestDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print())
                .andDo(document);
    }
}