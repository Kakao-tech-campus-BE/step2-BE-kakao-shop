package com.example.kakao._core;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao.user.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;

public class WithMockUserCustomerSecurityContextFactory implements WithSecurityContextFactory<WithMockUserCustomer> {

    @Override
    public SecurityContext createSecurityContext(WithMockUserCustomer annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        List<GrantedAuthority> grantedAuthorities = new ArrayList();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        CustomUserDetails principal = new CustomUserDetails(User.builder()
                .id(1)
                .email(annotation.username()+"@nate.com")
                .password(passwordEncoder.encode("meta1234!"))
                .username(annotation.username())
                .roles("ROLE_USER")
                .build());
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, "email", principal.getAuthorities());
        context.setAuthentication(auth);

        return context;
    }
}
