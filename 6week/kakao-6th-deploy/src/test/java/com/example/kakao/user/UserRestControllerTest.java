package com.example.kakao.user;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
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

  // 회원가입 성공 테스트
  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void join_test() throws Exception {
    // given
    UserRequest.JoinDTO requestDTOs = new UserRequest.JoinDTO();
    requestDTOs.setUsername("ssarmango");
    requestDTOs.setEmail("ssarmangoo@nate.com");
    requestDTOs.setPassword("meta1234!");

    String requestBody = om.writeValueAsString(requestDTOs);

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

  // 회원가입 실패 테스트
  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void join_format_fail_test() throws Exception {
    // given
    UserRequest.JoinDTO requestDTOs = new UserRequest.JoinDTO();
    requestDTOs.setUsername("ssarmango");
    requestDTOs.setEmail("ssarmango.com");
    requestDTOs.setPassword("meta1234!");

    String requestBody = om.writeValueAsString(requestDTOs);

    // when
    ResultActions resultActions = mvc.perform(
        post("/join")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    // verify
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }

  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void join_password_fail_test() throws Exception {
    // given
    UserRequest.JoinDTO requestDTOs = new UserRequest.JoinDTO();
    requestDTOs.setUsername("ssarmango");
    requestDTOs.setEmail("ssarmango@nate.com");
    requestDTOs.setPassword("meta1234");

    String requestBody = om.writeValueAsString(requestDTOs);

    // when
    ResultActions resultActions = mvc.perform(
        post("/join")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    // verify
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(
        jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }

  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void join_sameEmail_fail_test() throws Exception {
    // given
    UserRequest.JoinDTO requestDTOs = new UserRequest.JoinDTO();
    requestDTOs.setUsername("ssarmango");
    requestDTOs.setEmail("ssarmango@nate.com");
    requestDTOs.setPassword("meta1234!");

    String requestBody = om.writeValueAsString(requestDTOs);

    // when
    ResultActions resultActions = mvc.perform(
        post("/join")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    // verify
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(
        jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : " + requestDTOs.getEmail()));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }

  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void join_password_length_fail_test() throws Exception {
    // given
    UserRequest.JoinDTO requestDTOs = new UserRequest.JoinDTO();
    requestDTOs.setUsername("ssarmango");
    requestDTOs.setEmail("ssarmangoo@nate.com");
    requestDTOs.setPassword("meta12!");

    String requestBody = om.writeValueAsString(requestDTOs);

    // when
    ResultActions resultActions = mvc.perform(
        post("/join")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    // verify
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }

  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void join_name_length_fail_test() throws Exception {
    // given
    UserRequest.JoinDTO requestDTOs = new UserRequest.JoinDTO();
    requestDTOs.setUsername("ssar");
    requestDTOs.setEmail("ssarmangoo@nate.com");
    requestDTOs.setPassword("meta1234!");

    String requestBody = om.writeValueAsString(requestDTOs);

    // when
    ResultActions resultActions = mvc.perform(
        post("/join")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    // verify
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(jsonPath("$.error.message").value("8에서 45자 이내여야 합니다.:username"));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }

  // 로그인 성공 테스트
  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void login_test() throws Exception {
    // given
    UserRequest.LoginDTO requestDTOs = new UserRequest.LoginDTO();
    requestDTOs.setEmail("ssarmango@nate.com");
    requestDTOs.setPassword("meta1234!");

    String requestBody = om.writeValueAsString(requestDTOs);

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

  // 로그인 실패 테스트
  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void login_format_fail_test() throws Exception {
    // given
    UserRequest.LoginDTO requestDTOs = new UserRequest.LoginDTO();
    requestDTOs.setEmail("ssarmango.com");
    requestDTOs.setPassword("meta1234!");

    String requestBody = om.writeValueAsString(requestDTOs);

    // when
    ResultActions resultActions = mvc.perform(
        post("/login")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    // verify
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }

  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void login_password_fail_test() throws Exception {
    // given
    UserRequest.LoginDTO requestDTOs = new UserRequest.LoginDTO();
    requestDTOs.setEmail("ssarmango@nate.com");
    requestDTOs.setPassword("meta1234");

    String requestBody = om.writeValueAsString(requestDTOs);

    // when
    ResultActions resultActions = mvc.perform(
        post("/login")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    // verify
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(
        jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }

  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void login_password_format_fail_test() throws Exception {
    // given
    UserRequest.LoginDTO requestDTOs = new UserRequest.LoginDTO();
    requestDTOs.setEmail("ssarmango1@nate.com");
    requestDTOs.setPassword("meta12!");

    String requestBody = om.writeValueAsString(requestDTOs);

    // when
    ResultActions resultActions = mvc.perform(
        post("/login")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    // verify
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }

  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void login_Auth_fail_test() throws Exception {
    // given
    UserRequest.LoginDTO requestDTOs = new UserRequest.LoginDTO();
    requestDTOs.setEmail("ssarmango1@nate.com");
    requestDTOs.setPassword("meta1234!");

    String requestBody = om.writeValueAsString(requestDTOs);

    // when
    ResultActions resultActions = mvc.perform(
        post("/login")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    // verify
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(
        jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : " + requestDTOs.getEmail()));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }


  // 이메일 중복 체크 성공 테스트
  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void sameCheckEmail_test() throws Exception {
    // given
    UserRequest.EmailCheckDTO requestDTOs = new UserRequest.EmailCheckDTO();
    requestDTOs.setEmail("ssarmangoo@nate.com");

    String requestBody = om.writeValueAsString(requestDTOs);

    // when
    ResultActions resultActions = mvc.perform(
        post("/check")
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

  // 이메일 중복 체크 실패 테스트
  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void sameCheckEmail_same_fail_test() throws Exception {
    // given
    UserRequest.EmailCheckDTO requestDTOs = new UserRequest.EmailCheckDTO();
    requestDTOs.setEmail("ssarmango@nate.com");

    String requestBody = om.writeValueAsString(requestDTOs);

    // when
    ResultActions resultActions = mvc.perform(
        post("/check")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    // verify
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(
        jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : " + requestDTOs.getEmail()));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }

  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void sameCheckEmail_format_fail_test() throws Exception {
    // given
    UserRequest.EmailCheckDTO requestDTOs = new UserRequest.EmailCheckDTO();
    requestDTOs.setEmail("ssarmango.com");

    String requestBody = om.writeValueAsString(requestDTOs);

    // when
    ResultActions resultActions = mvc.perform(
        post("/check")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    // verify
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }

}
