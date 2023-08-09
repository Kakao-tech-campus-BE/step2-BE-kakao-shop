package com.example.kakao._core.errors.exception.product.option;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception401;
import com.example.kakao._core.errors.exception.Exception404;

import java.util.List;

public class OptionException {

    public static class DuplicatedOptionException extends Exception400 {
        public DuplicatedOptionException() {
            super("중복된 옵션이 입력되었습니다.");
        }
        public DuplicatedOptionException(List<Integer> optionIds) {
            super("중복된 옵션이 입력되었습니다. : " + optionIds);
        }
    }

    public static class OptionNotFoundException extends Exception404 {
        public OptionNotFoundException() {
            super("존재하지 않는 옵션입니다.");
        }
        public OptionNotFoundException(int id) {
            super("존재하지 않는 옵션입니다. : " + id);
        }
        public OptionNotFoundException(List<Integer> ids) {
            super("존재하지 않는 옵션입니다. : " + ids);
        }
    }

}
