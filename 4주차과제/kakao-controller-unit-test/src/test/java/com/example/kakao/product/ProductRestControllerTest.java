package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.OrderRestController;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@Import({
        SecurityConfig.class,
        FakeStore.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {

    @MockBean
    private ProductService productService;
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;
    @Autowired
    private FakeStore fakeStore;


    @Test
    public void findAll_test() throws Exception{
        // given
        int page = 0;

        List<Product> products = fakeStore.getProductList().stream().skip(page*9).limit(9).collect(Collectors.toList());
        List<ProductResponse.FindAllDTO> responseDTOS =
                products.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());

        Mockito.when(productService.findAll(anyInt())).thenReturn(responseDTOS);

        // when

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .queryParam("page", "0")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String res = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("res : " + res);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

    }

    @Test
    public void findById() throws Exception{
        //given
        int id = 1;
        // 상품 찾기
        Product product = fakeStore.getProductList().stream()
                .filter(product1 -> product1.getId()==id)
                .findFirst().get();
        // 옵션 찾기
        List<Option> options = fakeStore.getOptionList().stream()
                .filter(option -> option.getProduct().getId()==id)
                .collect(Collectors.toList());

        Mockito.when(productService.toFindByIdDTO(anyInt())).thenReturn(
                new ProductResponse.FindByIdDTO(product, options)
        );


        // when

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String res = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("res : " + res);

        // then

        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

    }

}
