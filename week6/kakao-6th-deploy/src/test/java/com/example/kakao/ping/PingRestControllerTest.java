package com.example.kakao.ping;

import com.example.kakao.MyRestDoc;
import com.example.kakao._core.errors.exception.user.UserException;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * JWT 토큰 인증 테스트를 위한 테스트
 */

@DisplayName("핑퐁_JWT_인증_컨트롤러_테스트")
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PingRestControllerTest extends MyRestDoc {

    private final String snippets = "{class-name}/{method-name}";

    @Autowired
    private UserJPARepository userJPARepository;

    @DisplayName("핑퐁_테스트")
    @Test
    public void ping_pong_test() throws Exception {
        // given
        String email = "ssarmango@nate.com";
        String token = getJwtToken(email);

        // when
        ResultActions resultActions = mvc.perform(
                RestDocumentationRequestBuilders.get("/ping")
                        .header(JWTProvider.HEADER, token)
        );

        // then
        resultActions.andExpectAll(
                jsonPath("$.success").value("true"),
                jsonPath("$.response").value("pong"),
                jsonPath("$.error").value(nullValue())
        );
        resultActions.andDo(print()).andDo(document(
                snippets,
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(JWTProvider.HEADER).description("JWT token")
                )
        ));
    }

    @DisplayName("핑퐁_테스트_토큰_실패")
    @Test
    public void ping_pong_test_fail_token_invalid() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                RestDocumentationRequestBuilders.get("/ping")
        );

        // then
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("인증되지 않았습니다"),
                jsonPath("$.error.status").value(401)
        );
        resultActions.andDo(print()).andDo(document);
    }

    private String getJwtToken(String email) {
        User user = userJPARepository.findByEmail(email).orElseThrow( () -> new UserException.UserNotFoundByEmailException(email));
        return JWTProvider.create(user);
    }
}
