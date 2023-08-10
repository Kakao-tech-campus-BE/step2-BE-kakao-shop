package com.example.kakao.user;

import com.example.kakao._core.errors.exception.BusinessException;
import com.example.kakao._core.errors.exception.ErrorCode;

public class NotFoundPasswordException extends BusinessException {
    public NotFoundPasswordException() {
        super(UserErrorCode.NOT_FOUND_PASSWORD);
    }
}
