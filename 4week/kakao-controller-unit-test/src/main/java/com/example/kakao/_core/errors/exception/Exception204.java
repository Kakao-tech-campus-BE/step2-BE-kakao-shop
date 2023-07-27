package com.example.kakao._core.errors.exception;

import com.example.kakao._core.utils.ApiUtils;
import org.springframework.http.HttpStatus;


public class Exception204 extends RuntimeException{
    public Exception204(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.success(getMessage());
    }

    public HttpStatus status(){
        return HttpStatus.NO_CONTENT;
    }
}
