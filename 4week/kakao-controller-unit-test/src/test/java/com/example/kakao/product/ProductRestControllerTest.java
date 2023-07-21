package com.example.kakao.product;


import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Import({
        GlobalExceptionHandler.class,
        SecurityConfig.class,
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @MockBean
    private FakeStore fakeStore;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @BeforeEach
    public void setUp() throws Exception {
        // stub
        List<Product> products = Arrays.asList(
                new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000),
                new Product(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000)
        );
        Mockito.when(fakeStore.getProductList()).thenReturn(products);

        Mockito.when(fakeStore.getOptionList()).thenReturn(
                Arrays.asList(
                        new Option(1, products.get(0), "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
                        new Option(2, products.get(0), "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
                        new Option(3, products.get(0), "고무장갑 베이지 S(소형) 6팩", 9900),
                        new Option(4, products.get(0), "뽑아쓰는 키친타올 130매 12팩", 16900),
                        new Option(5, products.get(0), "2겹 식빵수세미 6매", 8900),
                        new Option(6, products.get(1), "22년산 햇단밤 700g(한정판매)", 9900),
                        new Option(7, products.get(1), "22년산 햇단밤 1kg(한정판매)", 14500),
                        new Option(8, products.get(1), "밤깎기+다회용 구이판 세트", 5500)
                        )
        );
    }

    @Test
    public void findByAll_test() throws Exception {
        // given


        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
        );
        result.andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].productName").value("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].price").value(2000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }

    @Test
    public void findById_test() throws Exception {
        // given
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", 1)
        );
        result.andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.description").value(""));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(1000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(10000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[1].price").value(10900));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }
}
