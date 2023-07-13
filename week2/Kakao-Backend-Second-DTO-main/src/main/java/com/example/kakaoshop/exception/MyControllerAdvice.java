package com.example.kakaoshop.exception;

import com.example.kakaoshop._core.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler
    public ApiUtils.ApiResult<?> orderExHandler(OrderException e){
        log.error("[orderHandler] ex", e);
        return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
