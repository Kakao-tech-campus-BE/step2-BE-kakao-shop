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

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserJPARepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> userOP = userRepository.findByEmail(email);

        if (userOP.isEmpty()) {
            log.warn("로그인에 실패하였습니다.");
            return null;
        } else {
            User userPS = userOP.get();
            return new CustomUserDetails(userPS);
        }
    }
}
