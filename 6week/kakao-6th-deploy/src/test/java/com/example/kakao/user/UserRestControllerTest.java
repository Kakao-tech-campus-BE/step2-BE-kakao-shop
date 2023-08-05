package com.example.kakao.user;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ObjectMapper om;

    // (기능3) 이메일 중복체크 (실패1)
    @Test
    public void check_test_Fail() throws Exception {
        // given
        String testEmail = "ssarmango@nate.com";
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail(testEmail);
        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 테스트: " + requestBody);


        // when
        ResultActions resultActions = mvc.perform(post("/check")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답 테스트: " + responseBody);


        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);


    }

    // (기능3) 이메일 중복체크 (실패2)
    @Test
    public void check_test_Fail2() throws Exception {
        // given
        String testEmail = "ssarmangonate.com";
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail(testEmail);
        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 테스트: " + requestBody);


        // when
        ResultActions resultActions = mvc.perform(post("/check")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답 테스트: " + responseBody);


        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);


    }


    // (기능3) 이메일 중복체크 (성공)
    @Test
    public void check_test_Pass() throws Exception {
        // given
        String testEmail = "gijun@nate.com";
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail(testEmail);
        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 테스트: " + requestBody);


        // when
        ResultActions resultActions = mvc.perform(post("/check")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답 테스트: " + responseBody);


        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);


    }


    // (기능4) 회원가입
    @Test
    public void join_test() throws Exception {
        // given teardown.sql
        String userEmail = "gijun@jnu.ac.kr";
        String userName = "gijun1234";
        String userPW = "gijun123!";
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail(userEmail);
        requestDTO.setUsername(userName);
        requestDTO.setPassword(userPW);

        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("요청 테스트: " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답 테스트: " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    // (기능4) 회원가입 실패1
    @Test
    public void join_test_fail1() throws Exception {
        // given teardown.sql
        String userEmail = "gijunjnu.ac.kr";
        String userName = "gijun1234";
        String userPW = "gijun123!";
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail(userEmail);
        requestDTO.setUsername(userName);
        requestDTO.setPassword(userPW);

        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("요청 테스트: " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답 테스트: " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    // (기능4) 회원가입 실패2
    @Test
    public void join_test_fail2() throws Exception {
        // given teardown.sql
        String userEmail = "gijun@jnu.ac.kr";
        String userName = "gijun1234";
        String userPW = "gijun12322";
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail(userEmail);
        requestDTO.setUsername(userName);
        requestDTO.setPassword(userPW);

        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("요청 테스트: " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답 테스트: " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }


    // (기능4) 회원가입 실패3
    @Test
    public void join_test_fail3() throws Exception {
        // given teardown.sql
        String userEmail = "gijun@jnu.ac.kr";
        String userName = "gijun1234";
        String userPW = "dd15!";
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail(userEmail);
        requestDTO.setUsername(userName);
        requestDTO.setPassword(userPW);

        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("요청 테스트: " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답 테스트: " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }


    // (기능4) 회원가입 실패4
    @Test
    public void join_test_fail4() throws Exception {
        // given teardown.sql
        String userEmail = "ssarmango@nate.com";
        String userName = "gijun1234";
        String userPW = "gijun123!";
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail(userEmail);
        requestDTO.setUsername(userName);
        requestDTO.setPassword(userPW);

        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("요청 테스트: " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답 테스트: " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    // (기능5) 로그인 실패1
    @Test
    public void login_test_Fail1() throws Exception {
        // given
        String userEmail = "ssarmango@nate.com";
        String userPW = "meta1234";

        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail(userEmail);
        requestDTO.setPassword(userPW);

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 테스트: " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답 테스트: " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);


    }

    // (기능5) 로그인 실패2
    @Test
    public void login_test_Fail2() throws Exception {
        // given
        String userEmail = "ssarmangonate.com";
        String userPW = "meta1234!";

        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail(userEmail);
        requestDTO.setPassword(userPW);

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 테스트: " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답 테스트: " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);


    }

    // (기능5) 로그인 실패3
    @Test
    public void login_test_Fail3() throws Exception {
        // given
        String userEmail = "ssarmango3@nate.com";
        String userPW = "meta1234!";

        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail(userEmail);
        requestDTO.setPassword(userPW);

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 테스트: " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답 테스트: " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : ssarmango3@nate.com"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);


    }

    // (기능5) 로그인 실패1
    @Test
    public void login_test_Fail4() throws Exception {
        // given
        String userEmail = "ssarmango@nate.com";
        String userPW = "sm3!@";

        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail(userEmail);
        requestDTO.setPassword(userPW);

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 테스트: " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답 테스트: " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);


    }



    // (기능5) 로그인 성공
    @Test
    public void login_test_Pass() throws Exception {
        // given
        String userEmail = "ssarmango@nate.com";
        String userPW = "meta1234!";

        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail(userEmail);
        requestDTO.setPassword(userPW);

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 테스트: " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답 테스트: " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);


    }


}
