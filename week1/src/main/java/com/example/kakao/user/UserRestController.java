package com.example.kakao.user;

import com.example.kakao._core.security.JwtTokenProvider;
import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    // (기능1) 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Errors errors) {
        userService.join(requestDTO);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // (기능2) 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors) {
        String jwt = userService.login(requestDTO);
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt).body(ApiUtils.success(null));
    }

    // 사용 안함 - 프론트 요구사항에 이메일 중복 검사 로직 없음.
    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody @Valid  UserRequest.EmailCheckDTO emailCheckDTO, Errors errors) {
        userService.sameCheckEmail(emailCheckDTO.getEmail());
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // (기능3) - 로그아웃
    // 사용 안함 - 프론트에서 localStorage JWT 토큰을 삭제하면 됨.
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> user) {
        userService.sameCheckEmail(user.get("email"));
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, "").body(ApiUtils.success(null));
    }
}
