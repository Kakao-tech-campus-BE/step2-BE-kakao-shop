package com.example.kakao.user;

import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.utils.ApiResult;
import com.example.kakao._core.utils.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping("/join")
    public ApiResult<Object> join(@RequestBody UserRequest.JoinDTO joinDTO) {
        userService.join(joinDTO);
        return ApiResult.success();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequest.LoginDTO loginDTO) {
        String jwt = userService.login(loginDTO);
        return ResponseEntity.ok()
                .header(JWTProvider.HEADER, jwt)
                .body(new ResponseBody.Body(true, null, null));
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody @Valid UserRequest.EmailCheckDTO emailCheckDTO, Errors errors) {
        userService.sameCheckEmail(emailCheckDTO.getEmail());
        return ApiResult.success();
    }
}