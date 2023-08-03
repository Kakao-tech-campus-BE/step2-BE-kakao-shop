package com.example.kakao._core.errors.exception.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;

import java.util.List;

public class CartException {
    public static class CartNotFoundException extends Exception404 {
        public CartNotFoundException(int id) {
            super("존재하지 않는 장바구니입니다.: " + id);
        }
        public CartNotFoundException() {
            super("회원님의 장바구니가 비어있습니다.");
        }
    }

    public static class DuplicatedCartException extends Exception400 {
        public DuplicatedCartException() {
            super("중복된 장바구니가 입력되었습니다.");
        }
    }

    public static class NotExistsUserCartsException extends Exception400 {
        public NotExistsUserCartsException() {
            super("유저 장바구니에 없는 cartId가 입력되었습니다.");
        }
        public NotExistsUserCartsException(List<Integer> notExistCartIds) {
            super("유저 장바구니에 없는 cartId가 입력되었습니다. : " + notExistCartIds );
        }
    }

    public static class CartSaveException extends Exception500 {
        public CartSaveException(String message) {
            super("장바구니 저장 중 오류가 발생했습니다.: " + message);
        }
    }

    public static class CartUpdateException extends Exception500 {
        public CartUpdateException(String message) {
            super("장바구니 수정 중 오류가 발생했습니다.: " + message);
        }
    }

    public static class CartDeleteException extends Exception500 {
        public CartDeleteException(String message) {
            super("장바구니 삭제 중 오류가 발생했습니다.: " + message);
        }
    }
}
