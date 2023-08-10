package com.example.kakao._core.utils;

import com.example.kakao._core.security.JWTProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import static com.example.kakao._core.utils.ResponseBody.Body;
import static com.example.kakao._core.utils.ResponseBody.FailBody;
public class ApiResult<T> extends ResponseEntity<T> {
    private ApiResult(T body, HttpStatus status) {
        super(body, status);
    }

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult(new Body<T>(true, response, null), HttpStatus.OK);
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult(new Body<T>(true, null, null), HttpStatus.OK);
    }

    public static ApiResult error(String message, HttpStatus status) {
        return new ApiResult(new Body(false, null, new FailBody(message, status.value())), status);
    }
}
