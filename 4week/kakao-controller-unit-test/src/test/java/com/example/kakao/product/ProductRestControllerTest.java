package com.example.kakao.product;


import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Import({
       // FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {ProductRestController.class})
class ProductRestControllerTest {

    @MockBean
    FakeStore fakeStore;

    @Autowired
    private MockMvc mvc;


  //  @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findAll_test() throws Exception {
        //given
		int parameter = 0;

        //stub
        List<Product> productList = new ArrayList<>();
        Product p1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        Product p2 = new Product(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000);
		productList.add(p1);
        productList.add(p2);

        Mockito.when(fakeStore.getProductList()).thenReturn(productList);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .param("page", String.valueOf(parameter))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.[0].description").value(""));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.[0].image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.[0].price").value(1000));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    void findById_test() throws Exception{
		//given
        int id=1;

        //stub
        List<Product> productList = new ArrayList<>();
        Product p1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        Product p2 = new Product(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000);
        productList.add(p1);
        productList.add(p2);

        Mockito.when(fakeStore.getProductList()).thenReturn(productList);

        Mockito.when(fakeStore.getOptionList()).thenReturn(Arrays.asList(
                new Option(1, p1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
                new Option(2, p1, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
                new Option(3, p1, "고무장갑 베이지 S(소형) 6팩", 9900)
        ));


        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value("10000"));

    }


}