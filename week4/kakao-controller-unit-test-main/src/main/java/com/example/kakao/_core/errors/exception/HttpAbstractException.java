package com.example.kakao._core.errors.exception;

import com.example.kakao._core.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public abstract class HttpAbstractException extends RuntimeException {
    protected HttpStatus httpStatus;

    public HttpAbstractException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), status());
    }

    public HttpStatus status(){
        return this.httpStatus;
    }
}
