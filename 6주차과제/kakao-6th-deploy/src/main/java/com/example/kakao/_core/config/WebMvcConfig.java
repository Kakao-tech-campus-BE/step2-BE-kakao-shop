package com.example.kakao._core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.path}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
// 1. 윈도우 절대경로 file:///c:/images/
// 2. 상대경로 file:./images/
// 3. 상대경로에 했을 때 실행파일 jar파일이 만들어지면, jar파일과 동일한 위치에 images 폴더가 있으면 찾는다.
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations("file:" + filePath)
                .setCachePeriod(60 * 60) // 초 단위 => 한시간 .resourceChain(true)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
