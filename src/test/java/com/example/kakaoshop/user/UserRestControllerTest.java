package com.example.kakaoshop.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserRestControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("이메일 중복 체크 - 중복")
  void checkEmailDuplicate() throws Exception {
    String email = "ssar@nate.com";
    UserRequest.EmailDuplicateCheckDTO emailDuplicateCheckDTO = new UserRequest.EmailDuplicateCheckDTO();
    emailDuplicateCheckDTO.setEmail(email);

    ResultActions resultActions = mvc.perform(
        post("/user/email-duplicate-check")
          .contentType("application/json")
          .content(objectMapper.writeValueAsString(emailDuplicateCheckDTO))
    );

    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(jsonPath("$.error.status").value(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  void signUp() {
  }

  @Test
  void login() {
  }
}