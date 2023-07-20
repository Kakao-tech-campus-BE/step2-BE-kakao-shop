package com.example.kakao._core.util;


import com.example.kakao.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
        @Override
        public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            List<GrantedAuthority> grantedAuthorities = new ArrayList();
            grantedAuthorities.add(new SimpleGrantedAuthority("USER"));

            User principal = User.builder().id(1).roles("ROLE_USER").build();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    principal, principal.getPassword(), principal.getAuthorities());

            context.setAuthentication(authentication);
            return context;
        }


}
