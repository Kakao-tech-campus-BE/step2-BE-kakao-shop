package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(ProductRestController.class)
public class ProductRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FakeStore fakeStore;

    @DisplayName("(기능4) 전체 상품 목록 조회 테스트")
    @Test
    void findAll_test() throws Exception {
        List<Product> productList = fakeStore.getProductList();

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(productList.get(0).getId()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value(productList.get(0).getProductName()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].description").value(productList.get(0).getDescription()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].image").value(productList.get(0).getImage()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(productList.get(0).getPrice()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.length()").value(9));
    }

    @DisplayName("(기능5) 개별 상품 상세 조회 테스트")
    @Test
    void findById_test() throws Exception {
        int productId = 1;

        List<Product> productList = fakeStore.getProductList();
        List<Option> optionList = fakeStore.getOptionList().stream()
                .filter(option -> option.getProduct().getId() == productId)
                .collect(Collectors.toList());

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(productList.get(0).getId()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value(productList.get(0).getProductName()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.description").value(productList.get(0).getDescription()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.image").value(productList.get(0).getImage()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(productList.get(0).getPrice()));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(optionList.get(0).getId()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value(optionList.get(0).getOptionName()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(optionList.get(0).getPrice()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options.length()").value(optionList.size()));
    }
}