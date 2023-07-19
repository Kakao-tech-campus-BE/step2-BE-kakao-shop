package com.example.kakao._core.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.kakao._core.errors.exception.Exception400;


@RestControllerAdvice
public class GlobalExceptionHandler2 {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Exception400 e =  new Exception400("유효하지않는 요청입니다!");
        return new ResponseEntity<>(e.body(), e.status());
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    	Exception400 e =  new Exception400("유효하지않는 요청입니다!");
        return new ResponseEntity<>(e.body(), e.status());
    }
}
