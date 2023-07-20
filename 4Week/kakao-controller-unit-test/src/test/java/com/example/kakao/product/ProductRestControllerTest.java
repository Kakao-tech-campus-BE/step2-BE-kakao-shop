package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {
    @MockBean
    private ProductService productService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    private final MockMvc mvc;

    private final ObjectMapper om;



    @Autowired
    public ProductRestControllerTest(MockMvc mvc, ObjectMapper om) {
        this.mvc = mvc;
        this.om = om;
    }

    @DisplayName("상품 목록 조회 테스트")
    @Test
    public void findAll_test() throws Exception {
        given(productService.findAllDTO(any())).willReturn(
                Arrays.asList(
                    new ProductResponse.FindAllDTO(new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/path", 1000)),
                    new ProductResponse.FindAllDTO(new Product(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "description", "/image/path", 2000)),
                    new ProductResponse.FindAllDTO(new Product(3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "description", "/image/path", 30000))
                )
        );

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/products")
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.length()").value(3));
    }

    @DisplayName("상품 상세 조회 테스트")
    @Test
    public void findById() throws Exception {
        Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/path", 1000);
        List<Option> optionList = Arrays.asList(
                new Option(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
                new Option(2, product,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
                new Option(3, product,"고무장갑 베이지 S(소형) 6팩", 9900),
                new Option(4, product,"뽑아쓰는 키친타올 130매 12팩", 16900),
                new Option(5, product, "2겹 식빵수세미 6매", 8900)
        );

        given(productService.findByIdProduct(anyInt())).willReturn(product);

        given(productService.findOptionByProductID(anyInt())).willReturn(optionList);

        given(productService.findByIdDTO(any(),anyList())).willReturn(
                new ProductResponse.FindByIdDTO(product, optionList)
        );

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/products/1")
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.options.length()").value(5));
    }
}
