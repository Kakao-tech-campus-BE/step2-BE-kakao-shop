package com.example.kakao._core.errors.exception.order.Item;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;

public class ItemException {

    public static class ItemNotFoundException extends Exception404 {
        public ItemNotFoundException() {super("주문상품이 존재하지 않습니다.");}
    }

    public static class ItemSaveException extends Exception500 {
        public ItemSaveException(String message) {
            super("주문상품 저장 과정에서 오류가 발생했습니다. : " + message);
        }
    }
}
