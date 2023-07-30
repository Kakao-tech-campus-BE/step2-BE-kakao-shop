package com.example.kakao._core.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResult<T> extends ResponseEntity<T> {
    public ApiResult(T body, HttpStatus status) {
        super(body, status);
    }
}
