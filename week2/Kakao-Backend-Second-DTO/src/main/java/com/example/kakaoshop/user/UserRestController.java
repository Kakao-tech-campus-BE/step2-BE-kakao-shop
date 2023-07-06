package com.example.kakaoshop.user;

import com.example.kakaoshop._core.security.CustomUserDetails;
import com.example.kakaoshop._core.security.JWTProvider;
import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.user.request.UserReqCheckDTO;
import com.example.kakaoshop.user.request.UserReqJoinDTO;
import com.example.kakaoshop.user.request.UserReqLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserJPARepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("auth/join")
    public ResponseEntity<?> join(@RequestBody UserReqJoinDTO joinDTO) {
        // 이메일 형식 확인
        if (!emailFormCheck(joinDTO.getEmail())){
            return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요 :email", HttpStatus.BAD_REQUEST));
        }
        // 비밀번호 형식 확인 (길이)
        if (!passwordLengthCheck(joinDTO.getPassword())){
            return ResponseEntity.ok().body(ApiUtils.error("8에서 20자 이내여야 합니다. :password", HttpStatus.BAD_REQUEST));
        }
        // 비밀번호 형식 확인 (영문자, 숫자, 특수문자)
        if (!passwordFormCheck(joinDTO.getPassword())){
            return ResponseEntity.badRequest().body(ApiUtils.error("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다. :password", HttpStatus.BAD_REQUEST));
        }
        // 이메일 중복 확인
        if (!emailDuplicationCheck(joinDTO.getEmail())){
            return ResponseEntity.badRequest().body(ApiUtils.error("동일한 이메일이 존재합니다 :"+joinDTO.getEmail(), HttpStatus.BAD_REQUEST));
        }
        User user = User.builder()
                .email(joinDTO.getEmail())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .username(joinDTO.getUsername())
                .roles("ROLE_USER")
                .build();

        userRepository.save(user);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody UserReqLoginDTO loginDTO) {
        // 이메일 형식 확인
        if (!emailFormCheck(loginDTO.getEmail())){
            return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요 :email", HttpStatus.BAD_REQUEST));
        }
        // 비밀번호 형식 확인
        if (!passwordFormCheck(loginDTO.getPassword())){
            return ResponseEntity.badRequest().body(ApiUtils.error("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다. :password", HttpStatus.BAD_REQUEST));
        }
        // 인증
        if (!passwordLengthCheck(loginDTO.getPassword())){
            return ResponseEntity.ok().body(ApiUtils.error("8에서 20자 이내여야 합니다. :password", HttpStatus.BAD_REQUEST));
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = JWTProvider.create(myUserDetails.getUser());

        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
    }

    @PostMapping("/check-email")
    public ResponseEntity<?> emailCheck(@RequestBody UserReqCheckDTO checkDTO){
        //이메일 형식 체크
        if (!emailFormCheck(checkDTO.getEmail())){
            return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요 :email", HttpStatus.BAD_REQUEST));
        }
        // 이메일 중복 체크
        if (!emailDuplicationCheck(checkDTO.getEmail())){
            return ResponseEntity.badRequest().body(ApiUtils.error("동일한 이메일이 존재합니다 :"+checkDTO.getEmail(), HttpStatus.BAD_REQUEST));
        }
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    private boolean emailFormCheck(String email){
        return email.matches("[a-zA-Z0-9]+@[a-z]+\\.[a-z]+");
    }
    private boolean emailDuplicationCheck(String email){
        return userRepository.findByEmail(email).isEmpty();
    }
    private boolean passwordLengthCheck(String password){
        return 8 <= password.length() && password.length() <= 20;
    }
    private boolean passwordFormCheck(String password){
        return password.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]+");
    }


}
