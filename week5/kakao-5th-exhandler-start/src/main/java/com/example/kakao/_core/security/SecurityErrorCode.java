package com.example.kakao._core.security;

import com.example.kakao._core.errors.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum SecurityErrorCode implements ErrorCode {
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    NOT_AUTHORIZATION_EXCEPTION(HttpStatus.UNAUTHORIZED, "인증되지 않았습니다");
    private final HttpStatus status;
    private final String message;

    SecurityErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
