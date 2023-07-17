package com.example.kakao._core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class ApiUtils {

    private ApiUtils() {}

    public static <T> ApiResponse success(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResponse error(String message, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(message, status.value()));
    }

    @Getter @Setter @AllArgsConstructor
    private static class ApiResult<T> implements ApiResponse{
        private final boolean success;
        private final T response;
        private final ApiError error;
    }

    @Getter @Setter @AllArgsConstructor
    private static class ApiError {
        private final String message;
        private final int status;
    }
}
