package com.example.kakao;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * ApiDoc 생성을 위해서도 필요
 */
@ActiveProfiles("test")
@Transactional
@Sql("classpath:db/teardown.sql")
@WithUserDetails(value = "ssarmango@nate.com")
@AutoConfigureRestDocs(uriScheme = "http")
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class E2ETest {

  @Autowired
  protected WebApplicationContext ctx;

  @Autowired
  protected ObjectMapper om;

  @Autowired
  protected MockMvc mvc;

  protected ResultActions resultActions;

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

  @AfterEach
  void generateApiDocAfterTest(TestInfo testInfo) throws Exception {
    String className = testInfo.getTestClass().isPresent() ?
      testInfo.getTestClass().get().getSimpleName() : "";

    generateApiDoc(resultActions,  className);
  }

  protected void generateApiDoc(ResultActions resultActions, String className) throws Exception {
    resultActions.andDo(MockMvcRestDocumentationWrapper
      .document("{class-name}/{method-name}",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        resource(
          ResourceSnippetParameters.builder()
            .description(className)
            .requestFields()
            .responseFields()
            .build()
        )
      )
    );
  }

  protected void expectSuccess() throws Exception {
    resultActions.andExpect(jsonPath("$.success").value("true"))
      .andExpect(jsonPath("$.error").isEmpty());
  }

  protected void expectFail(String message, int status) throws Exception {
    resultActions.andExpect(jsonPath("$.success").value("false"))
      .andExpect(jsonPath("$.response").isEmpty())
      .andExpect(jsonPath("$.error.message").value(message))
      .andExpect(jsonPath("$.error.status").value(status));
  }

  // 메시지 검증이 귀찮음.
  protected void expectFail(int status) throws Exception {
    resultActions.andExpect(jsonPath("$.success").value("false"))
      .andExpect(jsonPath("$.response").isEmpty())
      .andExpect(jsonPath("$.error.status").value(status));
  }

}
