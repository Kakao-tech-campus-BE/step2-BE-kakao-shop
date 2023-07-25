package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductResponse;
import com.example.kakao.product.ProductRestController;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {ProductRestController.class})
@AutoConfigureMockMvc
public class ProductRestControllerMockTest{

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mvc;

    private final FakeStore fakeStore = new FakeStore();

    private int id;

    @Test
    void findAll_product_test() throws Exception {
        //given
        int page = 0;
        List<Product> productList = fakeStore.getProductList().stream().skip(page*9).limit(9).collect(Collectors.toList());

        //stub
        Mockito.when(productService.findAll(page)).thenReturn(productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList()));

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON)

        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000));

    }

    @Test //MockMvc를 이용해서 테스트를 진행한 테스트 코드입니다.
    void findById_product_test() throws Exception{
        id = fakeStore.getProductList().get(0).getId();

        // Json 직렬화로 responseBody를 관찰하기 위한 코드
        Product product = fakeStore.getProductList().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        List<Option> optionList = fakeStore.getOptionList().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());

        //stub
        Mockito.when(productService.findById(id)).thenReturn(new ProductResponse.FindByIdDTO(product, optionList));

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON)

        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
    @Test
    void findById_product_null_test() throws Exception{
        //given
        id = 1000; // product에 없는 id 값

        //stub
        Mockito.when(productService.findById(id)).thenThrow(new Exception404("해당 상품을 찾을 수 없습니다"));

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/" + id)

        );
        //then
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        result.andExpectAll(
                status().is4xxClientError(), // 404 오류 확인
                MockMvcResultMatchers.jsonPath("$.success").value("false")
        );
    }
}