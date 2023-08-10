package com.example.kakao._core.errors;

import com.example.kakao._core.errors.exception.Exception400;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class GlobalValidationHandler {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
    }

    @Before("postMapping()")
    public void validationAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;
                if(errors.hasErrors()){
                    List<FieldError> fieldErrors = errors.getFieldErrors();
                    List<String> errorMessages = new ArrayList<>();
                    for(FieldError error : fieldErrors) {
                        errorMessages.add(error.getDefaultMessage()+":"+error.getField());
                    }
                    String parsingErrorMessages = errorMessages.toString().replace("[","").replace("]","");
                    throw new Exception400(
                            parsingErrorMessages
                    );
                }
                // 기존 코드
         /*       if (errors.hasErrors()) {
                    throw new Exception400(
                            errors.getFieldErrors().get(0).getDefaultMessage()+":"+errors.getFieldErrors().get(0).getField()
                    );
                }*/
            }
        }
    }
}
