package com.example.kakao._core.errors.exception;

import com.example.kakao._core.utils.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class MethodArgumentNotValidExceptionCustom extends RuntimeException{
    public MethodArgumentNotValidExceptionCustom(MethodArgumentNotValidException message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    public HttpStatus status(){
        return HttpStatus.METHOD_NOT_ALLOWED;
    }
}
