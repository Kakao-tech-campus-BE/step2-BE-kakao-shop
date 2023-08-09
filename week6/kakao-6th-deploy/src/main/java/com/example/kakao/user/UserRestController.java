package com.example.kakao.user;

import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    // (기능3) 이메일 중복체크
    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody @Valid UserRequest.EmailCheckDTO emailCheckDTO, Errors errors) {
        userService.sameCheckEmail(emailCheckDTO.getEmail());
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // (기능4) 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Errors errors) {
        userService.join(requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // (기능5) 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors) {
        String jwt = userService.login(requestDTO);
        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
    }

    // 로그아웃 사용안함 - 프론트에서 JWT 토큰을 브라우저의 localstorage에서 삭제하면 됨.
}
