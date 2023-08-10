package com.example.kakao.user;

import com.example.kakao._core.errors.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND,"이메일을 찾을 수가 없습니다"),
    NOT_FOUND_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 찾을 수가 없습니다"),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST,"이메일이 중복됩니다.");

    private final HttpStatus status;
    private final String message;

    UserErrorCode(HttpStatus status, String message) {
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
