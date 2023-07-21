package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyInt;

@Import({
        FakeStore.class,
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {
    @MockBean
    private ProductService productService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private FakeStore fakeStore;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @DisplayName("상품 조회 테스트")
    @Test
    public void findAll_test() throws Exception {
        // given
        int page = 0;

        //stub
        // 1. 더미데이터 가져와서 페이징하기
        List<Product> productList = fakeStore.getProductList().stream().skip(page*9).limit(9).collect(Collectors.toList());

        // 2. DTO 변환
        List<ProductResponse.FindAllDTO> findAllDTOS =
                productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
        Mockito.when(productService.findAll(anyInt())).thenReturn(findAllDTOS);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", Integer.toString(page))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000));
    }
    @DisplayName("상품 옵션 조회 테스트")
    @Test
    public void findById_test() throws Exception {
        // given
        int id = 1;

        // stub
        // 1. 더미데이터 가져와서 상품 찾기
        Product product = fakeStore.getProductList().stream().filter(p -> p.getId() == id).findFirst().orElse(null);

        if(product == null) {
            Mockito.when(productService.findById(anyInt())).thenThrow(new Exception404("해당 상품을 찾을 수 없습니다:"+id));
        }
        else {
            // 2. 더미데이터 가져와서 해당 상품에 옵션 찾기
            List<Option> optionList = fakeStore.getOptionList().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());
            // 3. DTO 변환
            ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, optionList);
            Mockito.when(productService.findById(anyInt())).thenReturn(responseDTO);
        }

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(10000));
    }
}
