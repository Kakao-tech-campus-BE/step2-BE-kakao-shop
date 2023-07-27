package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Import({
        GlobalExceptionHandler.class,
        SecurityConfig.class,
        FakeStore.class
})
@WebMvcTest(controllers = {ProductRestController.class})
class ProductRestControllerTest {

    @Autowired
    private FakeStore fakeStore;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MockMvc mvc;


    @Test
    void findAll_test() throws Exception {
        // given
        int page = 0;

        // stub
        List<Product> productList = fakeStore.getProductList()
                .stream()
                .skip(page*9)
                .limit(9)
                .collect(Collectors.toList());
        List<ProductResponse.FindAllDTO> response = productList.stream()
                .map(ProductResponse.FindAllDTO::new)
                .collect(Collectors.toList());
        Mockito.when(productService.findAll(page)).thenReturn(response);
        // when
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .get("/products"));
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        result.andDo(MockMvcResultHandlers.print());
        // then
        result.andExpectAll(
                jsonPath("$.success").value(true),
                jsonPath("$.response.size()").value(9),
                jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"),
                jsonPath("$.response[8].productName").value("플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감")
        );
    }

    @Test
    void findById_test() throws Exception {
        // given
        int id = 3;

        // stub
        try {
            Product product = fakeStore.getProductList().stream()
                    .filter(p -> p.getId() == id)
                    .findFirst()
                    .orElseThrow();
            List<Option> optionList = fakeStore.getOptionList().stream()
                    .filter(option -> product.getId() == option.getProduct().getId())
                    .collect(Collectors.toList());
            ProductResponse.FindByIdDTO response = new ProductResponse.FindByIdDTO(product, optionList);
            Mockito.when(productService.findById(id)).thenReturn(response);
        } catch (NoSuchElementException e) {
            Mockito.when(productService.findById(id))
                    .thenThrow(new Exception404("해당 상품을 찾을 수 없습니다:" + id));
        }

        // when
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .get("/products/" + id));
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        result.andDo(MockMvcResultHandlers.print());

        // then
        result.andExpectAll(
                jsonPath("$.success").value(true),
                jsonPath("$.response.id").value(3),
                jsonPath("$.response.productName").value("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!"),
                jsonPath("$.response.price").value(30000),
                jsonPath("$.response.options.size()").value(6),
                jsonPath("$.response.options[0].id").value(9),
                jsonPath("$.response.options[0].price").value(29900)
        );
    }

    @Test
    void findById_fail_test() throws Exception {
        // given
        int id = 150;

        // stub
        try {
            Product product = fakeStore.getProductList().stream()
                    .filter(p -> p.getId() == id)
                    .findFirst()
                    .orElseThrow();
            List<Option> optionList = fakeStore.getOptionList().stream()
                    .filter(option -> product.getId() == option.getProduct().getId())
                    .collect(Collectors.toList());
            ProductResponse.FindByIdDTO response = new ProductResponse.FindByIdDTO(product, optionList);
            Mockito.when(productService.findById(id)).thenReturn(response);
        } catch (NoSuchElementException e) {
            Mockito.when(productService.findById(id))
                    .thenThrow(new Exception404("해당 상품을 찾을 수 없습니다:" + id));
        }

        // when
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .get("/products/" + id));
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        result.andDo(MockMvcResultHandlers.print());

        // then
        result.andExpectAll(
                jsonPath("$.success").value(false),
                jsonPath("$.response").isEmpty(),
                jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다:"+id),
                jsonPath("$.error.status").value(404)
        );
    }
}