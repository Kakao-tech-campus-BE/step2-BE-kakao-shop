package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
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

import java.util.List;
import static java.util.Arrays.asList;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})

@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {

    @MockBean
    private FakeStore fakeStore;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void fineAllTest() throws Exception {
        // given
        List<Product> productsList = asList(
                new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/1", 1000),
                new Product(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "description", "/image/2", 2000)
        );

        // stub
        Mockito.when(fakeStore.getProductList()).thenReturn(productsList);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.length()").value(2));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.[0].id").value(1));
    }

    @Test
    public  void findByIdTest() throws Exception {
        // given
        Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/path", 1000);

        List<Option> optionList = asList(
                new Option(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
                new Option(2, product, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
                new Option(3, product, "고무장갑 베이지 S(소형) 6팩", 9900),
                new Option(4, product, "뽑아쓰는 키친타올 130매 12팩", 16900),
                new Option(5, product, "2겹 식빵수세미 6매", 8900)
        );

        // stub
        Mockito.when(fakeStore.getProductList()).thenReturn(asList(product));
        Mockito.when(fakeStore.getOptionList()).thenReturn(optionList);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/1")
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(10000));
    }
}
