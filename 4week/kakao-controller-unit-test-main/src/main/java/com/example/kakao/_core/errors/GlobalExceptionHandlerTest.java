package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.*;
import com.example.kakao.log.ErrorLog;
import com.example.kakao.log.ErrorLogJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;


// if-else 안 쓰고 싶어서 만들어 본 ExceptionHandler
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandlerTest {
    private final ErrorLogJPARepository errorLogJPARepository;

    @ExceptionHandler({Exception400.class})
    public ResponseEntity<?> handleBadRequest(RuntimeException e) {
        Exception400 ex = (Exception400) e;
        return new ResponseEntity<>(
                ex.body(),
                ex.status()
        );
    }

    @ExceptionHandler({Exception401.class})
    public ResponseEntity<?> handleUnauthorized(RuntimeException e) {
        Exception401 ex = (Exception401) e;
        return new ResponseEntity<>(
                ex.body(),
                ex.status()
        );
    }

    @ExceptionHandler({Exception403.class})
    public ResponseEntity<?> handleForbidden(RuntimeException e) {
        Exception403 ex = (Exception403) e;
        return new ResponseEntity<>(
                ex.body(),
                ex.status()
        );
    }

    @ExceptionHandler({Exception404.class})
    public ResponseEntity<?> handleNotFound(RuntimeException e) {
        Exception404 ex = (Exception404) e;
        return new ResponseEntity<>(
                ex.body(),
                ex.status()
        );
    }

    @ExceptionHandler({Exception500.class})
    public ResponseEntity<?> handleInternalServerError(RuntimeException e, HttpServletRequest request) {
        ErrorLog errorLog = ErrorLog.builder()
                .message(e.getMessage())
                .userAgent(request.getHeader("User-Agent"))
                .userIp(request.getRemoteAddr())
                .build();
        errorLogJPARepository.save(errorLog);
        Exception500 ex = (Exception500) e;
        return new ResponseEntity<>(
                ex.body(),
                ex.status()
        );
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleUnknown(RuntimeException e, HttpServletRequest request){
        ErrorLog errorLog = ErrorLog.builder()
                .message(e.getMessage())
                .userAgent(request.getHeader("User-Agent"))
                .userIp(request.getRemoteAddr())
                .build();
        errorLogJPARepository.save(errorLog);
        return new ResponseEntity<>(
                "unknown server error",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
