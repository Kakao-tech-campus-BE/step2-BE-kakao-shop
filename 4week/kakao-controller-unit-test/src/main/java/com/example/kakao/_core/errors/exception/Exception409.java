package com.example.kakao._core.errors.exception;

import com.example.kakao._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 권한 없음
// 이메일 중복검사에는 리소스의 충돌이라고 느껴지기에 409 코드를 만들어보았습니다.
@Getter
public class Exception409 extends RuntimeException {
    public Exception409(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.NOT_FOUND);
    }

    public HttpStatus status(){
        return HttpStatus.NOT_FOUND;
    }
}