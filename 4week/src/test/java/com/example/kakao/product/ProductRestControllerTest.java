package com.example.kakao.product;


import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
// ver 1
@Import({
        SecurityConfig.class,  //  실수 1 - 포스트맨에선 로그인 안해도 반응이 오길래 필요없는 줄 알았는데, 얘 import 안하면 반응이 없음
        GlobalExceptionHandler.class,
        FakeStore.class
})

@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest extends DummyEntity{
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;


    @Test
    void findAllProducts_test() throws Exception {
        //given

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(1))  // 실수 2 - 정수 1이 아닌 string 1..
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+ responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    void findProductById_test() throws Exception {
        //given
        int id = 1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+ responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    void findProductById_test_failure_notFound() throws Exception {
        //given
        int id = 100; // 존재하지 않는 상품 ID

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+ responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}*/


// ver 2 - FakeStore를 MockBean으로 해서 stub으로 Mockito를 사용하면, findAllProducts_test()는 작동하는데 findProductById_test()는
//         작동하지 않음
@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})

@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest extends DummyEntity{
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @MockBean
    private FakeStore fakeStore;

    @Autowired
    private MockMvc mvc;


    @Test
    void findAllProducts_test() throws Exception {
        //given

        //stub
        List<Product> products = productDummyList();
        Mockito.when(fakeStore.getProductList()).thenReturn(products);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+ responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }


    @Test
    void findProductById_test() throws Exception {
        //given
        int id = 1;

        //stub
        List<Product> productList = productDummyList();
        List<Option> optionList = optionDummyList(productList);

        Mockito.when(fakeStore.getProductList()).thenReturn(productList);
        Mockito.when(fakeStore.getOptionList()).thenReturn(optionList);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+ responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        // 작동 하지 않음
    }
}
 //*/