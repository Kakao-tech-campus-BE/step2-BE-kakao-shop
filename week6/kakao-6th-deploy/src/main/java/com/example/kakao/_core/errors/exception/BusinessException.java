package com.example.kakao._core.errors.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final transient ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
