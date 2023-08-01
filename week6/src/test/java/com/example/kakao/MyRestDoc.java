package com.example.kakao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

@ExtendWith({ SpringExtension.class, RestDocumentationExtension.class })
public class MyRestDoc {
    protected MockMvc mvc;
    protected RestDocumentationResultHandler document; //문서로 생성

    @BeforeEach
    private void setup(WebApplicationContext webApplicationContext,
                       RestDocumentationContextProvider restDocumentation) {
        this.document = MockMvcRestDocumentation.document("{class-name}/{method-name}",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), //요청 formating
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())); //응답 formating

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true)) //filter 추가
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)) //설정 적용
                // .apply(SecurityMockMvcConfigurers.springSecurity())
                .alwaysDo(document) //문서 생성
                .build();
    }
}