package com.example.kakao._core.errors.exception;

import org.springframework.http.HttpStatus;


// 권한 없음
public class ForbiddenException extends BaseException {
  public ForbiddenException(String message) {
    super(message, HttpStatus.FORBIDDEN);
  }
}