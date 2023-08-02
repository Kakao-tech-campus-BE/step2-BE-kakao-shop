package com.example.kakao._core.errors.exception;

import com.example.kakao._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 권한 없음
@Getter
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.FORBIDDEN);
    }

    public HttpStatus status(){
        return HttpStatus.FORBIDDEN;
    }
}