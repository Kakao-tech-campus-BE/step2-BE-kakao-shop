package com.example.kakao._core.security;


import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.kakao.user.StringArrayConverter;
import com.example.kakao.user.User;
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
import java.util.List;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String prefixJwt = request.getHeader(JwtTokenProvider.HEADER);

        if (prefixJwt == null) {
            chain.doFilter(request, response);
            return;
        }

        String jwt = prefixJwt.replace(JwtTokenProvider.TOKEN_PREFIX, "");
        try {
            log.debug("디버그 : 토큰 있음");
            DecodedJWT decodedJWT = JwtTokenProvider.verify(jwt);
            int id = decodedJWT.getClaim("id").asInt();
            String roles = decodedJWT.getClaim("role").asString();

            StringArrayConverter sac = new StringArrayConverter();
            List<String> roleList = sac.convertToEntityAttribute(roles);

            User user = User.builder().id(id).roles(roleList).build();
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
