package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.BusinessException;
import com.example.kakao._core.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * javax.validation.Valid 또는 @Validated binding error가 발생할 경우
     */
    @ExceptionHandler(BindException.class)
    protected ApiResult handleBindException(BindException e) {
        log.error("handleBindException", e);
        return ApiResult.error(String.valueOf(e.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ApiResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        return ApiResult.error(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ApiResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        return ApiResult.error(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 비즈니스 로직 실행 중 오류 발생
     */
    @ExceptionHandler(value = {BusinessException.class})
    protected ApiResult handleConflict(BusinessException e) {
        log.error("BusinessException", e);
        return ApiResult.error(e.getErrorCode().getMessage(), e.getErrorCode().getStatus());
    }

    /**
     * 나머지 예외 발생
     */
    @ExceptionHandler(Exception.class)
    protected ApiResult handleException(Exception e) {
        log.error("Exception", e);
        return ApiResult.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}