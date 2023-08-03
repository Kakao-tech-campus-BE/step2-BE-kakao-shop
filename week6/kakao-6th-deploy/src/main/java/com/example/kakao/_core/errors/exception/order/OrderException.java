package com.example.kakao._core.errors.exception.order;

import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;

public class OrderException {


    public static class OrderNotFoundException extends Exception404{
        public OrderNotFoundException(int id) {
            super("해당하는 주문을 찾을 수 없습니다. : " + id);
        }
    }

    public static class ForbiddenOrderAccess extends Exception403 {
        public ForbiddenOrderAccess() {
            super("허용되지 않은 접근입니다.");
        }
    }

    public static class OrderSaveException extends Exception500 {
        public OrderSaveException(String message) {
            super("주문 저장 과정에서 오류가 발생했습니다. : " + message);
        }
    }

}
