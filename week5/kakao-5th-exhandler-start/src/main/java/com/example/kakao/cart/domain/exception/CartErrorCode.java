package com.example.kakao.cart.domain.exception;

import com.example.kakao._core.errors.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CartErrorCode implements ErrorCode {
    NOT_EXIST_CART(HttpStatus.NOT_FOUND, "존재하지 않는 장바구니 번호입니다."),
    EMPTY_CART(HttpStatus.BAD_REQUEST, "장바구니에 아무런 상품이 존재하지 않습니다."),
    NOT_EXISTS_OPTION_IN_CART(HttpStatus.NOT_FOUND, "장바구니에 해당 옵션이 존재하지 않습니다")
    ;

    private final HttpStatus status;
    private final String message;

    CartErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
