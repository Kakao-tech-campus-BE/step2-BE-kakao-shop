package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyInt;

@Import({
        GlobalExceptionHandler.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {ProductRestController.class})
class ProductRestControllerTest extends DummyEntity {


    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @MockBean
    ProductService productService;
    @Autowired
    private MockMvc mvc;


    @Autowired
    private ObjectMapper om;
    @Test
    @DisplayName("post - /products 전체 상품 조회")
    void findAll() throws Exception {
        // given
        int page = 0;
        // stub
        List<Product> productDummy = productDummyList();
        List<ProductResponse.FindAllDTO> responseDTO = productDummy.stream().skip(page*9).limit(9).map(p -> new ProductResponse.FindAllDTO(p)).collect(Collectors.toList());
        Mockito.when(productService.findAll(anyInt())).thenReturn(responseDTO);
        String DTOjson = om.writeValueAsString(responseDTO);
        System.out.println("테스트 : "+DTOjson);
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", Objects.toString(page))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].image").value("/images/1.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.size()").value(9));




    }
    @Test
    @DisplayName("get - /products/{id} 개별 상품 조회")
    void findById() throws Exception {
        // given
        String productid = "1";
        // stub
        List<Product> productDummy = productDummyList();
        List<Option> optionDummy = optionDummyList(productDummy);
        List<Option> filterdOptions = optionDummy.stream().filter(o-> o.getProduct().getProductName().equals("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")).collect(Collectors.toList());
        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(productDummy.get(0),filterdOptions);
        Mockito.when(productService.findById(anyInt())).thenReturn(responseDTO);
        String DTOjson = om.writeValueAsString(responseDTO);
        System.out.println("테스트 : "+DTOjson);
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/"+productid)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.image").value("/images/1.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.starCount").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.options.size()").value(5));

    }




}