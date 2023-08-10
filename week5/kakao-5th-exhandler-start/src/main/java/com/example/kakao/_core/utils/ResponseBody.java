package com.example.kakao._core.utils;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ResponseBody{

    @Getter @Setter @AllArgsConstructor
    public static class Body<T> {
        private final boolean success;
        private final T response;
        private final FailBody error;
    }
    @Getter @Setter @AllArgsConstructor
    public static class FailBody {
        private final String message;
        private final int status;
    }
}
