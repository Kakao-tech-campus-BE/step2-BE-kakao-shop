package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.Exception400;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Aspect //Aspect(Advice + Pointcut)
@Component //IoC 컨테이너에 띄움
public class GlobalValidationHandler {
    //1. 어노테이션에 Pointcut 등록하기
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
    }

    @Before("postMapping()") //3. Join Point 적용
    //2. Advice 만들기
    public void validationAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof Errors) { //에러 존재시
                Errors errors = (Errors) arg;

                if (errors.hasErrors()) { //맨 첫번째 에러 정보로 400번 에러 던짐
                    throw new Exception400(
                            errors.getFieldErrors().get(0).getDefaultMessage()+":"+errors.getFieldErrors().get(0).getField()
                    );
                }
            }
        }
    }
}