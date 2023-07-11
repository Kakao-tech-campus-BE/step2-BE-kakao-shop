package com.example.kakaoshop._core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.kakaoshop.domain.account.Account;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTProvider {
    private JWTProvider() {}

    public static final Long EXP = 1000L * 60 * 60 * 48; // 48시간 - 테스트 하기 편함.
    public static final String TOKEN_PREFIX = "Bearer "; // 스페이스 필요함
    public static final String HEADER = "Authorization";
    public static final String SECRET = "MySecretKey";

    public static String create(Account account) {
        String jwt = JWT.create()
                .withSubject(account.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                .withClaim("id", account.getId())
                .withClaim("role", account.getRoles())
                .sign(Algorithm.HMAC512(SECRET));
        return TOKEN_PREFIX + jwt;
    }

    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        jwt = jwt.replace(JWTProvider.TOKEN_PREFIX, "");
        return JWT.require(Algorithm.HMAC512(SECRET))
                .build().verify(jwt);
    }
}