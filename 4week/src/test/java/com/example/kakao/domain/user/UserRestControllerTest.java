package com.example.kakao.user;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyJwt;
import com.example.kakao.domain.user.User;
import com.example.kakao.domain.user.UserRestController;
import com.example.kakao.domain.user.UserService;
import com.example.kakao.domain.user.dto.request.EmailCheckDTO;
import com.example.kakao.domain.user.dto.request.JoinDTO;
import com.example.kakao.domain.user.dto.request.LoginDTO;
import com.example.kakao.domain.user.dto.request.UpdatePasswordDTO;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

// GlobalExceptionHandler 와 UserRestController를 SpringContext에 등록합니다.


@Import({
        SecurityConfig.class,
})
@WebMvcTest(controllers = {UserRestController.class})
class UserRestControllerTest {

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
    @DisplayName("회원가입")
    void testJoin() throws Exception {
        // given
        JoinDTO requestDTO = new JoinDTO();
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
    @DisplayName("로그인")
    void testLogin() throws Exception {
        // given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);

        // stub
        String jwt = DummyJwt.generate();
        BDDMockito.given(userService.login(any())).willReturn(jwt);

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
    @DisplayName("이메일 중복체크")
    void testEmailDuplicateCheck() throws Exception {
        // given
        String email = "abcd1234@gmail.com";
        EmailCheckDTO emailCheckDTO = new EmailCheckDTO();
        emailCheckDTO.setEmail(email);
        String requestBody = om.writeValueAsString(emailCheckDTO);

        // stub
        BDDMockito.willDoNothing().given(userService).checkEmailDuplicated(email);

        // when
        ResultActions result = mvc.perform(
            MockMvcRequestBuilders
                .post("/check")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    @DisplayName("비밀번호 변경")
    void testUpdatePassword() throws Exception {
        // given
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setPassword("meta1234!");
        String jwt = DummyJwt.generate();

        // stub
        BDDMockito.willDoNothing().given(userService).updatePassword(any(), anyInt());

        // when
        ResultActions result = mvc.perform(
            MockMvcRequestBuilders
                .post("/update-password")
                .header(JWTProvider.HEADER, jwt)
                .content(om.writeValueAsString(updatePasswordDTO))
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

}
