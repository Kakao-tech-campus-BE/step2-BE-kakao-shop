package com.example.kakao._core.errors.exception;

public class OptionException {

    public static class OptionNotFoundException extends Exception404 {
        public OptionNotFoundException(int optionId) {
            super("존재하지 않는 옵션입니다 : " + optionId);
        }
    }
}
