package com.example.kakao._core.errors.exception;

import com.example.kakao._core.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public abstract class HttpStatusException extends RuntimeException{
    private HttpStatus httpStatus;
    public HttpStatusException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), status());
    }

    public HttpStatus status(){
        return this.httpStatus;
    }
}
