package com.example.kakao.user;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
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
    public void join_invalid_email_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("invalid_email");
        requestDTO.setPassword("cha1234!!");
        requestDTO.setUsername("jiwon");
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
        result.andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
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

    @Test
    public void login_invalid_email_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("invalid_email");
        loginDTO.setPassword("cha1234!");
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
        result.andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
    }

    @Test
    public void length_test(){
        String value = "Bearer eyJ0eX";
        System.out.println(value.substring(0,6));
    }

    @Test
    public void updatePassword_test() throws Exception {
        // given
        UserRequest.UpdatePasswordDTO requestDTO = new UserRequest.UpdatePasswordDTO();
        requestDTO.setPassword("hello123!");

        User user = User.builder()
                .id(1)
                .roles("ROLE_USER")
                .build();
        String jwt = JWTProvider.create(user);

        when(userService.findById(1)).thenReturn(new UserResponse.FindById(user));

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/users/1/update-password")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwt)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

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
        requestDTO.setPassword("cha1234!!");
        String content = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/users/1/update-password")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwt)
        );

        // then
        result.andExpect(status().isForbidden());
    }


    @Test
    public void findById_test() throws Exception {
        User user = User.builder()
                .id(1)
                .roles("ROLE_USER")
                .build();
        String jwt = JWTProvider.create(user);

        when(userService.findById(1)).thenReturn(new UserResponse.FindById(user));

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwt)
        );

        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

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
    }

    @Test
    public void check_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("cha@naver.com");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

}
