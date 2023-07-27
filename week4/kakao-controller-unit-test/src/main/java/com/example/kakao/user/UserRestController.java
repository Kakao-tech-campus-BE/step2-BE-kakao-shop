package com.example.kakao.user;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final UserService userService;

    // 회원가입 기능
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Errors errors, HttpServletRequest request) {
        // Request : 필드 유효성 검사
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        // 서비스
        try {
            userService.join(requestDTO);
            return ResponseEntity.ok(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    // 로그인 기능
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors, HttpServletRequest request) {
        // Request : 필드 유효성 검사
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        // 서비스
        try {
            String jwt = userService.login(requestDTO);
            return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
    }
}
