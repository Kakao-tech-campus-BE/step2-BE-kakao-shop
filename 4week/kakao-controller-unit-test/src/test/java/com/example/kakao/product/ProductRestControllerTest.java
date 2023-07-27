package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Import({
        SecurityConfig.class,
        FakeStore.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {
    @Autowired
    private MockMvc mvc;


    @DisplayName("[/products]상품 목록 가져오기 테스트")
    @Test
    public void findAll_test() throws Exception {
        // given
        int page = 0;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products?page="+page)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000));
    }


    @DisplayName("[/products/{id}]개별 상품 상세 조회 테스트")
    @Test
    public void findById_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/"+id)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(1000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(10000));
    }

    @DisplayName("[/products/{id}]존재하지 않는 상품 조회 테스트")
    @Test
    public void findById_id_fail_test() throws Exception {
        // given
        int id = 30;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/"+id)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다:30"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));
    }
}
