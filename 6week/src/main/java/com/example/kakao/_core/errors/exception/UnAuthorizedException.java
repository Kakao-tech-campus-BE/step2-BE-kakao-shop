package com.example.kakao._core.errors.exception;


import org.springframework.http.HttpStatus;


// 인증 안됨
public class UnAuthorizedException extends BaseException {
  public UnAuthorizedException(String message) {
    super(message, HttpStatus.UNAUTHORIZED);
  }
}