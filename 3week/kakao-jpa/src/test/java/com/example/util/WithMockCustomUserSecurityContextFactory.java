package com.example.util;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;


public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        CustomUserDetails myUserDetails = new CustomUserDetails(User.builder()
                .id(customUser.id())
                .email(customUser.username()+"@nate.com")
                .password(passwordEncoder.encode("meta1234!"))
                .username(customUser.username())
                .roles(customUser.username().equals("admin") ? "ROLE_ADMIN" : "ROLE_USER")
                .build());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        myUserDetails,
                        myUserDetails.getPassword(),
                        myUserDetails.getAuthorities()
                );

        securityContext.setAuthentication(authentication);

        return securityContext;
        /*
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id(customUser.id())
                .email(customUser.username()+"@nate.com")
                .password(passwordEncoder.encode("meta1234!"))
                .username(customUser.username())
                .roles(customUser.username().equals("admin") ? "ROLE_ADMIN" : "ROLE_USER")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(user);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        context.setAuthentication(token);
        return context;
         */
    }
}
