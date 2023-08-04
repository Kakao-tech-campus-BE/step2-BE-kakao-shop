package com.example.kakao.product.domain.exception;

import com.example.kakao._core.errors.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ProductErrorCode implements ErrorCode {
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "해당하는 상품이 없습니다.");

    ProductErrorCode(HttpStatus status, String message) {
        this.status=status;
        this.message=message;
    }
    private final HttpStatus status;
    private final String message;

    public HttpStatus getStatus() {
        return status;
    }
    public String getMessage(){
        return message;
    }
}
