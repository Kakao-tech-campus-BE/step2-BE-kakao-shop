package com.example.kakao._core.errors.exception;

import com.example.kakao._core.utils.ApiResponse;
import com.example.kakao._core.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException{
  private final HttpStatus status;

  public BaseException(String message, HttpStatus status){
    super(message);
    this.status = status;
  }
  public ApiResponse body(){
    return ApiUtils.error(getMessage(), this.status);
  }

  public HttpStatus status(){
    return this.status;
  }
}
