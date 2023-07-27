package com.example.kakao.user;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserJPARepository userJPARepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) {
        // 이미 가입된 이메일 확인
        Optional<User> userOP = userJPARepository.findByEmail(requestDTO.getEmail());
        if (userOP.isPresent()) {
            throw new Exception400("동일한 이메일이 존재합니다 : " + requestDTO.getEmail());
        }
        // 비밀번호 암호화
        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        // 회원가입
        userJPARepository.save(requestDTO.toEntity());
    }

    public String login(UserRequest.LoginDTO requestDTO) {
        // 이메일 잘못 입력 확인
        User userPS = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(
                () -> new Exception404("이메일을 찾을 수 없습니다 : "+requestDTO.getEmail())
        );
        // 비밀번호 잘못 입력 확인
        if(!passwordEncoder.matches(requestDTO.getPassword(), userPS.getPassword())){
            throw new Exception400("패스워드가 잘못 입력되었습니다 ");
        }
        // 로그인
        return JWTProvider.create(userPS);
    }
}
