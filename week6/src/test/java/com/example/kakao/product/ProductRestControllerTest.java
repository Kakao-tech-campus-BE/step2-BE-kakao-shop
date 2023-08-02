package com.example.kakao.product;

import com.example.kakao.MyRestDoc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test") //test profile 사용
@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) //Test 메서드 실행 전에 Sql 실행
@AutoConfigureMockMvc //MockMvc 사용
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //통합테스트(SF-F-DS(Handler, ExHandler)-C-S-R-PC-DB) 다 뜬다.
public class ProductRestControllerTest extends MyRestDoc {

    @Test
    @DisplayName("전체 상품 목록 조회")
    public void findAll_test() throws Exception {
        // given teardown.sql

        // when
        ResultActions resultActions = mvc.perform(
                get("/products")
        );

        // eye (눈으로 본다.)
//        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response[0].description").value(""));
        resultActions.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(1000));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("전체 상품 목록 조회 - Param 사용")
    public void findAll_test_when_using_param() throws Exception {
        // given teardown.sql
            Integer page  = 1; //기본 0페이지, 1페이지(id=10)부터 시작

        // when
        //페이징 확인
        ResultActions resultActions = mvc.perform(
                get("/products")
                        .param("page", page.toString())
        );

        // eye (눈으로 본다.)
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response[0].id").value(10));
        resultActions.andExpect(jsonPath("$.response[0].productName").value("통영 홍 가리비 2kg, 2세트 구매시 1kg 추가증정"));
        resultActions.andExpect(jsonPath("$.response[0].description").value(""));
        resultActions.andExpect(jsonPath("$.response[0].image").value("/images/10.jpg"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(8900));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("개별 상품 상세 조회")
    public void findById_test() throws Exception {
        // given teardown.sql
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/products/" + id)
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
}
