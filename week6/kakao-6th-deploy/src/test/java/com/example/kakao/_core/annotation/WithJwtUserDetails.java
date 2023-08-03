/**
 * Jwt값으로 WithUserDetails를 구현하려고 했지만, 굳이 그럴 필요가 없을 것 같아 사용하지 않았습니다.
 * 해당 코드를 작성해봤던 이유는 @WithUserDetails를 사용할 경우 API문서에 request중 header부분이 나오지 않는 문제가 있었습니다.
 * mvc에 header를 추가해도 정상적으로 Authentication이 주입되지 않는 문제가 발생했고 이를 해결하는 과정에서 아래 코드를 작성했지만
 * 이 또한 header 추가와는 연관이 없기 때문에 사용하지 않았습니다.
 */


//package com.example.kakao._core.annotation;
//
//import com.example.kakao._core.factory.WithJwtUserDetailsSecurityContextFactory;
//import org.springframework.core.annotation.AliasFor;
//import org.springframework.security.test.context.support.TestExecutionEvent;
//import org.springframework.security.test.context.support.WithSecurityContext;
//
//
//import java.lang.annotation.*;
//
//@Target({ ElementType.METHOD, ElementType.TYPE })
//@Retention(RetentionPolicy.RUNTIME)
//@Inherited
//@Documented
//@WithSecurityContext(factory = WithJwtUserDetailsSecurityContextFactory.class)
//public @interface WithJwtUserDetails {
//
//    String value() default "";
//
//    @AliasFor(annotation = WithSecurityContext.class)
//    TestExecutionEvent setupBefore() default TestExecutionEvent.TEST_METHOD;
//}
