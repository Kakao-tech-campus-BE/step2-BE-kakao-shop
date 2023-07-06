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
import java.util.Optional;
import java.util.regex.Pattern;

import static org.springframework.data.projection.EntityProjection.ProjectionType.DTO;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserJPARepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO) {

        // 이메일 형식 체크
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        if(!emailPattern.matcher(joinDTO.getEmail()).matches()){
            return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요:email ", HttpStatus.BAD_REQUEST));
        }

        // 패스워드 글자 수 체크
        if(8 > joinDTO.getPassword().length() || joinDTO.getPassword().length() > 20 ){
            return ResponseEntity.badRequest().body(
                    ApiUtils.error("8에서 20자 이내여야 합니다.:password", HttpStatus.BAD_REQUEST));
        }

        // 패스워드 형식 체크
        Pattern pwPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\p{Punct})[^\\s]*$");
        if(!pwPattern.matcher(joinDTO.getPassword()).matches()){
            return ResponseEntity.badRequest().body(
                    ApiUtils.error("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다:password", HttpStatus.BAD_REQUEST));
        }

        // 동일한 이메일이 존재
        Optional<User> userCheck = userRepository.findByEmail(joinDTO.getEmail());
        if (!userCheck.isEmpty()) { // 성공
            return ResponseEntity.badRequest().body(ApiUtils.error("동일한 이메일이 존재합니다 : "+joinDTO.getEmail() , HttpStatus.BAD_REQUEST));
        }


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

        // 인증 되지 않음 (email이 donu@pusan.ac.kr인 유저에 대해서 하드코딩)
        if(loginDTO.getEmail().equals("donu@pusan.ac.kr")){
            return ResponseEntity.badRequest().body(ApiUtils.error("인증되지 않았습니다", HttpStatus.UNAUTHORIZED));
        }

        // 이메일 형식 체크
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        if(!emailPattern.matcher(loginDTO.getEmail()).matches()){
            return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요:email ", HttpStatus.BAD_REQUEST));
        }

        // 패스워드 글자 수 체크
        if(8 > loginDTO.getPassword().length() || loginDTO.getPassword().length() > 20 ){
            return ResponseEntity.badRequest().body(
                    ApiUtils.error("8에서 20자 이내여야 합니다.:password", HttpStatus.BAD_REQUEST));
        }

        // 패스워드 형식 체크
        Pattern pwPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\p{Punct})[^\\s]*$");
        if(!pwPattern.matcher(loginDTO.getPassword()).matches()){
            return ResponseEntity.badRequest().body(
                    ApiUtils.error("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다:password", HttpStatus.BAD_REQUEST));
        }


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = JWTProvider.create(myUserDetails.getUser());

        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody UserRequest.CheckDTO checkDTO) {

        // 이메일 형식 체크
        Pattern p = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
        if(!p.matcher(checkDTO.getEmail()).matches()){
            return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요:email ", HttpStatus.BAD_REQUEST));
        }

        Optional<User> userCheck = userRepository.findByEmail(checkDTO.getEmail());

        if (userCheck.isEmpty()) { // 성공
            return ResponseEntity.ok(ApiUtils.success(null));
        } else { // 중복 이메일 존재
            return ResponseEntity.badRequest().body(ApiUtils.error("동일한 이메일이 존재합니다 : "+checkDTO.getEmail() , HttpStatus.BAD_REQUEST));
        }
    }

}
