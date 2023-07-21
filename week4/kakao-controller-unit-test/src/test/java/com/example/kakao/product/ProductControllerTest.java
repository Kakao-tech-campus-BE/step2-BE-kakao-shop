package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Import({
        SecurityConfig.class,
        FakeStore.class
})

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
    @MockBean
    ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Test
    public void findAll_test() throws Exception {
        //given

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response[0].id").value(1));
        result.andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response[0].description").value(""));
        result.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
        result.andExpect(jsonPath("$.response[0].price").value(1000));
        result.andExpect(jsonPath("$.response[1].id").value(2));
        result.andExpect(jsonPath("$.response[1].productName").value("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율"));
        result.andExpect(jsonPath("$.response[1].description").value(""));
        result.andExpect(jsonPath("$.response[1].image").value("/images/2.jpg"));
        result.andExpect(jsonPath("$.response[1].price").value(2000));
    }

    @Test
    public void findById_test() throws Exception {
        //given
        int productId = 1;
        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/"+productId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response.id").value(1));
        result.andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.description").value(""));
        result.andExpect(jsonPath("$.response.image").value("/images/1.jpg"));
        result.andExpect(jsonPath("$.response.price").value(1000));
        result.andExpect(jsonPath("$.response.options[0].id").value(1));
        result.andExpect(jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.options[0].price").value(10000));
        result.andExpect(jsonPath("$.response.options[1].id").value(2));
        result.andExpect(jsonPath("$.response.options[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(jsonPath("$.response.options[1].price").value(10900));
    }
}
