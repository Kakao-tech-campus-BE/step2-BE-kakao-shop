package com.example.kakao.cart.domain.exception;

import com.example.kakao._core.errors.exception.BusinessException;

public class DuplicatedCartRequestException extends BusinessException {
    public DuplicatedCartRequestException() {
        super(CartErrorCode.DUPLICATED_REQUEST);
    }
}
