package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.*;
import com.example.kakao._core.utils.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    	ApiUtils.ApiResult<?> apiResult = ApiUtils.error("잘못된 JSON 양식입니다", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiResult, HttpStatus.BAD_REQUEST);
    }
    
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//    	ApiUtils.ApiResult<?> apiResult = ApiUtils.error("유효하지 않는 값입니다.", HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(apiResult, HttpStatus.BAD_REQUEST);
//    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> DataIntegrityViolationException(DataIntegrityViolationException ex) {
    	ApiUtils.ApiResult<?> apiResult = ApiUtils.error("사용자를 찾을 수 없습니다", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiResult, HttpStatus.NOT_FOUND);
    }
    

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(Exception e){
        ApiUtils.ApiResult<?> apiResult = ApiUtils.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        System.out.println(e.getClass());
        return new ResponseEntity<>(apiResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
