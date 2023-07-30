package com.example.kakao._core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
public class ResponseBody<T>{

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult(new SuccessBody(false, response, null), HttpStatus.OK);
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult(new SuccessBody(false, null, null), HttpStatus.OK);
    }

    public static ApiResult error(String message, HttpStatus status) {
        return new ApiResult<>(new FailBody(message, status.value()), status);
    }

    @Getter @Setter @AllArgsConstructor
    public static class SuccessBody<T> {
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
