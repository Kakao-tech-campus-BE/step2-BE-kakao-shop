package com.example.kakao._core.errors.exception;

public class CartException {

    public static class CartNotFoundException extends Exception404 {
        public CartNotFoundException(int cartId) {
            super("존재하지 않는 장바구니입니다 : " + cartId);
        }
    }

    public static class DuplicateCartException extends Exception400 {
        public DuplicateCartException() {
            super("동일한 장바구니를 하나의 요청에 담았습니다");
        }
    }

    public static class DuplicateOptionException extends Exception400 {
        public DuplicateOptionException() {
            super("동일한 옵션을 하나의 요청에 담았습니다");
        }
    }

    public static class EmptyCartException extends Exception404 {
        public EmptyCartException() {
            super("장바구니에 상품이 존재하지 않습니다.");
        }
    }
}
