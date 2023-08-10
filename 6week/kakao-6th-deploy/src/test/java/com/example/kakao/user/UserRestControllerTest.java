package com.example.kakao.user;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @Test
    public void checkEmail_test() throws Exception {
        // given
    	UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
    	requestDTO.setEmail("cosmos123@nate.com");
    	
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    
    // 같은 이메일이 이미 존재할 때
    @Test
    public void checkEmailFailure1_test() throws Exception {
        // given
    	UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
    	requestDTO.setEmail("ssarmango@nate.com");
    	
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    
    // 제출한 이메일이 이메일 형식에 맞지않을 때
    @Test
    public void checkEmailFailure2_test() throws Exception {
        // given
    	UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
    	requestDTO.setEmail("codenate.com");
    	
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    
    @Test
    public void join_test() throws Exception {
        // given teardown
    	UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
    	requestDTO.setEmail("cos@nate.com");
    	requestDTO.setPassword("cos1234!");
    	requestDTO.setUsername("cosmos123");

    	String requestBody = om.writeValueAsString(requestDTO);
    	
        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    
    // 회원가입 이메일이 이메일 형식에 맞지않을 때
    @Test
    public void join_failure1_test() throws Exception {
        // given teardown
    	UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
    	requestDTO.setEmail("cosnate.com");
    	requestDTO.setPassword("cos1234!");
    	requestDTO.setUsername("cosmos123");

    	String requestBody = om.writeValueAsString(requestDTO);
    	
        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    
    // 비밀번호가 규칙에 맞지 않을 때
    @Test
    public void join_failure2_test() throws Exception {
        // given teardown
    	UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
    	requestDTO.setEmail("cos@nate.com");
    	requestDTO.setPassword("cos12345");
    	requestDTO.setUsername("cosmos123");

    	String requestBody = om.writeValueAsString(requestDTO);
    	
        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    
    // 동일한 이메일이 이미 존재할 때
    @Test
    public void join_failure3_test() throws Exception {
        // given teardown
    	UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
    	requestDTO.setEmail("ssarmango@nate.com");
    	requestDTO.setPassword("meta1234!");
    	requestDTO.setUsername("ssarmango");

    	String requestBody = om.writeValueAsString(requestDTO);
    	
        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    
    // 비밀번호 개수가 맞지 않을 때
    @Test
    public void join_failure4_test() throws Exception {
        // given teardown
    	UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
    	requestDTO.setEmail("cos@nate.com");
    	requestDTO.setPassword("meta12!");
    	requestDTO.setUsername("cosmos123");

    	String requestBody = om.writeValueAsString(requestDTO);
    	
        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    
    
    @Test
    public void login_test() throws Exception {
        // given
    	UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
    	requestDTO.setEmail("ssarmango@nate.com");
    	requestDTO.setPassword("meta1234!");

    	String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 이메일 형식이 아닐 때
    @Test
    public void login_failure1_test() throws Exception {
        // given
    	UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
    	requestDTO.setEmail("ssarnate.com");
    	requestDTO.setPassword("meta1234!");

    	String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        
        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    
    // 로그인 시에 비밀번호가 규칙에 맞지 않을 때
    @Test
    public void login_failure2_test() throws Exception {
        // given
    	UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
    	requestDTO.setEmail("ssarmango@nate.com");
    	requestDTO.setPassword("meta1234");

    	String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        
        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    
    // 가입되지 않은 이메일로 로그인을 시도할 때
    @Test
    public void login_failure3_test() throws Exception {
        // given
    	UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
    	requestDTO.setEmail("ssar@nate.com");
    	requestDTO.setPassword("meta1234!");

    	String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        
        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : ssar@nate.com"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    
    
    // 비밀번호 개수가 맞지 않을 때
    @Test
    public void login_failure4_test() throws Exception {
        // given
    	UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
    	requestDTO.setEmail("ssarmango@nate.com");
    	requestDTO.setPassword("meta12!");

    	String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        
        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    
    // 비밀번호를 잘못 입력했을 때
    @Test
    public void login_failure5_test() throws Exception {
        // given
    	UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
    	requestDTO.setEmail("ssarmango@nate.com");
    	requestDTO.setPassword("meta1234!@");

    	String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        
        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("패스워드가 잘못입력되었습니다 "));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}