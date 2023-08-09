/**
 * Jwt값으로 WithUserDetails를 구현하려고 했지만, 굳이 그럴 필요가 없을 것 같아 사용하지 않았습니다.
 * 해당 코드를 작성해봤던 이유는 @WithUserDetails를 사용할 경우 API문서에 request중 header부분이 나오지 않는 문제가 있었습니다.
 * mvc에 header를 추가해도 정상적으로 Authentication이 주입되지 않는 문제가 발생했고 이를 해결하는 과정에서 아래 코드를 작성했지만
 * 이 또한 header 추가와는 연관이 없기 때문에 사용하지 않았습니다.
 */

//package com.example.kakao._core.factory;
//
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.example.kakao._core.annotation.WithJwtUserDetails;
//import com.example.kakao._core.security.CustomUserDetails;
//import com.example.kakao._core.security.JWTProvider;
//import com.example.kakao.user.User;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.test.context.support.WithSecurityContextFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Assert;
//import org.springframework.util.ClassUtils;
//
//@Component
//public final class WithJwtUserDetailsSecurityContextFactory implements WithSecurityContextFactory<WithJwtUserDetails> {
//    private static final boolean reactorPresent = ClassUtils.isPresent("reactor.core.publisher.Mono",
//            WithJwtUserDetailsSecurityContextFactory.class.getClassLoader());
//
//    private BeanFactory beans;
//
//    @Autowired
//    public WithJwtUserDetailsSecurityContextFactory(BeanFactory beans) {
//        this.beans = beans;
//    }
//
//    @Override
//    public SecurityContext createSecurityContext(WithJwtUserDetails withJwtUser) {
//        String jwtToken = withJwtUser.value();
//        Assert.hasLength(jwtToken, "value() must be non empty String");
//        DecodedJWT decodedJWT = JWTProvider.verify(jwtToken);
//        int id = decodedJWT.getClaim("id").asInt();
//        String roles = decodedJWT.getClaim("role").asString();
//        User user = User.builder().id(id).roles(roles).build();
//        UserDetails myUserDetails = new CustomUserDetails(user);
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(
//                        myUserDetails,
//                        myUserDetails.getPassword(),
//                        myUserDetails.getAuthorities()
//                );
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authentication);
//        return context;
//    }
//}
