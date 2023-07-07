package com.example.kakaoshop.domain.account;

import com.example.kakaoshop._core.security.CustomUserDetails;
import com.example.kakaoshop._core.security.JWTProvider;
import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.domain.account.request.AccountRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountRestController {
    private final AccountJPARepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;

    // 이메일 사용 가능 여부
    @GetMapping("/email-use-permission")
    public ResponseEntity<Object> checkEmailDuplicate(@RequestParam String email) {
        accountService.checkEmailDuplicate(email);

        // + 특정 도메인의 이메일 사용 제한이 추가되는 경우 등 ..

        return ResponseEntity.ok(ApiUtils.success());
    }

    // 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Validated AccountRequestDto.SignUpDto signUpDTO) {
        accountService.checkEmailDuplicate(signUpDTO.getEmail());


        accountRepository.save(
          Account.builder()
            .email(signUpDTO.getEmail())
            .password(passwordEncoder.encode(signUpDTO.getPassword()))
            .username(signUpDTO.getUsername())
            .roles(Account.Role.USER)
            .build()
        );

        return ResponseEntity.ok(ApiUtils.success());
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AccountRequestDto.LoginDto loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = JWTProvider.create(myUserDetails.getAccount());

        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success());
    }
}
