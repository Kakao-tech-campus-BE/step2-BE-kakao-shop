package com.example.kakao.user;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception401;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserJPARepository userJPARepository;


    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) {
        sameCheckEmail(requestDTO.getEmail());

        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        try {
            userJPARepository.save(requestDTO.toEntity());
        }catch (Exception e){
            throw new Exception500(e.getMessage());
        }

    }

    public String login(UserRequest.LoginDTO requestDTO) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
            return JwtTokenProvider.create(myUserDetails.getUser());
        }catch (Exception e){
            throw new Exception401("인증되지 않았습니다");
        }
    }

    public void sameCheckEmail(String email) {
        Optional<User> userOP = userJPARepository.findByEmail(email);
        if(userOP.isPresent()){
            throw new Exception400("동일한 이메일이 존재합니다 : "+email);
        }
    }
}
