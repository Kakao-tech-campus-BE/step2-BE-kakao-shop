package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.*;
import com.example.kakao.log.ErrorLog;
import com.example.kakao.log.ErrorLogJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class GlobalExceptionHandler {

    private final ErrorLogJPARepository errorLogJPARepository;

    public ResponseEntity<?> handle(RuntimeException e, HttpServletRequest request){
        if(e instanceof Exception400){
            Exception400 ex = (Exception400) e; //다운캐스팅
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
        }else if(e instanceof Exception500){ //500이 터질땐 에러 로그를 남김
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
        }else{ //위 에러로도 못 잡은 에러
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
