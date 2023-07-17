package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.*;
import com.example.kakao._core.utils.ApiResponse;
import com.example.kakao._core.utils.ApiUtils;
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

    public ResponseEntity<ApiResponse> handle(RuntimeException e, HttpServletRequest request){
        if(e instanceof BadRequestException){
            BadRequestException ex = (BadRequestException) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else if(e instanceof UnAuthorizedException){
            UnAuthorizedException ex = (UnAuthorizedException) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else if(e instanceof ForbiddenException){
            ForbiddenException ex = (ForbiddenException) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else if(e instanceof NotFoundException){
            NotFoundException ex = (NotFoundException) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else if(e instanceof InternalServerErrorException){
            ErrorLog errorLog = ErrorLog.builder()
                    .message(e.getMessage())
                    .userAgent(request.getHeader("User-Agent"))
                    .userIp(request.getRemoteAddr())
                    .build();
            errorLogJPARepository.save(errorLog);
            InternalServerErrorException ex = (InternalServerErrorException) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else{
            ErrorLog errorLog = ErrorLog.builder()
                    .message(e.getMessage())
                    .userAgent(request.getHeader("User-Agent"))
                    .userIp(request.getRemoteAddr())
                    .build();
            errorLogJPARepository.save(errorLog);
            return new ResponseEntity<>(
                ApiUtils.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
