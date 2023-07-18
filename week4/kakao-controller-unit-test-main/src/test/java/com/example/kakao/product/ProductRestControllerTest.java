package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import({
        SecurityConfig.class,
        FakeStore.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @DisplayName("전체 상품 목록 조회 테스트")
    @Test
    public void product_findAll_test() throws Exception {
        // given
        int page = 0;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(page))
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("전체 상품 목록 조회 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));

    }
    @DisplayName("개별 상품 목록 조회 테스트")
    @Test
    public void product_findById_test() throws Exception {
        // given
        int productId = 3;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", productId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("개별 상품 목록 조회 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(3));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(9));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!"));
    }
    @DisplayName("개별 상품 목록 조회 실패 테스트")
    @Test
    public void product_findById_fail_test() throws Exception {
        // given
        int productId = 100;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", productId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("개별 상품 목록 조회 실패 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(status().is4xxClientError());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다:100"));
    }
}
