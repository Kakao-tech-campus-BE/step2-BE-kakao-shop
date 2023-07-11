package com.example.kakaoshop._core.security;

import com.example.kakaoshop.domain.account.Account;
import com.example.kakaoshop.domain.account.AccountJPARepository;
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
    private final AccountJPARepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Account> userOP = userRepository.findByEmail(email);

        if (userOP.isEmpty()) {
            log.warn("로그인에 실패하였습니다.");
            return null;
        } else {
            Account accountPS = userOP.get();
            return new CustomUserDetails(accountPS);
        }
    }
}
