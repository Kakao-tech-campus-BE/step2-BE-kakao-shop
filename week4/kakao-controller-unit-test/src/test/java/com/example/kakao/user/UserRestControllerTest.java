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

import static com.example.kakao._core.utils.PrintUtils.getPrettyString;
import static org.mockito.ArgumentMatchers.any;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {UserRestController.class})
public class UserRestControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("회원가입 테스트")
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssar@nate.com");
        requestDTO.setPassword("eunjin2!");
        requestDTO.setUsername("eunjinee");

        System.out.println("========================================requestBody 시작=========================================");
        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println(getPrettyString(requestBody));
        System.out.println("========================================requestBody 종료=========================================");

        // stub
        // X

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        System.out.println("========================================responseBody 시작=========================================");
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(getPrettyString(responseBody));
        System.out.println("========================================responseBody 종료=========================================");

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    @DisplayName("로그인 테스트")
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarssar@nate.com");
        requestDTO.setPassword("meta1234!");

        System.out.println("========================================requestBody 시작=========================================");
        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println(getPrettyString(requestBody));
        System.out.println("========================================requestBody 종료=========================================");

        // jwt 변환
        User user = User.builder().id(1).roles("ROLE_USER").build();
        String jwt = JWTProvider.create(user);

        // stub
        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        System.out.println("========================================responseHeader 시작=========================================");
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println(responseHeader);
        System.out.println("========================================responseHeader 종료=========================================");

        System.out.println("========================================responseBody 시작=========================================");
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(getPrettyString(responseBody));
        System.out.println("========================================responseBody 종료=========================================");

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
    }

}
