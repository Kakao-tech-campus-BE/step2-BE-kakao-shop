package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.CartRestController;
import com.example.kakao.log.ErrorLogJPARepository;
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
import org.springframework.test.web.servlet.ResultMatcher;
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
    void findAll_test() throws Exception {
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
    void findAll_fail_test() throws Exception {
        //given
        int page = -1;

        //stub
        List<Product> productList = productDummyList();
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(page))
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        //then
        result.andExpect(status().is4xxClientError()).andDo(print());

    }

    @Test
    void findById_test() throws Exception {
        //given
        int productId = 1;

        //stub
        List<Product> productList = productDummyList();
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);

        List<Option> optionList = optionDummyList(productList);
        Mockito.when(fakeStore.getOptionList()).thenReturn(optionList);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", productId)
        ).andExpect(status().isOk()).andDo(print());

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        List<Option> findOptionList = new ArrayList<>();
        findOptionList.add(optionList.get(0));
        findOptionList.add(optionList.get(1));
        findOptionList.add(optionList.get(2));
        findOptionList.add(optionList.get(3));
        findOptionList.add(optionList.get(4));

        Product findProduct = productList.get(0);
        ProductResponse.FindByIdDTO findByIdDTO = new ProductResponse.FindByIdDTO(findProduct,findOptionList);
        String findProductString = om.writeValueAsString(ApiUtils.success(findByIdDTO));

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        Assertions.assertThat(responseBody).isEqualTo(findProductString);

    }

    @Test
    void findById_fail_test() throws Exception {
        //given
        int productId = -1;

        //stub
        List<Product> productList = productDummyList();
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);

        List<Option> optionList = optionDummyList(productList);
        Mockito.when(fakeStore.getOptionList()).thenReturn(optionList);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", productId)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        //then
        result.andExpect(status().is4xxClientError()).andDo(print());
    }
}