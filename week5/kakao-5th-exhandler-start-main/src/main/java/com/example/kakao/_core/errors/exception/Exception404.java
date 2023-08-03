package com.example.kakao._core.errors.exception;

import com.example.kakao._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 권한 없음
@Getter
public class Exception404 extends HttpStatusException {
    public Exception404(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

}