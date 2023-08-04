package com.example.kakao._core.security;

import com.example.kakao._core.errors.exception.BusinessException;

public class ForbiddenException extends BusinessException {
    public ForbiddenException() {
        super(SecurityErrorCode.FORBIDDEN_EXCEPTION);
    }
}
