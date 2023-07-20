package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest  {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @MockBean
    private FakeStore fakeStore;

    @Autowired
    private ObjectMapper om;
    private List<Product> productList;
    private List<Option> optionList;

    private int id;
    @BeforeEach
    public void init(){
        productList = new ArrayList<>();
         id =1;
        Product product = Product.builder()
                .id(id)
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .description("")
                .image("/images/" + 1 + ".jpg")
                .price(10000)
                .build();
        optionList = Arrays.asList(
                Option.builder()
                        .product(product)
                        .id(1)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .price(10000)
                        .build(),
                Option.builder()
                        .product(product)
                        .id(2)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .price(10900)
                        .build(),
                Option.builder()
                        .product(product)
                        .id(3)
                        .optionName("고무장갑 베이지 S(소형) 6팩")
                        .price(9900)
                        .build(),
                Option.builder()
                        .product(product)
                        .id(4)
                        .optionName("뽑아쓰는 키친타올 130매 12팩")
                        .price(16900)
                        .build(),
                Option.builder()
                        .product(product)
                        .id(5)
                        .optionName("2겹 식빵수세미 6매")
                        .price(8900)
                        .build()
        );
        productList.add(product);

    }
    @Test
    public void findAll_test() throws Exception {
        // FakeStore의 getProductList 메서드가 호출되면 더미 데이터를 반환하도록 설정합니다.

        // give
        when(fakeStore.getProductList()).thenReturn(productList);


        List < ProductResponse.FindAllDTO > responseDTOs = productList.stream()
                .map(ProductResponse.FindAllDTO::new)
                .collect(Collectors.toList());


        String responseBody = om.writeValueAsString(ApiUtils.success(responseDTOs));
        System.out.println("FindALl : "+responseBody);
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                // 테스트 코드
                ).andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.content().json(responseBody)
                );
    }

    @Test
    public void findById_test() throws Exception {
        //given

        // FakeStore의 getProductList()와 getOptionList() 메서드의 반환값 설정
        when(fakeStore.getProductList()).thenReturn(productList);
        when(fakeStore.getOptionList()).thenReturn(optionList);

        Product product = productList.get(0);
        List<Option> filteredOptionList = optionList.stream().filter(option -> product.getId()== option.getProduct().getId()).collect(Collectors.toList());

        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, filteredOptionList);
        //when

        String responseBody = om.writeValueAsString(ApiUtils.success(responseDTO));
        System.out.println(responseBody);
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                //then
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.content().json(responseBody)
        );
        System.out.println(result.andReturn().getResponse().getContentAsString());
    }

}
