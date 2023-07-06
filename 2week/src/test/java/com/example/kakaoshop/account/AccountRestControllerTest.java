package com.example.kakaoshop.account;

import com.example.kakaoshop.domain.account.Account;
import com.example.kakaoshop.domain.account.AccountJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AccountRestControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private AccountJPARepository userRepository;

  @Test
  @DisplayName("이메일 중복 체크 - 중복")
  @Transactional @Rollback
  void checkEmailDuplicate() throws Exception {
    String email = "ssar@nate.com";
    userRepository.save(
      Account.builder()
        .email(email)
        .password("1234")
        .username("ssar")
        .roles("ROLE_USER")
        .build()
    );

    ResultActions resultActions = mvc.perform(
        post("/user/email-duplicate-check")
          .param("email", email)
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