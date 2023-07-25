package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.*;
import com.example.kakao._core.utils.ApiResponse;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.log.ErrorLog;
import com.example.kakao.log.ErrorLogJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE) // 도메인 별로 Custom Exception 을 추가하게 될 경우, 올바른 우선순위 처리를 위함.
public class GlobalExceptionHandler {

  private final ErrorLogJPARepository errorLogJPARepository;

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResponse> handleConstraintViolationException(ConstraintViolationException ex) {
    log.error("Constraint Violation", ex);
    return new ResponseEntity<>(
      ApiUtils.error("Constraint Violation: " + ex.getMessage(), HttpStatus.BAD_REQUEST),
      HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
    log.error("Method Argument Not Valid",ex);

    FieldError fieldError = ex.getBindingResult().getFieldError();

    // 이 분기문이 실행되는 경우를 이해하지 못했음. 일단은 NullPointerException 방지를 위해 추가함.
    if(fieldError == null) return new ResponseEntity<>(
      ApiUtils.error("Argument Not Valid: No Field Error", HttpStatus.BAD_REQUEST),
      HttpStatus.BAD_REQUEST
    );

    return new ResponseEntity<>(
      ApiUtils.error("Argument Not Valid: "+ fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST),
      HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    log.error("Method Argument Type Mismatch", ex);
    return new ResponseEntity<>(
      ApiUtils.error(
        "Argument Type Mismatch: " + ex.getName() + " should be " + Objects.requireNonNull(ex.getRequiredType()).getSimpleName(),
        HttpStatus.BAD_REQUEST
      ),
      HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
    log.error("Bad Request: 400", ex);
    return new ResponseEntity<>(ex.body(), ex.status());
  }

  @ExceptionHandler(UnAuthorizedException.class)
  public ResponseEntity<ApiResponse> handleUnAuthorizedException(UnAuthorizedException ex, HttpServletRequest request) {
    log.error("UnAuthorized: 401", ex);
    return new ResponseEntity<>(ex.body(), ex.status());
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ApiResponse> handleForbiddenException(ForbiddenException ex, HttpServletRequest request) {
    log.error("Forbidden: 403", ex);
    return new ResponseEntity<>(ex.body(), ex.status());
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ApiResponse> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
    log.error("No Handler Found: 404", ex);
    return this.handleNotFoundException(new NotFoundException(ex.getMessage()), request);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiResponse> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
    log.error("Not Found: 404", ex);
    return new ResponseEntity<>(ex.body(), ex.status());
  }

  @ExceptionHandler(InternalServerErrorException.class)
  public ResponseEntity<ApiResponse> handleInternalServerErrorException(InternalServerErrorException ex, HttpServletRequest request) {
    log.error("Internal Server Error: 500", ex);
    return new ResponseEntity<>(ex.body(), ex.status());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse> handleException(Exception ex, HttpServletRequest request) {
    log.error("UnknownException: Server", ex);

    ErrorLog errorLog = ErrorLog.builder()
      .message(ex.getMessage())
      .userAgent(request.getHeader("User-Agent"))
      .userIp(request.getRemoteAddr())
      .build();

    errorLogJPARepository.save(errorLog);

    return new ResponseEntity<>(
      ApiUtils.error(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
      HttpStatus.INTERNAL_SERVER_ERROR
    );
  }

}
