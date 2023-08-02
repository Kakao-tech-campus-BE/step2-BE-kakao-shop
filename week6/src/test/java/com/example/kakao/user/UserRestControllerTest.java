package com.example.kakao.user;

import com.example.kakao.MyRestDoc;
import com.example.kakao.cart.CartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test") //test profile 사용
@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) //Test 메서드 실행 전에 Sql 실행
@AutoConfigureMockMvc //MockMvc 사용
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //통합테스트(SF-F-DS(Handler, ExHandler)-C-S-R-PC-DB) 다 뜬다.
public class UserRestControllerTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("로그인")
    public void login_test() throws Exception {
        // given teardown.sql - user1 저장되어있음
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("user1@nate.com");
        requestDTO.setPassword("user1234!");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response").doesNotExist()); //null인지 확인
        resultActions.andExpect(jsonPath("$.error").doesNotExist());

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("로그인 - 유효하지 않은 이메일")
    public void login_test_if_notvalid_email() throws Exception {
        // given teardown.sql - user1 저장되어있음
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("user1nate.com"); //@가 없는 이메일
        requestDTO.setPassword("user1234!");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러-유효성 검사 에러(@Valid)

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("로그인 - 유효하지 않은 비밀번호(문자종류 포함X)")
    public void login_test_if_notvalid_pw() throws Exception {
        // given teardown.sql - user1 저장되어있음
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("user1@nate.com");
        requestDTO.setPassword("user1234"); //특수기호 포함하지않은 비밀번호

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러-유효성 검사 에러(@Valid)

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("로그인 - 유효하지않은 비밀번호(글자수)")
    public void login_test_if_notvalid_pw2() throws Exception {
        // given teardown.sql - user1 저장되어있음
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("user1@nate.com");
        requestDTO.setPassword("user12!"); //글자수 부족한 비밀번호

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러-유효성 검사 에러(@Valid)

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("회원가입")
    public void join_test() throws Exception {
        // given teardown.sql
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("newuser@nate.com");
        requestDTO.setPassword("user1234!");
        requestDTO.setUsername("newusermango");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

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
        resultActions.andExpect(jsonPath("$.response").doesNotExist()); //null인지 확인
        resultActions.andExpect(jsonPath("$.error").doesNotExist());

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }
    @Test
    @DisplayName("회원가입 - 유효하지않은 이메일")
    public void join_test_if_notvalid_email() throws Exception {
        // given teardown.sql
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("newusernate.com"); //@가 없는 유효하지않은 이메일
        requestDTO.setPassword("user1234!");
        requestDTO.setUsername("newusermango");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

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
        resultActions.andExpect(status().isBadRequest()); //400번 에러

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("회원가입 - 유효하지않은 비밀번호(모든 문자종류 포함X)")
    public void join_test_if_notvalid_pw1() throws Exception {
        // given teardown.sql
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("newuser@nate.com");
        requestDTO.setPassword("user1234"); //특수기호가 없는 유효하지않은 비밀번호
        requestDTO.setUsername("newusermango");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

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
        resultActions.andExpect(status().isBadRequest()); //400번 에러-유효성 검사 에러(@Valid)

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }
    @Test
    @DisplayName("회원가입 - 동일한 이메일 존재")
    public void join_test_if_duplicated_email() throws Exception {
        // given teardown.sql
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("user1@nate.com"); //중복된 이메일
        requestDTO.setPassword("user1234!");
        requestDTO.setUsername("newusermango");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

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
        resultActions.andExpect(status().isBadRequest()); //400번 에러-유효성 검사 에러(@Valid)

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("회원가입 - 유효하지않은 비밀번호(글자수)")
    public void join_test_if_notvalid_pw2() throws Exception {
        // given teardown.sql된
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("newuser@nate.com");
        requestDTO.setPassword("user12!"); //글자수 적은 비밀번호
        requestDTO.setUsername("newusermango");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

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
        resultActions.andExpect(status().isBadRequest()); //400번 에러-유효성 검사 에러(@Valid)

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("이메일 중복확인")
        public void check_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("newuser@nate.com"); //중복되지 않은 이메일

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

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
        resultActions.andExpect(jsonPath("$.response").doesNotExist()); //null인지 확인
        resultActions.andExpect(jsonPath("$.error").doesNotExist());

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("이메일 중복확인 - 중복된 이메일")
    public void check_test_if_duplicated() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("user1@nate.com"); //중복된 이메일(이미 저장된 이메일)

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

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
        resultActions.andExpect(status().isBadRequest()); //400번 에러

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("이메일 중복확인 - 유효하지않은 이메일")
    public void check_notvalid_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("newusernate.com"); //유효한 형식이 아닌 이메일

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

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
        resultActions.andExpect(status().isBadRequest()); //400번 에러

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }
}
