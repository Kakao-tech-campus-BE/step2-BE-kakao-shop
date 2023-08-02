package com.example.kakao._core.errors.exception.product;

import com.example.kakao._core.errors.exception.Exception404;

public class ProductException {
    public static class ProductNotFoundException extends Exception404 {
        public ProductNotFoundException(int id) {
            super("해당 상품을 찾을 수 없습니다 : " + id);
        }
    }
}
