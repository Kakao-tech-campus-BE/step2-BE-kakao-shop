package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.*;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.log.ErrorLog;
import com.example.kakao.log.ErrorLogJPARepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Instanceof;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorLogJPARepository errorLogJPARepository;

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e) {
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorizaed(Exception401 e) {
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e) {
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e) {
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e) {
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> badRequest(Exception e) {
        if (e instanceof ConstraintViolationException) {
            ApiUtils.ApiResult<?> apiResult = ApiUtils.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(apiResult, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            ErrorLog error = ErrorLog.builder()
                    .message(e.getMessage())
                    .build();

            errorLogJPARepository.save(error);
            ApiUtils.ApiResult<?> apiResult = ApiUtils.error("서버의 오류로 처리할 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(apiResult, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
