package com.example.kakao.user;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

// GlobalExceptionHandler 와 UserRestController를 SpringContext에 등록합니다.

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
        FakeStore.class
})
@WebMvcTest(controllers = {UserRestController.class})
public class UserRestControllerTest {

    // 객체의 모든 메서드는 추상메서드로 구현됩니다. (가짜로 만들면)
    // 해당 객체는 SpringContext에 등록됩니다.
    @MockBean
    private UserService userService;
    @MockBean
    private UserJPARepository userJPARepository;
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    // @WebMvcTest를 하면 MockMvc가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private MockMvc mvc;

    // @WebMvcTest를 하면 ObjectMapper가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){

    }

    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssarmango");
        String requestBody = om.writeValueAsString(requestDTO);

        //login과 마찬가지로 stub이 추가되어야한다고 생각이 듭니다
        //stub
        Mockito.doNothing().when(userService).join(any());

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

    @DisplayName("로그인 성공 테스트")
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


    @DisplayName("로그인 실패 테스트: 잘못된 패스워드")
    @Test
    public void login_wrongPassword_fail_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("mete12!");
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
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }

// ... [Other test methods will follow the same structure]

    @DisplayName("이메일 중복확인 테스트")
    @Test
    public void check_sameEmail_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO emailCheckDTO = new UserRequest.EmailCheckDTO();
        emailCheckDTO.setEmail("dummy1@nate.com");
        String requestBody = om.writeValueAsString(emailCheckDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value("이메일 사용 가능합니다."));
    }


    @DisplayName("이메일 중복확인에러 테스트")
    @Test
    public void check_sameEmail_fail_test() throws Exception {
        // given
        String duplicateEmail = "dummy1";
        UserRequest.EmailCheckDTO emailCheckDTO = new UserRequest.EmailCheckDTO();
        emailCheckDTO.setEmail(duplicateEmail);
        String requestBody = om.writeValueAsString(emailCheckDTO);


        doThrow(new Exception400("동일한 이메일이 존재합니다 : " + duplicateEmail))
                .when(userService).sameCheckEmail(duplicateEmail);

        // check 에 post 보내기
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest()) // Expecting 400 error status
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : " + duplicateEmail));
    }

// ... [Continue rewriting the remaining test methods in a similar style]


    @Test
    public void length_test(){
        String value = "Bearer eyJ0eX";
        System.out.println(value.substring(0,6));
    }

}
