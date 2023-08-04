package com.example.kakao.order.domain.exception;

import com.example.kakao._core.errors.exception.BusinessException;

public class NotExistOrderException extends BusinessException {
    public NotExistOrderException() {
        super(OrderErrorCode.ORDER_NOT_FOUND);
    }
}
