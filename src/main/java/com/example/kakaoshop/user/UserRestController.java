package com.example.kakaoshop.user;

import com.example.kakaoshop._core.security.CustomUserDetails;
import com.example.kakaoshop._core.security.JWTProvider;
import com.example.kakaoshop._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user") // TODO: user -> account 도메인 이름 변경 고려.
public class UserRestController {
    private final UserJPARepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // 이메일 중복 검사
    @PostMapping("/email-duplicate-check")
    public ResponseEntity<?> checkEmailDuplicate(@RequestBody UserRequest.EmailDuplicateCheckDTO checkEmailDuplicateDTO) {
        // TODO: service level 에서 exception throw-handle 고려.
        if( userRepository.existsByEmail(checkEmailDuplicateDTO.getEmail()) ) {
            return ResponseEntity.badRequest().body(
              ApiUtils.error("동일한 이메일이 존재합니다 : " + checkEmailDuplicateDTO.getEmail(), HttpStatus.BAD_REQUEST)
            );
        }
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserRequest.SignUpDTO signUpDTO) {
        // TODO: email 중복체크
        userRepository.save(
          User.builder()
            .email(signUpDTO.getEmail())
            .password(passwordEncoder.encode(signUpDTO.getPassword()))
            .username(signUpDTO.getUsername())
            .roles("ROLE_USER")
            .build()
        );

        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = JWTProvider.create(myUserDetails.getUser());

        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
    }
}
