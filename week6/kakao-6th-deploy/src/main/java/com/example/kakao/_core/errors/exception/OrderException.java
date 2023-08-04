package com.example.kakao._core.errors.exception;

public class OrderException {

    public static class OrderNotFoundException extends Exception404 {
        public OrderNotFoundException(int orderId) {
            super("존재하지 않는 주문입니다 : " + orderId);
        }
    }
}
