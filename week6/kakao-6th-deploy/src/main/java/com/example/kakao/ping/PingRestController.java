package com.example.kakao.ping;

import com.example.kakao._core.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * JWT 토큰 인증 테스트를 위한 테스트
 */

@RestController
@RequestMapping("/ping")
public class PingRestController {

    @GetMapping("")
    public ResponseEntity<?> ping() {
        return ResponseEntity.ok(ApiUtils.success("pong"));
    }
}
