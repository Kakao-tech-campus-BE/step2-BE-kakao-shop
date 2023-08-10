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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.example.kakao._core.utils.PrintUtils.getPrettyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("이메일 중복 확인 성공")
    public void sameCheckEmail_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("addnewuser@nate.com");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("이메일 Pattern Validation 실패")
    public void sameCheckEmail_emailPatternValidationFail_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("addnewuser");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("이미 가입된 이메일이면 실패")
    public void sameCheckEmail_isPresent_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("ssarmango@nate.com");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("회원가입 성공")
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("addnewuser@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("addnewuser");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("이메일 Pattern Validation 실패")
    public void join_emailPatternValidationFail_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("addnewuser");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("addnewuser");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody zff시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("비밀번호 Size Validation 실패")
    public void join_passwordSizeValidationFail_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("addnewuser@nate.com");
        requestDTO.setPassword("meta1!");
        requestDTO.setUsername("addnewuser");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("비밀번호 Pattern Validation 실패")
    public void join_passwordPatternValidationFail_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("addnewuser@nate.com");
        requestDTO.setPassword("metameta");
        requestDTO.setUsername("addnewuser");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("이름 Size Validation 실패")
    public void join_usernameSizeValidationFail_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("addnewuser@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("new");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("이미 가입된 이메일이면 실패")
    public void join_isPresent_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssarmango");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("로그인 성공")
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("이메일 Pattern Validation 실패")
    public void login_emailPatternValidationFail_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango");
        requestDTO.setPassword("meta1234!");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("비밀번호 Size Validation 실패")
    public void login_passwordSizeValidationFail_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1!");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("비밀번호 Pattern Validation 실패")
    public void login_passwordPatternValidationFail_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("metameta");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("존재하지 않는 이메일이면 실패")
    public void login_notFound_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("addnewuser@nate.com");
        requestDTO.setPassword("meta1234!");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isNotFound()); // 404

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 실패")
    public void login_notMatch_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("wrongpassword!");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
