package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.*;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.log.ErrorLog;
import com.example.kakao.log.ErrorLogJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<?> serverError(
            Exception500 e,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request
    ){
        saveErrorLog(e, userDetails, request);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(
            Exception e,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request
    ){
        saveErrorLog(e, userDetails, request);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.error("알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional
    public void saveErrorLog(Exception e, CustomUserDetails userDetails, HttpServletRequest request) {
        String errorMessage = e.getMessage();
        Integer userId = userDetails.getUser().getId();
        String userIp = getIp(request);
        String userAgent = request.getHeader("user-agent");

        errorLogJPARepository.save(ErrorLog.builder()
                .userId(userId)
                .userAgent(userAgent)
                .userIp(userIp)
                .message(errorMessage)
                .build());
    }

    private String getIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

        System.out.println(">>>> X-FORWARDED-FOR : " + ip);

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println(">>>> Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
            System.out.println(">>>> WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println(">>>> HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println(">>>> HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        System.out.println(">>>> Result : IP Address : "+ip);

        return ip;

    }
}
