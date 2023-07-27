package com.example.kakao._core.errors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@RequiredArgsConstructor
@Slf4j
@Component
public class ExceptionLoggingHandler {

  @After("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
  public void logAfterException(JoinPoint joinPoint) {
    Exception ex = (Exception) joinPoint.getArgs()[0];

    log.error(
      "Exception with {} : {}",
      joinPoint.getSignature().getName(),
      ex.getMessage()
    );
  }

}
