package com.example.kakao._core.security;


import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJPARepository userJPARepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<User> userOP = userJPARepository.findByEmail(email);
        User user = userJPARepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일로 가입된 회원을 찾지 못했습니다.: " + email));

        return new CustomUserDetails(user);
    }
}
