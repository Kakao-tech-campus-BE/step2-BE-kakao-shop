package com.example.kakaoshop.user;

import com.example.kakaoshop.user.UserRequest.JoinDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional //테스트 후 rollback
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext context;

    //Spring Security 테스트 환경 구성
    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    //회원가입 요청 메서드
    private ResultActions doPerform(String requestData) throws Exception {
        return mvc.perform(
                post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestData));
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입 성공(가입된 id와 비밀번호)")
    public void join_success_test() throws Exception {
        //given
        //유저 생성
        UserRequest.JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("newuser");
        joinDTO.setEmail("newuser@nate.com");
        joinDTO.setPassword("newuser1234!");
        //JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(joinDTO);

        //when
        doPerform(requestData)
                .andDo(print()) //결과 출력
        //then
                .andExpect(jsonPath("$.success").value("true"));
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입-올바르지않은 이메일")
    public void join_fail_email_format_test() throws Exception {
        //given
        //유저 생성
        UserRequest.JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("newuser");
        joinDTO.setEmail("newusernate.com"); //@가 없는 올바르지 않은 이메일
        joinDTO.setPassword("newuser1234!");
        //JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(joinDTO);

        //when
        doPerform(requestData)
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입-비밀번호 검증")
    public void join_fail_password_test() throws Exception {
        //given
        //유저 생성
        UserRequest.JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("newuser");
        joinDTO.setEmail("newuser@nate.com");
        joinDTO.setPassword("newuser1234"); //특수문자가 없는 비밀번호
        //JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(joinDTO);

        //when
        doPerform(requestData)
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입-중복 이메일 검증")
    public void join_fail_email_duplicated_test() throws Exception {
        //given
        //유저 생성
        UserRequest.JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("newuser");
        joinDTO.setEmail("newuser@nate.com");
        joinDTO.setPassword("newuser1234!");
        //JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(joinDTO);

        //when
        doPerform(requestData)
                .andExpect(jsonPath("$.success").value("true"));
        //중복 이메일
        doPerform(requestData)
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입-글자수 검증")
    public void join_fail_password_length_test() throws Exception {
        //given
        //유저 생성
        UserRequest.JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("newuser");
        joinDTO.setEmail("newuser@nate.com");
        joinDTO.setPassword("new12!");
        //JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(joinDTO);

        //when
        //중복 이메일
        doPerform(requestData)
                .andDo(print()) //결과 출력
        //then
                .andExpect(jsonPath("$.success").value("false"));
    }


    @Test
    @WithMockUser
    @DisplayName("로그인 성공(가입된 id와 비밀번호)")
    public void login_success_test() throws Exception {
        //given
        //user 생성
        User user = User.builder()
                .email("user1@nate.com")
                .password(passwordEncoder.encode("user1234!"))
                .username("user")
                .roles("ROLE_USER")
                .build();
        //저장
        userJPARepository.save(user);

        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("user1@nate.com");
        loginDTO.setPassword("user1234!");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);
        //jwt Token
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzMzZAbmF0ZS5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjEsImV4cCI6MTY4ODg5ODkxNn0.2ovT4QRQHAKFsjHZG1g_bFwC3RN9-3TxdgS_gMm3FKVstqrqPrw6C0VZEwmh5buZzz3ek3Ez_Z3IsNqiVnONcQ";

        //when
        mvc.perform(
                post("/login")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestData))
                .andDo(print()) //결과 출력
        //then
                .andExpect(jsonPath("$.success").value("true"));
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 실패 -가입된 id와 잘못된 비밀번호")
    public void login_fail_pw_test() throws Exception {
        //given
        //user 생성
        User user = User.builder()
                .email("user@nate.com")
                .password(passwordEncoder.encode("user1234!"))
                .username("user")
                .roles("ROLE_USER")
                .build();
        //저장
        userJPARepository.save(user);
        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("user@nate.com");
        loginDTO.setPassword("wrongpassword!");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);
        //jwt Token
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzMzZAbmF0ZS5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjEsImV4cCI6MTY4ODg5ODkxNn0.2ovT4QRQHAKFsjHZG1g_bFwC3RN9-3TxdgS_gMm3FKVstqrqPrw6C0VZEwmh5buZzz3ek3Ez_Z3IsNqiVnONcQ";

        //when
        mvc.perform(
                post("/login")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestData))
                .andDo(print()) //결과 출력
        //then
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - 존재하지 않는 id와 비밀번호 (미가입)")
    public void login_fail_unregistered_test() throws Exception {
        //given
        //user 생성
        User user = User.builder()
                .email("user@nate.com")
                .password(passwordEncoder.encode("user1234!"))
                .username("user")
                .roles("ROLE_USER")
                .build();
        //저장
        userJPARepository.save(user);
        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("newuser@nate.com"); //이미 존재하는 id
        loginDTO.setPassword("fake1234!");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);
        //jwt Token
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzMzZAbmF0ZS5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjEsImV4cCI6MTY4ODg5ODkxNn0.2ovT4QRQHAKFsjHZG1g_bFwC3RN9-3TxdgS_gMm3FKVstqrqPrw6C0VZEwmh5buZzz3ek3Ez_Z3IsNqiVnONcQ";

        //when
        mvc.perform(
                post("/login")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestData))
                .andDo(print()) //결과 출력
        //then
                .andExpect(jsonPath("$.success").value("false"));

    }

    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - 이메일 형식 검증")
    public void login_fail_email_format_test() throws Exception {
        //given
        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("newusernate.com"); //올바르지 않은 이메일 (@가 없음)
        loginDTO.setPassword("user1234!");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);
        //jwt Token
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzMzZAbmF0ZS5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjEsImV4cCI6MTY4ODg5ODkxNn0.2ovT4QRQHAKFsjHZG1g_bFwC3RN9-3TxdgS_gMm3FKVstqrqPrw6C0VZEwmh5buZzz3ek3Ez_Z3IsNqiVnONcQ";

        //when
        mvc.perform(
                    post("/login")
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestData))
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - 비밀번호 글자 검증")
    public void login_fail_password_character_test() throws Exception {
        //given
        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("newuser@nate.com");
        loginDTO.setPassword("user1234"); //특수문자가 없는 비밀번호
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);
        //jwt Token
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzMzZAbmF0ZS5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjEsImV4cCI6MTY4ODg5ODkxNn0.2ovT4QRQHAKFsjHZG1g_bFwC3RN9-3TxdgS_gMm3FKVstqrqPrw6C0VZEwmh5buZzz3ek3Ez_Z3IsNqiVnONcQ";

        //when
        mvc.perform(
                    post("/login")
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestData))
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }
    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - 인증되지 않은 유저")
    public void login_fail_unauth_test() throws Exception {
        //given
        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("newuser@nate.com");
        loginDTO.setPassword("user1234!");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);

        //when
        mvc.perform( //토큰 보내지 않음
                    post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestData))
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - 비밀번호 글자수")
    public void login_fail_password_length_test() throws Exception {
        //given
        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("newuser@nate.com");
        loginDTO.setPassword("us4!"); //적은 글자수의 비밀번호
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);

        //when
        mvc.perform( //토큰 보내지 않음
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestData))
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }


    @Test
    @WithMockUser
    @DisplayName("이메일 확인 테스트 - 이미 존재하는 email")
    public void check_fail_duplicated_test() throws Exception {
        //given
        //user 생성
        User user = User.builder()
                .email("user1@nate.com")
                .password(passwordEncoder.encode("user1234!"))
                .username("user")
                .roles("ROLE_USER")
                .build();
        //저장
        userJPARepository.save(user);

        //요청 body
        UserRequest.CheckEmailDTO checkEmailDTO = new UserRequest.CheckEmailDTO();
        checkEmailDTO.setEmail("user1@nate.com"); //존재하는 email
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(checkEmailDTO);

        //when
        mvc.perform(
                        post("/check")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestData))
                .andDo(print()) //결과 출력
        // verify
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("이메일 확인 테스트 - 올바르지않은 형식")
    public void check_fail_format_test() throws Exception {
        //given
        //요청 body
        UserRequest.CheckEmailDTO checkEmailDTO = new UserRequest.CheckEmailDTO();
        checkEmailDTO.setEmail("user1nate.com"); //올바르지않은 형식(@가 없음)
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(checkEmailDTO);

        //when
        mvc.perform(
                        post("/check")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestData))
                .andDo(print()) //결과 출력
        // verify
                .andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.error.status").value(400)); //400번 에러
    }
}