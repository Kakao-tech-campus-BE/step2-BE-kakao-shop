package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.*;
import com.example.kakao.log.ErrorLog;
import com.example.kakao.log.ErrorLogJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor // dependency injection
@Component // conpoent를 걸면 globalexcep이 ioc container에 걸림
public class GlobalExceptionHandler {

    private final ErrorLogJPARepository errorLogJPARepository;
    // 500터질 때 log남기려고 service에서 이제 필요없음

    public ResponseEntity<?> handle(RuntimeException e, HttpServletRequest request){
        if(e instanceof Exception400){
            Exception400 ex = (Exception400) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else if(e instanceof Exception401){
            Exception401 ex = (Exception401) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else if(e instanceof Exception403){
            Exception403 ex = (Exception403) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else if(e instanceof Exception404){
            Exception404 ex = (Exception404) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else if(e instanceof Exception500){ // 500일 때는 로그남김
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
        }else{ // 하다 보면 못잡는 에러가 생김 이건 unknown server error로
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
}
