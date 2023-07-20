package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Import({SecurityConfig.class}) // 이것을 설정해주지 않으면 프로젝트에 spring security가 설정되어 있기 때문에, get요청조차도 실패한다. (401 unauthorized)
@WebMvcTest(controllers = {ProductRestController.class})
class ProductRestControllerTest extends DummyEntity {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FakeStore fakeStore;

    @Test
    void findAll_test() throws Exception {

        // given
        List<Product> mockProducts = productList;

        // mock
        when(fakeStore.getProductList()).thenReturn(
                mockProducts.stream().skip(0).limit(9).collect(Collectors.toList())
        );

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andDo(print());

        // then
        result.andExpectAll(MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.success").value("true"),
                MockMvcResultMatchers.jsonPath("$.response[0].id").value(mockProducts.get(0).getId()),
                MockMvcResultMatchers.jsonPath("$.response[0].productName").value(mockProducts.get(0).getProductName()),
                MockMvcResultMatchers.jsonPath("$.response[0].description").value(mockProducts.get(0).getDescription()),
                MockMvcResultMatchers.jsonPath("$.response[0].image").value(mockProducts.get(0).getImage()),
                MockMvcResultMatchers.jsonPath("$.response[0].price").value(mockProducts.get(0).getPrice()),
                MockMvcResultMatchers.jsonPath("$.error").isEmpty()
                );
    }

    @Test
    void findAll_with_param_test() throws Exception {

        // given
        List<Product> mockProducts = productList;
        int page = 0;
        int limit = 9;

        // mock
        when(fakeStore.getProductList()).thenReturn(
                mockProducts.stream().skip(page * limit).limit(limit).collect(Collectors.toList())
        );


        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(page))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andDo(print());

        // then
        result.andExpectAll(MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.success").value("true"),
                MockMvcResultMatchers.jsonPath("$.response[0].id").value(mockProducts.get(page * limit).getId()),
                MockMvcResultMatchers.jsonPath("$.response[0].productName").value(mockProducts.get(page * limit).getProductName()),
                MockMvcResultMatchers.jsonPath("$.response[0].description").value(mockProducts.get(page * limit).getDescription()),
                MockMvcResultMatchers.jsonPath("$.response[0].image").value(mockProducts.get(page * limit).getImage()),
                MockMvcResultMatchers.jsonPath("$.response[0].price").value(mockProducts.get(page * limit).getPrice()),
                MockMvcResultMatchers.jsonPath("$.error").isEmpty()
        );
    }

    @Test
    void findById_test() throws Exception {

        // given
        int id = 1;
        List<Product> products = productList;
        List<Option> options = optionList;

        // mock
        when(fakeStore.getProductList()).thenReturn(products);
        when(fakeStore.getOptionList()).thenReturn(options);

        Product product = products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        List<Option> productOptions = options.stream()
                .filter(option -> product.getId() == option.getProduct().getId())
                .collect(Collectors.toList());


        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/products/" + id)
                .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print());

        // then
        result.andExpectAll(MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.success").value("true"),
                MockMvcResultMatchers.jsonPath("$.response.id").value(id),
                MockMvcResultMatchers.jsonPath("$.response.productName").value(product.getProductName()),
                MockMvcResultMatchers.jsonPath("$.response.description").value(product.getDescription()),
                MockMvcResultMatchers.jsonPath("$.response.image").value(product.getImage()),
                MockMvcResultMatchers.jsonPath("$.response.price").value(product.getPrice()),
                MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(productOptions.get(0).getId()),
                MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value(productOptions.get(0).getOptionName()),
                MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(productOptions.get(0).getPrice()),
                MockMvcResultMatchers.jsonPath("$.error").isEmpty()
        );
    }
}