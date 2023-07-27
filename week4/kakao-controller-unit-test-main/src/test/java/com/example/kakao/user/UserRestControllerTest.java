package com.example.kakao.user;

import com.example.kakao._core.errors.GlobalExceptionHandler;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {UserRestController.class})
class UserRestControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("회원가입 성공")
    void join_success_test() throws Exception {
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

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 형식 오류")
    void join_fail_test1() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmangonate.com");
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

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }

    @Test
    @DisplayName("회원가입 실패 - 비밀번호 형식 오류")
    void join_fail_test2() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234");
        requestDTO.setUsername("ssarmango");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }

    @Test
    @DisplayName("회원가입 실패 - 이름 길이 오류")
    void join_fail_test3() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssar");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("8에서 45자 이내여야 합니다.:username"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1234!");
        User user = User.builder().id(1).roles("ROLE_USER").build();
        String requestBody = om.writeValueAsString(loginDTO);

        // stub
        String jwt = JWTProvider.create(user);
        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("responseBody=" + responseBody);
        System.out.println("responseHeader=" + responseHeader);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
    }

    @Test
    @DisplayName("로그인 실패 - 이메일 형식 오류")
    void login_fail_test1() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarnate.com");
        loginDTO.setPassword("meta1234!");
        User user = User.builder().id(1).roles("ROLE_USER").build();
        String requestBody = om.writeValueAsString(loginDTO);

        // stub
        String jwt = JWTProvider.create(user);
        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("responseBody=" + responseBody);
        System.out.println("responseHeader=" + responseHeader);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 형식 오류")
    void login_fail_test2() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1234");
        User user = User.builder().id(1).roles("ROLE_USER").build();
        String requestBody = om.writeValueAsString(loginDTO);

        // stub
        String jwt = JWTProvider.create(user);
        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("responseBody=" + responseBody);
        System.out.println("responseHeader=" + responseHeader);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }
}
