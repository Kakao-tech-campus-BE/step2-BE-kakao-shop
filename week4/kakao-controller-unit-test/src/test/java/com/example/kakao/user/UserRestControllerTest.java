package com.example.kakao.user;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

// GlobalExceptionHandler 와 UserRestController를 SpringContext에 등록합니다.
@Import({
        SecurityConfig.class, // 이걸 걸지 않고 실험을 해보자. post에서 터질 것이다.
        GlobalExceptionHandler.class // 얘를 추가하지않으면, UserRestController.class에서의 의존성 주입이 실패
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

    @DisplayName("회원가입")
    @Nested
    class join {

        @DisplayName("회원가입 성공")
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

            String responseBody = result.andReturn().getResponse().getContentAsString();
            System.out.println("테스트 : "+responseBody);

            // then
            result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        }

        @DisplayName("회원가입 실패: 올바르지 않은 이메일 형식")
        @Test
        void join_invalidEmail_test() throws Exception {

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
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            result.andDo(print());

            // then
            result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        }

        @DisplayName("회원가입 실패: 올바르지 않은 비밀번호 형식")
        @Test
        void join_invalidPassword_test() throws Exception {

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
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            result.andDo(print());

            // then
            result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        }

        @DisplayName("회원가입 실패: 너무 긴 회원이름")
        @Test
        void join_invalidUsername_test() throws Exception {

            // given
            UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
            requestDTO.setEmail("ssarmango@nate.com");
            requestDTO.setPassword("meta1234!");
            requestDTO.setUsername("sssss".repeat(100));
            String requestBody = om.writeValueAsString(requestDTO);

            // when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/join")
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            result.andDo(print());

            // then
            result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        }
    }

    @DisplayName("로그인")
    @Nested
    class Login {

        @DisplayName("로그인 성공")
        @Test
        void login_test() throws Exception {

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

        @DisplayName("로그인 실패: 올바르지 않은 이메일 형식")
        @Test
        void login_invalidEmail_test() throws Exception {

            // given
            UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
            loginDTO.setEmail("ssarnate.com");
            loginDTO.setPassword("meta1234!");
            String requestBody = om.writeValueAsString(loginDTO);

            // when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/login")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        }

        @DisplayName("로그인 실패: 올바르지 않은 패스워드 형식")
        @Test
        void login_invalidPassword_test() throws Exception {

            // given
            UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
            loginDTO.setEmail("ssar@nate.com");
            loginDTO.setPassword("meta1234");
            String requestBody = om.writeValueAsString(loginDTO);

            // when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/login")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        }

        @DisplayName("로그인 실패: 서비스 계층의 login 실패")
        @Test
        void login_serviceFail_test() throws Exception {

            // given
            UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
            loginDTO.setEmail("ssar@nate.com");
            loginDTO.setPassword("meta1234!");
            String requestBody = om.writeValueAsString(loginDTO);

            // stub
            when(userService.login(any())).thenThrow(Exception400.class);

            // when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/login")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            result.andDo(print());

            // then
            result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        }
    }
}
