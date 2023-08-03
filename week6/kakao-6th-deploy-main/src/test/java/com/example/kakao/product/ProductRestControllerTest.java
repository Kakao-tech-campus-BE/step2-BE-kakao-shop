package com.example.kakao.product;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ProductRestControllerTest extends MyRestDoc {

    @Autowired
    private MockMvc mvc;

    @DisplayName("전체 상품 조회")
    @Test
    void findAll_test() throws Exception {
        //given

        //when
        ResultActions result = mvc.perform(
                get("/products")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(jsonPath("$.success").value(true));
        result.andExpect(jsonPath("$.response[0].id").value(1));
        result.andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response[0].description").value(""));
        result.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
        result.andExpect(jsonPath("$.response[0].price").value(1000));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("상품 상세 조회 성공")
    @Test
    void findById_success_test() throws Exception {
        //given
        int productId = 1;

        //when
        ResultActions result = mvc.perform(
                get("/products/{id}", productId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(jsonPath("$.success").value(true));
        result.andExpect(jsonPath("$.response.id").value(1));
        result.andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.description").value(""));
        result.andExpect(jsonPath("$.response.image").value("/images/1.jpg"));
        result.andExpect(jsonPath("$.response.price").value(1000));
        result.andExpect(jsonPath("$.response.starCount").value(5));
        result.andExpect(jsonPath("$.response.options[0].id").value(1));
        result.andExpect(jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.options[0].price").value(10000));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("상품 상세 조회 실패 - 상품 없음")
    @Test
    void findById_fail_test() throws Exception {
        //given
        int productId = 100;

        //when
        ResultActions result = mvc.perform(
                get("/products/{id}", productId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isNotFound());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다 : 100"));
        result.andExpect(jsonPath("$.error.status").value(404));
        result.andDo(print()).andDo(document);
    }
}
