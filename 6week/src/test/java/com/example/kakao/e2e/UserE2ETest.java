package com.example.kakao.e2e;

import com.example.kakao.E2ETest;
import com.example.kakao.domain.user.UserRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

class UserE2ETest extends E2ETest {
  private String existEmail = "ssar@nate.com";
  private String nonExistEmail = "anonymous@gmail.com";
  private String existPassword = "meta1234!";

  @Nested
  class 이메일_중복체크 {
    @Test
    void 성공_이메일중복체크() throws Exception {
      UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
      requestDTO.setEmail(nonExistEmail);

      resultActions = mvc.perform(
        post("/check")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTO)));

      expectSuccess();
    }

    @Test
    void 실패_이메일중복체크_이메일양식을지키지않음() throws Exception {
      UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
      requestDTO.setEmail("anonymousgmail.com");

      resultActions = mvc.perform(
        post("/check")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTO)));

      expectFail(400);
    }

    @Test
    void 실패_이메일중복체크_이메일이중복됨() throws Exception {
      UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
      requestDTO.setEmail(existEmail);

      resultActions = mvc.perform(
        post("/check")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTO)));

      expectFail(400);
    }


  }

  @Nested
  class 회원가입 {
    private String validPassword = "1234abcd!!";
    private String validUserName = "AnonymousUser";


    @Test
    void 성공_회원가입() throws Exception {
      UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
      requestDTO.setEmail(nonExistEmail);
      requestDTO.setPassword(validPassword);
      requestDTO.setUsername(validUserName);

      resultActions = mvc.perform(
        post("/join")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTO)));

      expectSuccess();
    }

    @Test
    void 실패_회원가입_이메일양식을지키지않음() throws Exception {
      UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
      requestDTO.setEmail("anonymousgmail.com");
      requestDTO.setPassword(validPassword);
      requestDTO.setUsername(validUserName);

      processFail(requestDTO);
    }

    @Test
    void 실패_회원가입_비밀번호양식을지키지않음() throws Exception {
      UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
      requestDTO.setEmail(nonExistEmail);
      requestDTO.setPassword("1234abcd");
      requestDTO.setUsername(validUserName);

      processFail(requestDTO);
    }

    @Test
    void 실패_회원가입_유저이름길이규칙을지키지않음() throws Exception {
      UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
      requestDTO.setEmail(nonExistEmail);
      requestDTO.setPassword(validPassword);
      requestDTO.setUsername("Anon");

      processFail(requestDTO);
    }

    @Test
    void 실패_회원가입_이메일이중복됨() throws Exception {
      UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
      requestDTO.setEmail(existEmail);
      requestDTO.setPassword(validPassword);
      requestDTO.setUsername(validUserName);

      processFail(requestDTO);
    }

    private void processFail(UserRequest.JoinDTO requestDTO) throws Exception {
      resultActions = mvc.perform(
        post("/join")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTO)));

      expectFail(400);
    }

  }

  @Nested
  class 로그인 {
    @Test
    void 성공_로그인() throws Exception {
      UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
      requestDTO.setEmail(existEmail);
      requestDTO.setPassword(existPassword);

      resultActions = mvc.perform(
        post("/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTO)));

      expectSuccess();
      resultActions
        .andExpect(header().exists("Authorization"));
    }

    @Test
    void 실패_존재하지않는이메일() throws Exception {
      UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
      requestDTO.setEmail(nonExistEmail);
      requestDTO.setPassword(existPassword);

      resultActions = mvc.perform(
        post("/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTO)));

      expectFail(400);
    }

    @Test
    void 실패_패스워드틀림() throws Exception {
      UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
      requestDTO.setEmail(existEmail);
      requestDTO.setPassword("1234abcd!!");

      resultActions = mvc.perform(
        post("/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTO)));

      expectFail(400);
    }

  }
}
