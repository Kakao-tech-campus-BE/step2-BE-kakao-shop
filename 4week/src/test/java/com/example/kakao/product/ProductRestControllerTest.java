package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@Import({SecurityConfig.class})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {
    @MockBean
    private FakeStore fakeStore;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("전체 상품 조회하기")
    void findAll() throws Exception{
        // given
        // stub
        BDDMockito.given(fakeStore.getProductList()).willReturn(Arrays.asList(
                new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/path", 1000),
                new Product(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "description", "/image/path", 2000),
                new Product(3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "description", "/image/path", 30000)
        ));

        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/products").accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    @DisplayName("개별 상품 조회하기")
    void findById() throws Exception{
        // given
        int id = 1;
        Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/path", 1000);
        // stub
        BDDMockito.given(fakeStore.getProductList()).willReturn(Arrays.asList(product));
        BDDMockito.given(fakeStore.getOptionList()).willReturn(Arrays.asList(
                new Option(1, product, "지퍼백1", 5000),
                new Option(2, product, "주방용품1", 8000),
                new Option(3, product, "주방용품2", 9000)
        ));

        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/products/"+id).accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value("1"));
    }
}