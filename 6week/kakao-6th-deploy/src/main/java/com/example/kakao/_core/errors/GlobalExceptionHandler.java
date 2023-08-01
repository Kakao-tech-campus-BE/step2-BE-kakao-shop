package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.*;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.log.ErrorLog;
import com.example.kakao.log.ErrorLogJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ErrorLogJPARepository errorLogJPARepository;

    private void logError(HttpServletRequest request, Exception e) {
        ErrorLog errorLog = ErrorLog.builder()
                .userIp(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent"))
                .message(e.getMessage())
                .build();
        errorLogJPARepository.save(errorLog);
    }


    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e, HttpServletRequest request){
        logError(request, e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e, HttpServletRequest request){
        logError(request, e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e, HttpServletRequest request){
        logError(request, e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e, HttpServletRequest request){
        logError(request, e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e, HttpServletRequest request){
        logError(request, e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(Exception e, HttpServletRequest request){
        logError(request, e);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
