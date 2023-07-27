package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {ProductRestController.class})
class ProductRestControllerTest extends DummyEntity {

    @MockBean
    private FakeStore fakeStore;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;


    @Test
    void findAllTest() throws Exception {
        //given
        int page = 0;

        //stub
        List<Product> productList = productDummyList();
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(page))
        ).andExpect(status().isOk()).andDo(print());
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        List<Product> findProduct = productList.stream().limit(9).collect(Collectors.toList());
        String findProductString = om.writeValueAsString(ApiUtils.success(findProduct));

        Assertions.assertThat(responseBody).isEqualTo(findProductString);
    }

    @Test
    void findByIdTest() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/" + id)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.description").value(""));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(1000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.starCount").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(10000));
    }
}