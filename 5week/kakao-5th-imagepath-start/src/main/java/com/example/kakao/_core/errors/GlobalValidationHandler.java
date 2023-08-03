package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.Exception400;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Aspect // 부가 기능과 해당 부가 기능을 어디에 적용할 지 정의
@Component
public class GlobalValidationHandler {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)") // 부가 기능을 적용할 곳 정의
    public void postMapping() { // 별칭
    }

    @Before("postMapping()") // 부가 기능이 핵심 로직 실행 전에 실행될 지 실행 후에 실행될 지를 결정
    public void validationAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;

                if (errors.hasErrors()) {
                    throw new Exception400(
                            errors.getFieldErrors().get(0).getDefaultMessage()+":"+errors.getFieldErrors().get(0).getField()
                    );
                }
            }
        }
    }
}
