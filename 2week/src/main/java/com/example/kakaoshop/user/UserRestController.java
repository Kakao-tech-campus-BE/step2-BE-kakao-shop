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
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserJPARepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO) {
        //이메일 형식 체크
        if(!isEmailFormat(joinDTO.getEmail()))
            return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요 : "+joinDTO.getEmail(), HttpStatus.BAD_REQUEST));

        //비밀번호 형식 체크
        if(!isPasswordFormat(joinDTO.getPassword()))
            return ResponseEntity.badRequest().body(ApiUtils.error("영문, 숫자, 특수문자가 포함되어야 하고 공백이 포함될 수 없습니다 : "+joinDTO.getPassword(), HttpStatus.BAD_REQUEST));

        //이메일 중복 체크
        if(hasEmail(joinDTO.getEmail()))
            return ResponseEntity.badRequest().body(ApiUtils.error("동일한 이메일이 존재합니다 : "+joinDTO.getEmail(), HttpStatus.BAD_REQUEST));

        //비밀번호 길이 체크
        if(joinDTO.getPassword().length()<8||joinDTO.getPassword().length()>20)
            return ResponseEntity.badRequest().body(ApiUtils.error("비밀번호의 길이는 8에서 20 이내여야 합니다 : "+joinDTO.getPassword(), HttpStatus.BAD_REQUEST));

        User user = User.builder()
                .email(joinDTO.getEmail())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .username(joinDTO.getUsername())
                .roles("ROLE_USER")
                .build();

        userRepository.save(user);

        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = JWTProvider.create(myUserDetails.getUser());

        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
    }

    @PostMapping("/emailCheck")
    public ResponseEntity<?> check(@RequestBody Map<String,String> map){
        String email=map.get("email");

        //이메일 형식 체크
        if(!isEmailFormat(email)) return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요 : "+email, HttpStatus.BAD_REQUEST));

        //이메일 중복 체크
        if(hasEmail(email)) return ResponseEntity.badRequest().body(ApiUtils.error("동일한 이메일이 존재합니다 : "+email, HttpStatus.BAD_REQUEST));

        return ResponseEntity.ok(ApiUtils.success(null));
    }

    //이메일 중복 체크
    public boolean hasEmail(String email){
        Optional<User> user=userRepository.findByEmail(email);
        if(user.isEmpty()) return false;
        else return true;
    }
    //이메일 형식 체크
    public boolean isEmailFormat(String email){
        String[] format=email.split("@");
        if(format.length!=2) return false;
        else if(!format[1].contains(".")) return false;
        else return true;
    }

    //비밀번호 형식 체크
    public boolean isPasswordFormat(String password){
        String pattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\p{Punct})[^\\s]+$";
        return password.matches(pattern);
    }
}
