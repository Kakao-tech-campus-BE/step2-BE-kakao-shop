package com.example.kakao.product;

import com.example.kakao.MyRestDoc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("제품_통합_테스트")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ProductRestControllerTest extends MyRestDoc {
    private final String snippets = "{class-name}/{method-name}";
    /**
     * 제품 전체 조회 테스트
     */

    @DisplayName("제품_전체_조회_테스트")
    @Test
    public void findAll_test() throws Exception {
        // given teardown.sql

        // when
        ResultActions resultActions = mvc.perform(
                get("/products")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response[0].description").value(""));
        resultActions.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(1000));
        resultActions.andDo(print()).andDo(document);
    }

    @DisplayName("제품_전체_조회_테스트_2페이지")
    @Test
    public void findAll_test_page_2() throws Exception {
        // given teardown.sql
        int page = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/products")
                        .queryParam("page", String.valueOf(page))
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response[0].id").value(10));
        resultActions.andExpect(jsonPath("$.response[0].productName").value("통영 홍 가리비 2kg, 2세트 구매시 1kg 추가증정"));
        resultActions.andExpect(jsonPath("$.response[0].description").value(""));
        resultActions.andExpect(jsonPath("$.response[0].image").value("/images/10.jpg"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(8900));
        resultActions.andDo(print())
                .andDo(document(
                        snippets,
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                            parameterWithName("page").description("page의 디폴트 값은 0이다.")
                        )
                ));
    }

    @DisplayName("제품_상세_조회_테스트")
    @Test
    public void findById_test() throws Exception {
        // given teardown.sql
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/products/{product_id}", id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.description").value(""));
        resultActions.andExpect(jsonPath("$.response.image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response.price").value(1000));
        resultActions.andDo(print())
                .andDo(document(
                        snippets,
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("product_id").description("제품ID")
                        )
                ));
    }

    @DisplayName("제품_상세_조회_테스트_실패_제품_없음")
    @Test
    public void findById_test_fail_no_product_data() throws Exception {
        // given teardown.sql
        int id = 100;

        // when
        ResultActions resultActions = mvc.perform(
                get("/products/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify

        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다 : " + id),
                jsonPath("$.error.status").value(404)
        );
        resultActions.andDo(print()).andDo(document);
    }
}
