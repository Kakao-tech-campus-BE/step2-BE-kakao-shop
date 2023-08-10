package com.example.kakao.cart.domain.exception;

import com.example.kakao._core.errors.exception.BusinessException;

public class NotExistOptionException extends BusinessException {
    public NotExistOptionException() {
        super(CartErrorCode.NOT_EXISTS_OPTION);
    }
}
