package com.example.kakaoshop._core.security;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.kakaoshop.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String prefixJwt = request.getHeader(JWTProvider.HEADER);

        if (prefixJwt == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            DecodedJWT decodedJWT = JWTProvider.verify(prefixJwt);
            int id = decodedJWT.getClaim("id").asInt();
            String roles = decodedJWT.getClaim("role").asString();

            User user = User.builder().id(id).roles(roles).build();
            CustomUserDetails myUserDetails = new CustomUserDetails(user);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            myUserDetails,
                            myUserDetails.getPassword(),
                            myUserDetails.getAuthorities()
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("디버그 : 인증 객체 만들어짐");
        } catch (SignatureVerificationException sve) {
            log.error("토큰 검증 실패");
        } catch (TokenExpiredException tee) {
            log.error("토큰 만료됨");
        } finally {
            chain.doFilter(request, response);
        }
    }
}