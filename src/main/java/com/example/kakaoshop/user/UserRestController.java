package com.example.kakaoshop.user;

import com.example.kakaoshop._core.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final AuthenticationManager authenticationManager;
    private final UserJPARepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 (예외가 터지는 것은 추후에 잡을 예정)
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO) {
        // 코드 리팩토링도 추후에 할 예정
        User user = User.builder()
                .email(joinDTO.getEmail())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .username(joinDTO.getUsername())
                .roles("ROLE_USER")
                .build();

        userRepository.save(user);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        return ResponseEntity.ok("ok");
    }
    
    @GetMapping("/login-check")
    public ResponseEntity<?> loginCheck(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return ResponseEntity.ok(customUserDetails.getUser().getEmail()+"로 로그인함");
    }
}
