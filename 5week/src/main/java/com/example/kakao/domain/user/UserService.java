package com.example.kakao.domain.user;

import com.example.kakao._core.errors.exception.BadRequestException;
import com.example.kakao._core.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserJPARepository userJPARepository;

    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) {
        sameCheckEmail(requestDTO.getEmail());

        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        userJPARepository.save(requestDTO.toEntity());
    }

    public String login(UserRequest.LoginDTO requestDTO) {
        User userPS = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(
          () -> new BadRequestException("이메일을 찾을 수 없습니다 : "+requestDTO.getEmail())
        );

        if(!passwordEncoder.matches(requestDTO.getPassword(), userPS.getPassword())){
            throw new BadRequestException("패스워드가 잘못입력되었습니다 ");
        }
        return JWTProvider.create(userPS);
    }

    public void sameCheckEmail(String email) {
        userJPARepository.findByEmail(email)
          .ifPresent(user -> {
              throw new BadRequestException("동일한 이메일이 존재합니다 : " + email);
          });
    }
}
