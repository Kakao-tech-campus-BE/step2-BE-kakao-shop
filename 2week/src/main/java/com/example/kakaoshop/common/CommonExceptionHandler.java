package com.example.kakaoshop.common;


import com.example.kakaoshop._core.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
@Order
public class CommonExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
    log.error("Method Argument Not Valid",ex);

    FieldError fieldError = ex.getBindingResult().getFieldError();
    if(fieldError == null) return ResponseEntity.badRequest().body(ApiUtils.error("Argument Not Valid: No Field Error", HttpStatus.BAD_REQUEST));

    return ResponseEntity.badRequest().body(ApiUtils.error(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException ex){
    log.error("Not Found: 404",ex);
    return ResponseEntity.badRequest().body(ApiUtils.error("Not Found", HttpStatus.NOT_FOUND));
  }

  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<?> handleDataAccessException(DataAccessException ex){
    log.error("Data Access Exception: Database",ex);
    return ResponseEntity.badRequest().body(ApiUtils.error("Database Error", HttpStatus.INTERNAL_SERVER_ERROR));
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
    log.error("Http Request Method Not Supported",ex);
    return ResponseEntity.badRequest().body(ApiUtils.error("Http Request Method Not Supported", HttpStatus.METHOD_NOT_ALLOWED));
  }



  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception ex){
    log.error("Exception: Server",ex);
    return ResponseEntity.badRequest().body(ApiUtils.error("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR));
  }
}
