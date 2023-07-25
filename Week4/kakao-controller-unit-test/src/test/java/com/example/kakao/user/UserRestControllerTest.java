package com.example.kakao.user;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

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
    public void join_fail_test() throws Exception {
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
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
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

    @Test
    public void login_fail_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarnate.com");
        loginDTO.setPassword("meta1234!");
        User user = User.builder().id(1).roles("ROLE_USER").build();
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }

    @Test
    public void updatePassword_test() throws Exception {
        // given
        int id = 1;
        User user = User.builder()
                .id(id)
                .email("ssar@nate.com")
                .password("meta1234!")
                .username("ssarmango")
                .roles("ROLE_USER")
                .build();
        UserRequest.UpdatePasswordDTO updatePasswordDTO = new UserRequest.UpdatePasswordDTO();
        updatePasswordDTO.setPassword("ssar1234!");
        String requestBody = om.writeValueAsString(updatePasswordDTO);

        // stub
        String jwt = JWTProvider.create(user);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/users/" + id + "/update-password")
                        .header(HttpHeaders.AUTHORIZATION, jwt)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    public void updatePassword_fail_test() throws Exception {
        // given
        int id = 1;
        User user = User.builder()
                .id(id)
                .email("ssar@nate.com")
                .password("meta1234!")
                .username("ssarmango")
                .roles("ROLE_USER")
                .build();
        UserRequest.UpdatePasswordDTO updatePasswordDTO = new UserRequest.UpdatePasswordDTO();
        updatePasswordDTO.setPassword("ssar12!");
        String requestBody = om.writeValueAsString(updatePasswordDTO);

        // stub
        String jwt = JWTProvider.create(user);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/users/" + id + "/update-password")
                        .header(HttpHeaders.AUTHORIZATION, jwt)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }

    @Test
    public void findById_test() throws Exception {
        // given
        int id = 1;
        User user = User.builder()
                .id(id)
                .email("ssar@nate.com")
                .password("meta1234!")
                .username("ssarmango")
                .roles("ROLE_USER")
                .build();

        // stub
        String jwt = JWTProvider.create(user);
        Mockito.when(userService.findById(id)).thenReturn(new UserResponse.FindById(user));

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/users/" + id)
                        .header(HttpHeaders.AUTHORIZATION, jwt)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.username").value("ssarmango"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.email").value("ssar@nate.com"));
    }

    @Test
    public void findById_fail_test() throws Exception {
        // given
        int id = 1;
        int wrongId  = 2;
        User user = User.builder()
                .id(id)
                .email("ssar@nate.com")
                .password("meta1234!")
                .username("ssarmango")
                .roles("ROLE_USER")
                .build();

        // stub
        String jwt = JWTProvider.create(user);
        Mockito.when(userService.findById(id)).thenReturn(new UserResponse.FindById(user));

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/users/" + wrongId)
                        .header(HttpHeaders.AUTHORIZATION, jwt)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("인증된 user는 해당 id로 접근할 권한이 없습니다:2"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(403));
    }

    @Test
    public void check_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    public void length_test(){
        String value = "Bearer eyJ0eX";
        System.out.println(value.substring(0,6));
    }

}
