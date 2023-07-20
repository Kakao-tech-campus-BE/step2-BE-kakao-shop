package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest
class ProductRestControllerTest extends DummyEntity {
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @MockBean
    private FakeStore fakeStore;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("전체 상품 조회하기 테스트")
    void findAll_test() throws Exception {
        //given
        int id = 1;

        //stub
        List<Product> products = productDummyList();
        Mockito.when(fakeStore.getProductList()).thenReturn(products);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(id))
        ).andExpect(status().isOk()).andDo(print());

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        // ResponseBody 검증
        List<Product> pagedProducts = products.stream().skip(id*9).limit(9).collect(Collectors.toList());
        String productsJson = om.writeValueAsString(ApiUtils.success(pagedProducts));

        assertThat(responseBody).isEqualTo(productsJson);
    }

    @Test
    @DisplayName("개별 상품 조회하기 테스트")
    void findById_test() throws Exception {
        //given
        int id = 1;

        //stub
        List<Product> products = productDummyList();
        Mockito.when(fakeStore.getProductList()).thenReturn(products);

        List<Option> options = optionDummyList(products);
        Mockito.when(fakeStore.getOptionList()).thenReturn(options);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", id)
        ).andExpect(status().isOk()).andDo(print());

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        // ReponseBody 검증
        List<Option> selectedOptions = new ArrayList<>();
        selectedOptions.add(options.get(0));
        selectedOptions.add(options.get(1));
        selectedOptions.add(options.get(2));
        selectedOptions.add(options.get(3));
        selectedOptions.add(options.get(4));

        Product selectedProduct = products.get(0);
        ProductResponse.FindByIdDTO findByIdDTO = new ProductResponse.FindByIdDTO(selectedProduct, selectedOptions);
        String productJson = om.writeValueAsString(ApiUtils.success(findByIdDTO));

        assertThat(responseBody).isEqualTo(productJson);
    }

    @Test
    @DisplayName("존재하지 않는 페이지 조회 테스트")
    void notExist_findAll_test() throws Exception {
        //given
        int id = -1;

        //stub
        List<Product> products = productDummyList();
        Mockito.when(fakeStore.getProductList()).thenReturn(products);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(id))
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        // 4xx 에러 검증 - 404
        result.andExpect(status().is4xxClientError()).andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 상품 조회 테스트")
    void notExist_findById_test() throws Exception {
        //given
        int id = 200;

        //stub
        List<Product> products = productDummyList();
        Mockito.when(fakeStore.getProductList()).thenReturn(products);

        List<Option> options = optionDummyList(products);
        Mockito.when(fakeStore.getOptionList()).thenReturn(options);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", id)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        // 4xx 에러 검증 - 404
        result.andExpect(status().is4xxClientError()).andDo(print());
    }

    @Test
    @DisplayName("잘못된 HTTP 메서드로 서버에 요청 테스트")
    void notAllowed_httpMethod_test() throws Exception {
        //given
        int id = 1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/products")
                        .param("page", String.valueOf(id))
        );

        //then
        // 4xx 에러 검증 - 405
        result.andExpect(status().is4xxClientError()).andDo(print());
    }
}