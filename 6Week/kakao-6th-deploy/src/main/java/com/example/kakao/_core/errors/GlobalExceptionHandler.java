package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.*;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.log.ErrorLog;
import com.example.kakao.log.ErrorLogJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {
    private final ErrorLogJPARepository errorLogJPARepository;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(Exception e, HttpServletRequest request){
        if(e instanceof ConstraintViolationException) {
            ApiUtils.ApiResult<?> apiResult = ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(apiResult, HttpStatus.BAD_REQUEST);
        }

        ErrorLog errorLog = ErrorLog.builder()
                .message(e.getMessage())
                .userAgent(request.getHeader("User-Agent"))
                .userIp(request.getRemoteAddr())
                .build();
        errorLogJPARepository.save(errorLog);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.error("unknown server error", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
