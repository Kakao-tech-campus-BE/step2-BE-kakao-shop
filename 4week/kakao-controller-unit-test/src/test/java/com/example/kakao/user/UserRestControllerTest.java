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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;

// GlobalExceptionHandler 와 UserRestController를 SpringContext에 등록합니다.


//별거아님 그냥 import해오는거 클래스단위로 가져올수있는거임 수구
@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
//GlobalExceptionHandler는 실제로 테스트해보고싶으니까 import하고
@WebMvcTest(controllers = {UserRestController.class})
public class UserRestControllerTest {

    //UserService 나 Error로그남기는 repo는 테스트해보고싶은게 아니므로 가짜로 준다.
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
        result.andExpect(MockMvcResultMatchers.jsonPath("success").value("true"));
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
        //밑에 해석하자면 userService클래스안에 login이라는 함수가 실행될떄, 매개변수로뭐든들어오든간에(any()) jwt를 리턴해주겠다는의미이다.
        //즉, 앞에 MockBean으로설정한 객체들의 추상메서드를 가정법으로 구현한거임
        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        // return body내용을 json으로 가져오는거임
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        //권한(Authorization)헤더가지고오는거임
        System.out.println("테스트 : "+responseBody);
        System.out.println("테스트 : "+responseHeader);

        // then

        //이떄 success만 성공뜨는거 확인하면 안됨
        // 토큰이 제대로오는지 확인하는것도 무척 중요해서 토큰도 확인해줘야함
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        Assertions.assertTrue(responseHeader.startsWith(JWTProvider.TOKEN_PREFIX));
    }

    @Test
    public void length_test(){
        String value = "Bearer eyJ0eX";
        System.out.println(value.substring(0,6));
    }

}
