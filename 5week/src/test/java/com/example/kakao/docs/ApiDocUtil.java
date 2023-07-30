package com.example.kakao.docs;


import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@AutoConfigureRestDocs(uriScheme = "http")
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class})
public abstract class ApiDocUtil {

  @Autowired
  protected WebApplicationContext ctx;

  @Autowired
  protected ObjectMapper om;

  @Autowired
  protected MockMvc mvc;

  @MockBean
  private ErrorLogJPARepository errorLogJPARepository; // Exception Handler 의존성

  @BeforeEach
  void setUp(final RestDocumentationContextProvider restDocumentation) {
    mvc = MockMvcBuilders.webAppContextSetup(ctx)
      .apply(documentationConfiguration(restDocumentation))
      .addFilters(new CharacterEncodingFilter("UTF-8", true))
      .alwaysDo(print())
      .build();
  }

  protected void generateApiDoc(ResultActions resultActions) throws Exception {
    resultActions.andDo(MockMvcRestDocumentationWrapper
      .document("{class-name}/{method-name}",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint())
      )
    );
  }

  protected void generateApiDoc(ResultActions resultActions, String description) throws Exception {
    resultActions.andDo(MockMvcRestDocumentationWrapper
      .document("{class-name}/{method-name}",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        resource(
          ResourceSnippetParameters.builder()
            .description(description)
            .requestFields()
            .responseFields()
            .build()
        )
      )
    );
  }
}