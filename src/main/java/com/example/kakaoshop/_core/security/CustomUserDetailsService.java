package com.example.kakaoshop._core.security;

import com.example.kakaoshop.user.User;
import com.example.kakaoshop.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserJPARepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username : "+username);
        Optional<User> userOP = userRepository.findByEmail(username);

        // 로그인 실패시 예외 처리는 추후에 배울 예정
        if (userOP.isEmpty()) {
            log.warn("로그인에 실패하였습니다.");
            return null;
        } else {
            User userPS = userOP.get();
            return new CustomUserDetails(userPS);
        }
    }
}
