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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE) // 도메인 별로 Custom Exception 을 추가하게 될 경우, 올바른 우선순위 처리를 위함.
public class GlobalExceptionHandler {

  private final ErrorLogJPARepository errorLogJPARepository;

  // Bad Request 400

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ApiResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
    return new ResponseEntity<>(
      ApiUtils.error("Missing Servlet Request Parameter", HttpStatus.BAD_REQUEST),
      HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    return new ResponseEntity<>(
      ApiUtils.error("Http Message Not Readable", HttpStatus.BAD_REQUEST),
      HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResponse> handleConstraintViolationException(ConstraintViolationException ex) {
    List<String> errorMessages = ex.getConstraintViolations().stream()
      .map(ConstraintViolation::getMessage)
      .collect(Collectors.toList());

    return new ResponseEntity<>(
      ApiUtils.error("Constraint Violation: " + errorMessages, HttpStatus.BAD_REQUEST),
      HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    return new ResponseEntity<>(
      ApiUtils.error(
        "Argument Type Mismatch: " + ex.getName() + " should be " + Objects.requireNonNull(ex.getRequiredType()).getSimpleName(),
        HttpStatus.BAD_REQUEST
      ),
      HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    return this.handleBindExceptionFieldError(ex, "Method Argument Not Valid Exception");
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ApiResponse> handleBindException(BindException ex) {
    return this.handleBindExceptionFieldError(ex, "Bind Exception");
  }


  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
    log.error("error log in ex hand");
    log.debug("debug log in ex hand");
    return new ResponseEntity<>(ex.body(), ex.status());
  }

  // 401

  @ExceptionHandler(UnAuthorizedException.class)
  public ResponseEntity<ApiResponse> handleUnAuthorizedException(UnAuthorizedException ex, HttpServletRequest request) {
    return new ResponseEntity<>(ex.body(), ex.status());
  }

  //403

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ApiResponse> handleForbiddenException(ForbiddenException ex, HttpServletRequest request) {
    return new ResponseEntity<>(ex.body(), ex.status());
  }

  // 404

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ApiResponse> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
    return this.handleNotFoundException(new NotFoundException(ex.getMessage()), request);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiResponse> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
    return new ResponseEntity<>(ex.body(), ex.status());
  }

  // 405

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
    return handleNotFoundException(
      new NotFoundException("Http Request Method " + ex.getMethod() + " Not Supported"), request);
    // 405 이지만 400~404까지만 사용하기로 약속됨.
  }

  // 500

  @ExceptionHandler(InternalServerErrorException.class)
  public ResponseEntity<ApiResponse> handleInternalServerErrorException(InternalServerErrorException ex, HttpServletRequest request) {
    return new ResponseEntity<>(ex.body(), ex.status());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse> handleException(Exception ex, HttpServletRequest request) {
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


  private ResponseEntity<ApiResponse> handleBindExceptionFieldError(BindException ex, String errorMessage) {
    FieldError fieldError = ex.getBindingResult().getFieldError();

    if (fieldError == null) {
      return new ResponseEntity<>(
        ApiUtils.error(errorMessage + ": No Field Error", HttpStatus.BAD_REQUEST),
        HttpStatus.BAD_REQUEST
      );
    }

    return new ResponseEntity<>(
      ApiUtils.error(errorMessage + ": " + fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST),
      HttpStatus.BAD_REQUEST
    );
  }
}
