package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.*;
import com.example.kakao._core.utils.ApiResponse;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.log.ErrorLog;
import com.example.kakao.log.ErrorLogJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

  private final ErrorLogJPARepository errorLogJPARepository;

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
