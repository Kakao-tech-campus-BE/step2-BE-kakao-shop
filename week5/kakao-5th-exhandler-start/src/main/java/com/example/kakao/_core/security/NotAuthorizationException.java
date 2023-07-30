package com.example.kakao._core.security;

import com.example.kakao._core.errors.exception.BusinessException;

public class NotAuthorizationException extends BusinessException {
    public NotAuthorizationException() {
        super(SecurityErrorCode.NOT_AUTHORIZATION_EXCEPTION);
    }
}
