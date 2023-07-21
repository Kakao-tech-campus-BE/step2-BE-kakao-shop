package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.cart.CartResponse;
import com.example.kakao.product.option.Option;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void findAll_test() throws Exception {
        List<Product> productList = fakeStore.getProductList();

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);


        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(productList.get(0).getId()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value(productList.get(0).getProductName()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].description").value(productList.get(0).getDescription()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].image").value(productList.get(0).getImage()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(productList.get(0).getPrice()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.length()").value(9));
    }

    @Test
    void findById_test() throws Exception {
        List<Product> productList = fakeStore.getProductList();
        Product product = productList.get(0);
        List<Option> optionList = fakeStore.getOptionList().stream()
                .filter(option -> option.getProduct().getId() == product.getId())
                .collect(Collectors.toList());

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
}