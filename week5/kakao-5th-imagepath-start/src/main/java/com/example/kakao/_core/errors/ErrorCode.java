package com.example.kakao._core.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Cart
    CART_ITEM_NOT_FOUND(BAD_REQUEST, "해당 상품은 장바구니에 존재하지 않아 수정할 수 없습니다."),
    DUPLICATE_CART_ITEMS(BAD_REQUEST, "중복된 장바구니 아이템을 하나의 요청에 담았습니다."),
    DUPLICATE_CART_OPTIONS(BAD_REQUEST, "중복된 장바구니 옵션을 하나의 요청에 담았습니다."),
    EMPTY_CART(NOT_FOUND, "장바구니에 상품이 존재하지 않습니다."),

    // Option
    OPTION_NOT_FOUND(NOT_FOUND, "존재하지 않는 옵션입니다."),

    // Order
    ORDER_NOT_FOUND(NOT_FOUND, "존재하지 않는 주문입니다."),

    // User
    EMAIL_NOT_FOUND(NOT_FOUND, "이메일을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
