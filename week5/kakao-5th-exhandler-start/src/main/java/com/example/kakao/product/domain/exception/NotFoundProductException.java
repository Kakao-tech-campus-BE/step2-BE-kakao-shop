package com.example.kakao.product.domain.exception;

import com.example.kakao._core.errors.exception.BusinessException;

public class NotFoundProductException extends BusinessException {
    public  NotFoundProductException() {
        super(ProductErrorCode.NOT_FOUND_PRODUCT);
    }

}
