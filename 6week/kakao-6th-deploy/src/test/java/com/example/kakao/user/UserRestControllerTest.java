package com.example.kakao.user;


import com.example.kakao.MyRestDoc;
import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception401;
import com.example.kakao._core.security.JWTProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    @MockBean
    private UserService userService;

    @Test
    public void check_test() throws Exception {
        // given

        // DTO 생성
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("meta@nate.com");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post("/check")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = result.andReturn().getResponse().getContentAsString();
        // System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response").value(nullValue()));
        result.andExpect(jsonPath("$.error").value(nullValue()));
        result.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    // 회원가입 동일한 이메일 존재 에러
    @Test
    @Transactional
    public void checkError_test() throws Exception {
        // given
        String email = "ssarmango@nate.com";
        // DTO 생성
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail(email);

        String requestBody = om.writeValueAsString(requestDTO);

        doThrow(new Exception400("동일한 이메일이 존재합니다 : " + email)).when(userService).sameCheckEmail(any());

        // when
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post("/check")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(jsonPath("$.success").value("false"));
        result.andExpect(jsonPath("$.response").value(nullValue()));
        result.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : " + email));
        result.andExpect(jsonPath("$.error.status").value("400"));
        result.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    //이메일 형식 에러
    @Test
    public void checkError2_test() throws Exception {
        // given
        // DTO 생성
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("ssarnate.com");

        String requestBody = om.writeValueAsString(requestDTO);
        // when
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post("/check")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = result.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(jsonPath("$.success").value("false"));
        result.andExpect(jsonPath("$.response").value(nullValue()));
        result.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        result.andExpect(jsonPath("$.error.status").value("400"));
        result.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("meta@nate.com");
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
        //System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response").value(nullValue()));
        result.andExpect(jsonPath("$.error").value(nullValue()));
        result.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    //로그인시 email 형식 에러
    @Test
    public void joinError_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("metanate.com");
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
        // System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(jsonPath("$.success").value("false"));
        result.andExpect(jsonPath("$.response").value(nullValue()));
        result.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        result.andExpect(jsonPath("$.error.status").value("400"));
        result.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    //비밀번호 에러2
    @Test
    public void joinError2_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("meta@nate.com");
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
        // System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(jsonPath("$.success").value("false"));
        result.andExpect(jsonPath("$.response").value(nullValue()));
        result.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        result.andExpect(jsonPath("$.error.status").value("400"));
        result.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    //동일한 이메일 존재 에러
    @Test
    public void joinError3_test() throws Exception {
        // given
        String email = "ssarmango@nate.com";
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail(email);
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssarmango");

        String requestBody = om.writeValueAsString(requestDTO);

        doThrow(new Exception400("동일한 이메일이 존재합니다 : " + email)).when(userService).join(any());

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(jsonPath("$.success").value("false"));
        result.andExpect(jsonPath("$.response").value(nullValue()));
        result.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : " + email));
        result.andExpect(jsonPath("$.error.status").value("400"));
        result.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    //비밀번호 개수 에러4
    @Test
    public void joinError4_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("meta@nate.com");
        requestDTO.setPassword("meta12!");
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
        //System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(jsonPath("$.success").value("false"));
        result.andExpect(jsonPath("$.response").value(nullValue()));
        result.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        result.andExpect(jsonPath("$.error.status").value("400"));
        result.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");

        User user = User.builder()
                .id(1)
                .roles("ROLE_USER")
                .build();

        String requestBody = om.writeValueAsString(requestDTO);

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
        //System.out.println("테스트 : "+responseBody);
        //System.out.println("테스트 : "+responseHeader);

        // then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response").value(nullValue()));
        result.andExpect(jsonPath("$.error").value(nullValue()));
        Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
        result.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 에러
    @Test
    public void loginError_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmangonate.com");
        requestDTO.setPassword("meta1234!");

        User user = User.builder()
                .id(1)
                .roles("ROLE_USER")
                .build();

        String requestBody = om.writeValueAsString(requestDTO);


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
        //System.out.println("테스트 : "+responseBody);
        //System.out.println("테스트 : "+responseHeader);

        // then
        result.andExpect(jsonPath("$.success").value("false"));
        result.andExpect(jsonPath("$.response").value(nullValue()));
        result.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        result.andExpect(jsonPath("$.error.status").value("400"));
        result.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    //비밀번호 에러2
    @Test
    public void loginError2_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234");

        User user = User.builder()
                .id(1)
                .roles("ROLE_USER")
                .build();

        String requestBody = om.writeValueAsString(requestDTO);

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
        //System.out.println("테스트 : "+responseBody);
        //System.out.println("테스트 : "+responseHeader);

        // then
        result.andExpect(jsonPath("$.success").value("false"));
        result.andExpect(jsonPath("$.response").value(nullValue()));
        result.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        result.andExpect(jsonPath("$.error.status").value("400"));
        result.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 인증 에러3
    @Test
    public void loginError3_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssar1111@nate.com");
        requestDTO.setPassword("meta1234!");

        User user = User.builder()
                .id(1)
                .roles("ROLE_USER")
                .build();

        String requestBody = om.writeValueAsString(requestDTO);

        doThrow(new Exception401("인증되지 않았습니다")).when(userService).login(any());

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        //System.out.println("테스트 : "+responseBody);
        //System.out.println("테스트 : "+responseHeader);

        // then
        result.andExpect(jsonPath("$.success").value("false"));
        result.andExpect(jsonPath("$.response").value(nullValue()));
        result.andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"));
        result.andExpect(jsonPath("$.error.status").value("401"));
        result.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}