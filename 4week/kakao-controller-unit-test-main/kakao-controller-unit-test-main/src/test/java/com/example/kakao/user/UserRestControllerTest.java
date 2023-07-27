package com.example.kakao.user;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// GlobalExceptionHandler 와 UserRestController를 SpringContext에 등록합니다.

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {UserRestController.class})
public class UserRestControllerTest {

    // 객체의 모든 메서드는 추상메서드로 구현됩니다. (가짜로 만들면)
    // 해당 객체는 SpringContext에 등록됩니다.
    @MockBean
    private UserService userService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    // @WebMvcTest를 하면 MockMvc가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private MockMvc mvc;

    // @WebMvcTest를 하면 ObjectMapper가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private ObjectMapper om;

    @Test
    public void t1(){}

    @DisplayName("(기능1) 회원가입 테스트")
    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssarmango");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @DisplayName("회원가입 - 이메일 형식 에러 테스트")
    @Test
    public void join_invalid_email_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("invalid_email");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssarmango");
        String requestBody = om.writeValueAsString(requestDTO);

        // Set up the userService mock
        Mockito.doNothing().when(userService).join(any());

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
    }

    @DisplayName("회원가입 - 패스워드 형식 에러 테스트")
    @Test
    public void join_invalid_password_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssar@nate.com");
        requestDTO.setPassword("meta1234");
        requestDTO.setUsername("ssarmango");
        String requestBody = om.writeValueAsString(requestDTO);

        // Set up the userService mock
        Mockito.doNothing().when(userService).join(any());

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
    }

    @DisplayName("(기능2) 로그인 테스트")
    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1234!");
        User user = User.builder().id(1).roles("ROLE_USER").build();
        String requestBody = om.writeValueAsString(loginDTO);

        // stub
        String jwt = JWTProvider.create(user);
        when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("테스트 : "+responseBody);
        System.out.println("테스트 : "+responseHeader);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
    }

    @DisplayName("로그인 - 이메일 형식 에러 테스트")
    @Test
    public void login_invalid_email_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("invalid_email");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);

        // Set up the userService mock
        when(userService.login(any())).thenReturn(null);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
    }

    @DisplayName("로그인 - 패스워드 형식 에러 테스트")
    @Test
    public void login_invalid_password_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1234");
        String requestBody = om.writeValueAsString(loginDTO);

        // Set up the userService mock
        when(userService.login(any())).thenReturn(null);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
    }

    @DisplayName("패스워드 변경 테스트")
    @Test
    public void updatePassword_test() throws Exception {
        // given
        User user = User.builder()
                .id(1)
                .roles("ROLE_USER")
                .build();
        String jwt = JWTProvider.create(user);

        UserRequest.UpdatePasswordDTO requestDTO = new UserRequest.UpdatePasswordDTO();
        requestDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(requestDTO);

        when(userService.findById(1)).thenReturn(new UserResponse.FindById(user));

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/users/1/update-password")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwt)
        );

        // then
        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @DisplayName("패스워드 변경 - 접근 권한 에러 테스트")
    @Test
    @WithMockUser
    public void updatePassword_forbidden_test() throws Exception {
        // given
        User user = User.builder()
                .id(2)
                .roles("ROLE_USER")
                .build();
        String jwt = JWTProvider.create(user);

        UserRequest.UpdatePasswordDTO requestDTO = new UserRequest.UpdatePasswordDTO();
        requestDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/users/1/update-password")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwt)
        );

        // then
        result.andExpect(status().isForbidden());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("인증된 user는 해당 id로 접근할 권한이 없습니다.:1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(403));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
    }


    @DisplayName("유저 조회 테스트")
    @Test
    public void findById_test() throws Exception {
        int userId = 1;
        User user = User.builder()
                .id(userId)
                .roles("ROLE_USER")
                .build();
        String jwt = JWTProvider.create(user);

        when(userService.findById(userId)).thenReturn(new UserResponse.FindById(user));

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/users/"+userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwt)
        );

        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @DisplayName("유저 조회 - 접근 권한 에러 테스트")
    @Test
    @WithMockUser
    public void findById_forbidden_test() throws Exception {
        // given
        int userId = 1;
        User user = User.builder()
                .id(2)
                .roles("ROLE_USER")
                .build();
        String jwt = JWTProvider.create(user);

        when(userService.findById(userId)).thenReturn(new UserResponse.FindById(user));

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwt)
        );

        // then
        result.andExpect(status().isForbidden());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("인증된 user는 해당 id로 접근할 권한이 없습니다.:1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(403));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
    }

    @DisplayName("이메일 중복 체크 테스트")
    @Test
    public void check_email_duplicated_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("ssar@nate.com");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @DisplayName("이메일 중복 체크 - 이메일 형식 에러 테스트")
    @Test
    public void check_email_duplicated_error_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("invalid_email");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
    }

    @DisplayName("이메일 중복 체크 - 중복된 이메일 case 테스트")
    @Test
    public void check_email_duplicated_error_test2() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("ssar@nate.com");
        String requestBody = om.writeValueAsString(requestDTO);

        // stub
        Mockito.doThrow(new Exception400("동일한 이메일이 존재합니다.:" + requestDTO.getEmail())).when(userService).checkEmailDuplicated(requestDTO.getEmail());

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
    }
}

