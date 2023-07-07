package com.example.kakaoshop.domain.account;

import com.example.kakaoshop._core.security.CustomUserDetails;
import com.example.kakaoshop.domain.account.Account;
import com.example.kakaoshop.domain.account.AccountService;
import com.example.kakaoshop.domain.account.exception.EmailDuplicateException;
import com.example.kakaoshop.domain.account.request.AccountRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
class AccountRestControllerTest {

  private final String email = "ssar@nate.com";

  @Autowired
  private MockMvc mvc;

  @MockBean
  private AccountService accountService;

  @MockBean
  private AuthenticationManager authenticationManager;

  @Mock
  private Authentication authentication;

  @Autowired
  private ObjectMapper objectMapper;

  @Nested
  @DisplayName("이메일 중복 체크")
  class testCheckEmailDuplicate {
    @Test
    @DisplayName("이메일 중복 체크 - 중복 되지 않음")
    void testSuccess() throws Exception {
      willDoNothing().given(accountService).checkEmailDuplicate(email);

      ResultActions resultActions = mvc.perform(
        get("/account/email-use-permission")
          .param("email", email)
      );

      resultActions.andExpect(status().isOk());
      resultActions.andExpect(jsonPath("$.success").value("true"));
    }
    @Test
    @DisplayName("이메일 중복 체크 - 중복")
    void testFail() throws Exception {
      willThrow(new EmailDuplicateException()).given(accountService).checkEmailDuplicate(email);

      ResultActions resultActions = mvc.perform(
        get("/account/email-use-permission")
          .param("email", email)
      );

      resultActions.andExpect(status().isBadRequest());
      resultActions.andExpect(jsonPath("$.success").value("false"));
      resultActions.andExpect(jsonPath("$.error.status").value(HttpStatus.BAD_REQUEST.value()));
    }
  }

  @Test
  @DisplayName("회원가입 - 성공")
  void testSignUp() throws Exception {
    String password = "12345678";
    String username = "abcd";

    ResultActions resultActions = mvc.perform(
        post("/account/sign-up")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(
            AccountRequestDto.SignUpDto.builder()
              .email(email)
              .password(password)
              .username(username)
              .build()
          ))
    );

    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.success").value("true"));
  }


  @Nested
  @DisplayName("로그인")
  class testLogin {
    @Test
    @DisplayName("로그인 - Success")
    void testLoginSuccess() throws Exception {
      // Mock the authentication manager to return successful authentication
      Account account = Account.builder()
        .email("user@example.com")
        .password("password")
        .build();

      given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .willReturn(authentication);

      given(authentication.getPrincipal()).willReturn(new CustomUserDetails(account));

      // Create the login request DTO
      AccountRequestDto.LoginDto loginDto = AccountRequestDto.LoginDto.builder()
        .email("user@example.com")
        .password("password")
        .build();

      // Perform the login request
      ResultActions resultActions = mvc.perform(post("/account/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginDto)));

      // Assert the response
      resultActions.andExpect(status().isOk())
        .andExpect(header().exists("Authorization"))
        .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("로그인 - Invalid credentials")
    void testLoginInvalidCredentials() throws Exception {
      // Mock the authentication manager to throw an exception (invalid credentials)
      given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .willThrow(InternalAuthenticationServiceException.class);

      // Create the login request DTO
      AccountRequestDto.LoginDto loginDto = AccountRequestDto.LoginDto.builder()
        .email("user@example.com")
        .password("wrongpassword")
        .build();

      // Perform the login request
      ResultActions resultActions = mvc.perform(post("/account/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginDto)));

      // Assert the response
      resultActions.andExpect(status().isUnauthorized())
        .andExpect(header().doesNotExist("Authorization"))
        .andExpect(jsonPath("$.success").value(false));
    }
  }
}