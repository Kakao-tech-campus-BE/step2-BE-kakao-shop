package com.example.kakao._core.errors.exception;


import com.example.kakao._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 인증 안됨
@Getter
public class Exception401 extends HttpAbstractException {
    public Exception401(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}