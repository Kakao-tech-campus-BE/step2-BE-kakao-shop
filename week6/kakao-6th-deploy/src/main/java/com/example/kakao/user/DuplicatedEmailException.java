package com.example.kakao.user;

import com.example.kakao._core.errors.exception.BusinessException;
import com.example.kakao._core.errors.exception.ErrorCode;

public class DuplicatedEmailException extends BusinessException {
    public DuplicatedEmailException() {
        super(UserErrorCode.DUPLICATED_EMAIL);
    }
}
