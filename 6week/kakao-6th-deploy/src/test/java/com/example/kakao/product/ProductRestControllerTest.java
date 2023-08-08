package com.example.kakao.product;

import com.example.kakao.MyRestDoc;
import com.example.kakao._core.util.CustomRequestPostProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ProductRestControllerTest extends MyRestDoc {
    @DisplayName("전체 조회 테스트")
    @Test
    public void findAll_test() throws Exception {
        // given teardown.sql

        // when
        ResultActions resultActions = mvc.perform(
                get("/products")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
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
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @DisplayName("전체 조회 페이징 테스트")
    @Test
    public void findAll_paging_test() throws Exception {
        // given teardown.sql
        Integer page = 1;
        // when
        ResultActions resultActions = mvc.perform(
                get("/products")
                        .param("page", String.valueOf(page))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
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
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("전체 조회 페이징 에러 테스트")
    @Test
    public void findAll_paging_error_test() throws Exception {
        // given teardown.sql
        Integer page = 100;
        // when
        ResultActions resultActions = mvc.perform(
                get("/products")
                    .param("page", String.valueOf(page))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .with(new CustomRequestPostProcessor())
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("상품이 없는 페이지입니다. 접근불가"));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("개별 물건 조회 테스트")
    @Test
    public void findById_test() throws Exception {
        // given teardown.sql
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/products/" + id)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
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
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @DisplayName("개별 물건 조회 테스트(향상된 쿼리)")
    @Test
    public void findByIdv2_test() throws Exception {
        // given teardown.sql
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/products/" + id + "/v2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
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
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("개별 물건 조회 에러 테스트")
    @Test
    public void findById_error_test() throws Exception {
        // given teardown.sql
        int id = 1000;

        // when
        ResultActions resultActions = mvc.perform(
                get("/products/" + id + "/v2")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .with(new CustomRequestPostProcessor())
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다 : "+id));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
