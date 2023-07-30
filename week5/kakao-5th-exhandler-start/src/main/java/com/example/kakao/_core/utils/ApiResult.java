package com.example.kakao._core.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.kakao._core.utils.ResponseBody.*;

public class ApiResult<T> extends ResponseEntity<T> {
    public ApiResult(T body, HttpStatus status) {
        super(body, status);
    }

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult(new Body<T>(false, response, null), HttpStatus.OK);
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult(new Body<T>(false, null, null), HttpStatus.OK);
    }

    public static ApiResult error(String message, HttpStatus status) {
        return new ApiResult<>(new Body(false, null, new FailBody(message, status.value())),status);
    }
}
