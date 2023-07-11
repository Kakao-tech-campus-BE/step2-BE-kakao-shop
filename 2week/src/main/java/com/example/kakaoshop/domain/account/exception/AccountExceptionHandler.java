package com.example.kakaoshop.domain.account.exception;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.domain.account.AccountRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@Slf4j
@RestControllerAdvice(assignableTypes = {AccountRestController.class})
@Order(1)
public class AccountExceptionHandler {

  @ExceptionHandler(InternalAuthenticationServiceException.class)
  public ResponseEntity<Object> handleAuthenticationException(InternalAuthenticationServiceException ex){
    log.error("InternalAuthenticationServiceException",ex);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiUtils.error("이메일 또는 비밀번호가 일치하지 않습니다", HttpStatus.UNAUTHORIZED));
  }

  @ExceptionHandler(EmailDuplicateException.class)
  public ResponseEntity<Object> handleEmailDuplicateException(EmailDuplicateException ex){
    log.error("EmailDuplicateException",ex);
    return ResponseEntity.badRequest().body(ApiUtils.error("동일한 이메일이 존재합니다: email", HttpStatus.BAD_REQUEST));
  }

}
