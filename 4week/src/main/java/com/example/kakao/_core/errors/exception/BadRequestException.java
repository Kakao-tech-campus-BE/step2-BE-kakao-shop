package com.example.kakao._core.errors.exception;

import org.springframework.http.HttpStatus;


// 유효성 검사 실패, 잘못된 파라메터 요청
public class BadRequestException extends BaseException {
  public BadRequestException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }
}