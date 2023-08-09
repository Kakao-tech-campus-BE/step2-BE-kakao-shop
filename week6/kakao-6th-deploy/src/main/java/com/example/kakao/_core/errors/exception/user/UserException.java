package com.example.kakao._core.errors.exception.user;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;

public class UserException {
    public static class UserSaveException extends Exception500 {
        public UserSaveException(String message) {
            super("회원가입 중 오류가 발생했습니다. : " + message);
        }
    }

    public static class UserNotFoundByEmailException extends Exception404 {
        public UserNotFoundByEmailException(String email) {
            super("이메일을 찾을 수 없습니다 : " + email );
        }
    }

    public static class UserPasswordMismatchException extends Exception400 {
        public UserPasswordMismatchException() {
            super("비밀번호가 일치하지 않습니다.");
        }
    }

    public static class SameEmailExistException extends Exception400 {
        public SameEmailExistException(String email) {
            super("동일한 이메일이 존재합니다 : " + email);
        }
    }

}
