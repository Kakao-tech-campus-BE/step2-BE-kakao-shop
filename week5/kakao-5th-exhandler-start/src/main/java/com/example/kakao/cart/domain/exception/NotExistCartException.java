package com.example.kakao.cart.domain.exception;

import com.example.kakao._core.errors.exception.BusinessException;

public class NotExistCartException extends BusinessException {
    public NotExistCartException() {
        super(CartErrorCode.NOT_EXIST_CART);
    }
}
