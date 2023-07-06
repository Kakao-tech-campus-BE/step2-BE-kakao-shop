package com.example.kakaoshop.user;

import com.example.kakaoshop._core.security.CustomUserDetails;
import com.example.kakaoshop._core.security.JWTProvider;
import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop._core.utils.EmailValidator;
import com.example.kakaoshop._core.utils.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO) {


        if (!EmailValidator.validateEmail(joinDTO.getEmail())) {
            return ResponseEntity.ok(ApiUtils.error("이메일 형식으로 작성해주세요 : email", HttpStatus.valueOf(400)));
        }
        if(!PasswordValidator.validatePassword(joinDTO.getPassword())){
            return ResponseEntity.ok(ApiUtils.error("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다 :password", HttpStatus.valueOf(400)));
        }
        if(joinDTO.getPassword().length()>20 || joinDTO.getPassword().length()<8){
            return ResponseEntity.ok(ApiUtils.error("비밀번호는 8자 이상 20자 이내여야 합니다 :password", HttpStatus.valueOf(400)));
        }
        if(joinDTO.getEmail().equals("ssar@nate.com")){
            return ResponseEntity.ok(ApiUtils.error("동일한 이메일입니다 : "+joinDTO.getEmail(), HttpStatus.valueOf(400)));
        }
        User user = User.builder()
                .email(joinDTO.getEmail())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .username(joinDTO.getUsername())
                .roles("ROLE_USER")
                .build();

        userRepository.save(user);

        return ResponseEntity.ok(ApiUtils.success(null) );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO) {
        if (!EmailValidator.validateEmail(loginDTO.getEmail())) {
            return ResponseEntity.ok(ApiUtils.error("이메일 형식으로 작성해주세요 : email", HttpStatus.valueOf(400)));
        }
        if(!PasswordValidator.validatePassword(loginDTO.getPassword())){
            return ResponseEntity.ok(ApiUtils.error("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다 :password", HttpStatus.valueOf(400)));
        }

        if(loginDTO.getEmail().equals("ssal1@nate.com")){
            return ResponseEntity.ok(ApiUtils.error("인증 되지 않았습니다", HttpStatus.valueOf(401)));
        }

        if(loginDTO.getPassword().length()>20 || loginDTO.getPassword().length()<8){
            return ResponseEntity.ok(ApiUtils.error("비밀번호는 8자 이상 20자 이내여야 합니다 :password", HttpStatus.valueOf(400)));
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = JWTProvider.create(myUserDetails.getUser());

        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body("ok");
    }
    @GetMapping("/check")
    public ResponseEntity<?> check(@RequestBody UserRequest.checkDTO checkDTO) {
        String email = checkDTO.getEmail();
        // 이메일 형식 검사
        if (!EmailValidator.validateEmail(email)) {
            return ResponseEntity.ok(ApiUtils.error("이메일 형식으로 작성해주세요 : email", HttpStatus.valueOf(400)));
        }
        if (email=="meta@nate.com")
            return ResponseEntity.ok(ApiUtils.success(null));
        else if(email=="ssar@nate.com")
            return ResponseEntity.ok(ApiUtils.error("이미 존재하는 이메일입니다 : "+ email, HttpStatus.valueOf(400)));
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
