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

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Errors errors,
                                  HttpServletRequest request) {

        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }
        try {
            userService.join(requestDTO);
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors, HttpServletRequest request) {

        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        try {
            String jwt = userService.login(requestDTO);
            return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
    }

    @PostMapping("/users/{id}/update-password")
    public ResponseEntity<?> updatePassword(
            @PathVariable Integer id,
            @RequestBody @Valid UserRequest.UpdatePasswordDTO requestDTO, Errors errors,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {

        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 e = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }

        if (id != userDetails.getUser().getId()) {
            Exception403 e = new Exception403("인증된 user는 해당 id로 접근할 권한이 없습니다" + id);
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }

        try {
            userService.updatePassword(requestDTO, id);
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findById(
            @PathVariable Integer id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request
    ) {
        if (id != userDetails.getUser().getId()) {
            Exception403 e = new Exception403("인증된 user는 해당 id로 접근할 권한이 없습니다:" + id);
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }

        try {
            UserResponse.FindById responseDTO = userService.findById(id);
            return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    // 이메일 중복체크 (기능에는 없지만 사용중)
    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody UserRequest.EmailCheckDTO emailCheckDTO) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // (기능3) - 로그아웃
    // 사용 안함 - 프론트에서 localStorage JWT 토큰을 삭제하면 됨.
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> user) {
        return ResponseEntity.ok().header(JWTProvider.HEADER, "").body(ApiUtils.success(null));
    }
}
