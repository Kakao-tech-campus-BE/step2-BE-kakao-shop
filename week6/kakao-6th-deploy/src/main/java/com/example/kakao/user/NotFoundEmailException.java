package com.example.kakao.user;

import com.example.kakao._core.errors.exception.BusinessException;
import com.example.kakao._core.errors.exception.ErrorCode;

public class NotFoundEmailException extends BusinessException {
    public NotFoundEmailException() {
        super(UserErrorCode.NOT_FOUND_EMAIL);
    }
}
