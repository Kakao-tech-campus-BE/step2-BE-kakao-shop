package com.example.kakao.user.util;

import com.example.kakaoshop.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class UserDummyData {
    public User newUser(Long id, String username) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .userId(id)
                .email(username + "@nate.com")
                .password(passwordEncoder.encode("meta1234!"))
                .username(username)
                .roles(username.equals("admin") ? "ROLE_ADMIN" : "ROLE_USER")
                .build();
    }
}
