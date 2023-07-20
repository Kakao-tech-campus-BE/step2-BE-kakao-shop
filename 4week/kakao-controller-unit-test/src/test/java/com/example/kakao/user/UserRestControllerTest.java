package com.example.kakao.user;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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
        System.out.println("테스트 : "+responseBody);
        System.out.println("테스트 : "+responseHeader);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
    }

    @Nested
    @DisplayName("실패 케이스")
    public class FailCases {
        @Nested
        @DisplayName("[/join]회원 가입")
        public class JoinTest {
            @Test
            @DisplayName("회원가입 시 올바르지 않은 이메일 양식")
            public void join_email_form_fail_test() throws Exception {
                UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
                requestDTO.setEmail("metanate.com");
                requestDTO.setPassword("meta1234!");
                requestDTO.setUsername("metameta");
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
                result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
                result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
                result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
                result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
            }

            @Test
            @DisplayName("회원가입 시 올바르지 않은 비밀번호 양식")
            public void join_password_form_fail_test() throws Exception {
                UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
                requestDTO.setEmail("meta@nate.com");
                requestDTO.setPassword("meta1234");
                requestDTO.setUsername("metameta");
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
                result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
                result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
                result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
                result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
            }
        }

        @Nested
        @DisplayName("[/login]로그인")
        public class login{
            @Test
            @DisplayName("등록되지 않은 이메일로 로그인하는 경우")
            public void login_authenticated_fail_test() throws Exception {
                // given
                UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
                loginDTO.setEmail("ssar@nate.com");
                loginDTO.setPassword("meta1234!");
                User user = User.builder().id(1).roles("ROLE_USER").build();
                String requestBody = om.writeValueAsString(loginDTO);

                // stub
                Mockito.when(userService.login(any())).thenThrow(new Exception400("이메일을 찾을 수 없습니다 : "+loginDTO.getEmail()));

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
                result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
                result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
                result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : ssar@nate.com"));
                result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
            }

            @Test
            @DisplayName("로그인 시 비밀번호가 잘못된 경우")
            public void login_password_fail_test() throws Exception {
                // given
                UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
                loginDTO.setEmail("ssar@nate.com");
                loginDTO.setPassword("meta1234!");
                User user = User.builder().id(1).roles("ROLE_USER").build();
                String requestBody = om.writeValueAsString(loginDTO);

                // stub
                Mockito.when(userService.login(any())).thenThrow(new Exception400("패스워드가 잘못입력되었습니다."));

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
                result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
                result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
                result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("패스워드가 잘못입력되었습니다."));
                result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
            }
        }
    }

    @Test
    public void length_test(){
        String value = "Bearer eyJ0eX";
        System.out.println(value.substring(0,6));
    }

}
