package com.example.kakao.cart.domain.exception;

import com.example.kakao._core.errors.exception.BusinessException;

public class EmptyCartException extends BusinessException {
    public EmptyCartException() {
        super(CartErrorCode.EMPTY_CART);
    }
}
