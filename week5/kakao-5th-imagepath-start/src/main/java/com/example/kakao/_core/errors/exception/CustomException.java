package com.example.kakao._core.errors.exception;

import com.example.kakao._core.errors.ErrorCode;
import com.example.kakao._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = -5415773249608096132L;

    private final ErrorCode errorCode;

    private String message;

    public CustomException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ApiUtils.ApiResult<?> body(){
        if (!getMessage().isEmpty()) {
            return ApiUtils.error(errorCode.getMessage() + ":" + message, errorCode.getHttpStatus());
        }
        return ApiUtils.error(errorCode.getMessage(), errorCode.getHttpStatus());
    }

    public HttpStatus status(){
        return errorCode.getHttpStatus();
    }

}
