package com.example.kakao.domain.user;

import com.example.kakao._core.errors.exception.ForbiddenException;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.utils.ApiResponse;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.domain.user.dto.UserResponse;
import com.example.kakao.domain.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse> join(@RequestBody @Validated UserRequest.JoinDTO requestDTO) {

        userService.join(requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Validated UserRequest.LoginDTO requestDTO, Errors errors, HttpServletRequest request) {

        String jwt = userService.login(requestDTO);
        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success());
    }

    @PostMapping("/users/{id}/update-password")
    public ResponseEntity<ApiResponse> updatePassword(
            @PathVariable("id") Integer accessId,
            @RequestBody @Validated UserRequest.UpdatePasswordDTO requestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        userService.validateUserInfoAccessPermission(accessId, userDetails);

        userService.updatePassword(requestDTO, accessId);

        return ResponseEntity.ok().body(ApiUtils.success());
    }

    // 클라이언트로 부터 전달된 데이터는 신뢰할 수 없다.
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse> findById(
            @PathVariable("id") Integer accessId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        userService.validateUserInfoAccessPermission(accessId, userDetails);

        UserResponse.FindById responseDTO = userService.findById(accessId);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));

    }

    // 이메일 중복체크 (기능에는 없지만 사용중)
    @PostMapping("/check")
    public ResponseEntity<ApiResponse> check(@RequestBody UserRequest.EmailCheckDTO emailCheckDTO) {
        userService.checkEmailDuplicated(emailCheckDTO.getEmail());
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // (기능3) - 로그아웃
    // 사용 안함 - 프론트에서 localStorage JWT 토큰을 삭제하면 됨.
    @GetMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestBody Map<String, String> user) {
        return ResponseEntity.ok().header(JWTProvider.HEADER, "").body(ApiUtils.success());
    }
}
