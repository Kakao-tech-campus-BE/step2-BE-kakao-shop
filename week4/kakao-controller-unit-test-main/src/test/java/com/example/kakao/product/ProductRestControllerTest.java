package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
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
import java.util.List;

@Import({
        GlobalExceptionHandler.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {ProductRestController.class})
class ProductRestControllerTest extends DummyEntity {

    @MockBean
    private ProductService productService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("전체 상품 조회")
    void findAll_test() throws Exception {
        //given
        int page = 0;

        //stub
        List<Product> products = productDummyList();
        List<ProductResponse.FindAllDTO> responseDTO = new ArrayList<>();
        for (Product product : products) {
            responseDTO.add(new ProductResponse.FindAllDTO(product));
        }
        Mockito.when(productService.findAll(page)).thenReturn(responseDTO);
        System.out.println("responseDTO=" + responseDTO);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].description").value(""));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000));

    }

    @Test
    @DisplayName("상품 상세 조회")
    void findById_test() throws Exception {
        //given
        int productId = 1;

        //stub
        List<Product> productList = productDummyList();
        List<Option> optionList = optionDummyList(productList);
        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(productList.get(0), optionList);
        Mockito.when(productService.findById(productId)).thenReturn(responseDTO);
        System.out.println("responseDTO=" + responseDTO);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", productId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
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