package com.example.kakao.user;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.CustomUserDetailsService;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
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


    // 이메일이나 비밀번호 형식에 대한 테스트는 프론트의 책임이 더 크며 명시적으로 DTO에서 거를 수 있기 때문에 따로 테스트 하지 않는다.
    // 그럼 중복 이메일은 왜 테스트하냐고 할 수 있는데 이메일의 중복을 체크하는 것은 온전히 백엔드의 책임이기 때문이다.
    // 왜 예외가 터지지 않지..? -> 중복 체크는 Service 레이어에서 진행하고, UserService는 MockBean으로 생성했기 때문이다.
    // WebMvcTest는 컨트롤러만 테스트 한다. 따라서 @Service가 붙은 클래스들은 Bean으로 생성하지 않기 때문에
    // 의존성 주입이 불가하여 MockBean으로 생성하는 것이다.
    // 찾아보니 SpyBean이라는게 있다. 써보자. -> 안된다 UserService는 또 UserJpaRepository와 연관되어 있다.
    @Test
    public void join_withSameEmail_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssarmango");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // given

        String sameEmail = requestDTO.getEmail();
        // 약간 억지로 구현한 느낌이 든다. join에 any()가 아닌 그냥 requestDTO를 그대로 떄려 넣으면 같은 값이 들어가 있어도
        // 객체의 해시 값이 다르니 다르다고 인식하는 것 같다.
        // JoinDTO에 @EqualsAndHashCode를 붙이고 테스트 해보자. -> 잘 동작한다.
        // 그런데 테스트를 올바르게 실행하기 위해 원본 코드에 어노테이션을 하나 더 붙이는게 맞는 행동일까....?
        Mockito.doThrow(new Exception400("동일한 이메일이 존재합니다 : " + sameEmail)).when(userService).join(requestDTO);

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
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }

    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);
        // stub
        User user = User.builder().id(1).roles("ROLE_USER").build();
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
    public void length_test(){
        String value = "Bearer eyJ0eX";
        System.out.println(value.substring(0,6));
    }

    @Test
    public void findById_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);
        // stub
        User user = User.builder().id(1).roles("ROLE_USER").build();
        String jwt = JWTProvider.create(user);

        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/users/" + user.getId())
                        .header(HttpHeaders.AUTHORIZATION, jwt)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("테스트 : "+responseBody);
        System.out.println("테스트 : "+responseHeader);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        // 실제로 저장된 유저가 아니기 때문에 response에는 null 이 들어온다.
        Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
    }

    @Test
    public void findById_diffId_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO);
        // stub
        User user = User.builder().id(1).roles("ROLE_USER").build();
        String jwt = JWTProvider.create(user);

        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/users/" + 2)
                        .header(HttpHeaders.AUTHORIZATION, jwt)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("테스트 : "+responseBody);
        System.out.println("테스트 : "+responseHeader);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("인증된 user는 해당 id로 접근할 권한이 없습니다:2"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(403));
    }

    @Test
    public void updatePassword_test() throws Exception {
        // given
        UserRequest.UpdatePasswordDTO updatePasswordDTO = new UserRequest.UpdatePasswordDTO();
        updatePasswordDTO.setPassword("ssar1234!");
        String requestBody = om.writeValueAsString(updatePasswordDTO);
        // stub
        User user = User.builder().id(1).roles("ROLE_USER").build();
        String jwt = JWTProvider.create(user);

        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/users/" + user.getId() + "/update-password")
                        .header(HttpHeaders.AUTHORIZATION, jwt)
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
    public void updatePassword_diffId_test() throws Exception {
        // given
        UserRequest.UpdatePasswordDTO updatePasswordDTO = new UserRequest.UpdatePasswordDTO();
        updatePasswordDTO.setPassword("ssar1234!");
        String requestBody = om.writeValueAsString(updatePasswordDTO);
        // stub
        User user = User.builder().id(1).roles("ROLE_USER").build();
        String jwt = JWTProvider.create(user);

        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/users/" + 2 + "/update-password")
                        .header(HttpHeaders.AUTHORIZATION, jwt)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("테스트 : "+responseBody);
        System.out.println("테스트 : "+responseHeader);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("인증된 user는 해당 id로 접근할 권한이 없습니다:2"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(403));
    }
}
