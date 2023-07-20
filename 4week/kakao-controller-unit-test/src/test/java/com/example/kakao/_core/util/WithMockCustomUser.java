package com.example.kakao._core.util;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.example.kakao.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String username() default "yunzae";

    String vlaue() default "yunzae2";

}

