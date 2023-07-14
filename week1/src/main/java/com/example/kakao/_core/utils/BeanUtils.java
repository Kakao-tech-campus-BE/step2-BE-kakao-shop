package com.example.kakao._core.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationcontext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtils.applicationcontext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationcontext.getBean(clazz);
    }
}
