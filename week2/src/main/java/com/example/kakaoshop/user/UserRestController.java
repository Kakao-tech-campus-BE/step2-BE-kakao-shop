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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserJPARepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO) {
        //검증
        String email = joinDTO.getEmail();
        String password = joinDTO.getPassword();
        //올바른 이메일 형식인지 확인
        if (!email.contains("@"))
            return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요:email", HttpStatus.BAD_REQUEST));

        //유효한 비밀번호인지 확인
        if (!isValidPassword(password))
            return ResponseEntity.badRequest().body(ApiUtils.error("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password", HttpStatus.BAD_REQUEST));

        //동일한 이메일이 존재하는지 확인
        UserRequest.CheckEmailDTO checkEmailDTO = new UserRequest.CheckEmailDTO();
        checkEmailDTO.setEmail(email);
        ResponseEntity<?> responseEntity = check(checkEmailDTO); //check 메서드 사용
        boolean isSuccessful = responseEntity.getStatusCode().is2xxSuccessful();
        if (!isSuccessful)
            return ResponseEntity.badRequest().body(ApiUtils.error("동일한 이메일이 존재합니다 : "+email, HttpStatus.BAD_REQUEST));

        //비밀번호 길이 검증
        int passwordLength = password.length();
        if(!(passwordLength>=8 && passwordLength <= 20))
            return ResponseEntity.badRequest().body(ApiUtils.error("8에서 20자 이내여야 합니다.:password", HttpStatus.BAD_REQUEST));

        //회원가입 성공
        //유저 생성
        User user = User.builder()
                .email(joinDTO.getEmail())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .username(joinDTO.getUsername())
                .roles("ROLE_USER")
                .build();
        //repo에 저장
        userRepository.save(user);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader(value = "Authorization", required = false) String authHeader, @RequestBody UserRequest.LoginDTO loginDTO) {
            //검증 단계
            String email = loginDTO.getEmail();
            String password = loginDTO.getPassword();
            //올바른 이메일 형식인지 확인
            if (!email.contains("@"))
                return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요:email", HttpStatus.BAD_REQUEST));
            //유효한 비밀번호인지 확인
            if (!isValidPassword(password))
                return ResponseEntity.badRequest().body(ApiUtils.error("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password", HttpStatus.BAD_REQUEST));
            //인증 확인
            if (authHeader == null || authHeader.isEmpty())
                return ResponseEntity.badRequest().body(ApiUtils.error("인증되지 않았습니다", HttpStatus.UNAUTHORIZED));
            //비밀번호 길이 검증
            int passwordLength = loginDTO.getPassword().length();
            if(!(passwordLength>=8 && passwordLength <= 20))
                return ResponseEntity.badRequest().body(ApiUtils.error("8에서 20자 이내여야 합니다.:password", HttpStatus.BAD_REQUEST));

            //로그인 수행
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
            Authentication authentication;
            //로그인 성공, 실패 여부 확인
            try {
                authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return ResponseEntity.badRequest().body(ApiUtils.error("email 또는 password가 올바르지 않습니다", HttpStatus.BAD_REQUEST));
            }
            CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = JWTProvider.create(myUserDetails.getUser());

            //로그인 성공
            return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
    }

    //비밀번호 유효성 검사
    private boolean isValidPassword(String password) {
        boolean hasLetter = false; //문자 여부
        boolean hasDigit = false; //숫자 여부
        boolean hasSpecialCharacter = false; //특수문자 여부
        for (char c:password.toCharArray()){
            if(Character.isLetter(c)) hasLetter=true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (isSpecialCharacter(c)) hasSpecialCharacter = true;
            if(hasLetter && hasDigit && hasSpecialCharacter) break;
        }
        //문자,숫자,특수문자가 있어야하고, 공백이 없어야한다.
        return hasLetter && hasDigit && hasSpecialCharacter && !password.contains(" ");
    }
    //특수 문자 포함하는지 확인
    private boolean isSpecialCharacter(char c) {
        String specialCharacters = "!@#$%^&*()-_=+[]{};:'\"\\|<>,.?/~`";
        return specialCharacters.contains(String.valueOf(c));
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody UserRequest.CheckEmailDTO emailDTO) {
        //요청 email 얻기
        String email = emailDTO.getEmail();

        //repository에서 email이 존재하는지 확인
        Optional<User> byEmail = userRepository.findByEmail(email);

        if (byEmail.isPresent()) { //email이 이미 존재하면
            return ResponseEntity.badRequest().body(ApiUtils.error("동일한 이메일이 존재합니다:email ", HttpStatus.BAD_REQUEST));
        } else { //email 중복이 아님
            if (email.contains("@")) //이메일 형식이면
                return ResponseEntity.ok(ApiUtils.success(null));
            else
                return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요:email", HttpStatus.BAD_REQUEST));
        }
    }

}
