package com.example.kakaoshop.domain.account.exception;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.domain.account.AccountRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {AccountRestController.class})
@Order(1)
public class AccountExceptionHandler {

  @ExceptionHandler(EmailDuplicateException.class)
  public ResponseEntity<?> handleEmailDuplicateException(EmailDuplicateException ex){
    log.error("EmailDuplicateException",ex);
    return ResponseEntity.badRequest().body(ApiUtils.error("동일한 이메일이 존재합니다: email", HttpStatus.BAD_REQUEST));
  }

}
