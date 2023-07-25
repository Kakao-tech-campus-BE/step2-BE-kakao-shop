package com.example.kakao._core.util;

import com.example.kakao._core.security.JWTProvider;
import com.example.kakao.domain.user.User;

public class DummyJwt {
  public static String generate() {
    User user = User.builder()
      .id(1)
      .email("ssarmango@nate.com")
      .roles("ROLE_USER")
      .build();
    return JWTProvider.create(user);
  }
}
