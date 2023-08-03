package com.example.kakao._core.errors;

import lombok.Getter;

/**
 * 현재 프로젝트에서는 열거형으로 사용하는 것보다 Exception4xx, 5xx를 상속받는
 * CustomException 클래스를 만드는 것이 좋다고 판단해 더 이상 구현하지 않았습니다.
 */
@Getter
public enum ExceptionStatus {

    // Cart
    CART_EMPTY("회원님의 장바구니가 존재하지 않습니다.", 404),
    CART_DUPLICATE("중복된 장바구니가 입력되었습니다.", 401),
    CART_INSERT_FAIL("장바구니 등록 실패", 500),
    CART_UPDATE_FAIL("장바구니 수정 실패", 500),
    CART_DELETE_FAIL("장바구니 삭제 실패", 500);

    private final String message;
    private final int code;

    private ExceptionStatus(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
