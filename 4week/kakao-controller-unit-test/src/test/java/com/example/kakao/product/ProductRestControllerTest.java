package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest extends DummyEntity {
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;
    @MockBean
    private ProductService productService;

    @MockBean
    private FakeStore fakeStore;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    private List<Product> mockProducts;
    @BeforeEach
    public void setUp() {
        mockProducts = productDummyList();
    }

    @Test
    public void findAll_test() throws Exception {

        // given
        int page = 0;
        String name = "page";
        String value = "0";

        // stub
        List<Product> mockProductsPaging = mockProducts.stream().skip(page*9).limit(9).collect(Collectors.toList());

        List<ProductResponse.FindAllDTO> mockDtoProducts = mockProductsPaging.stream()
                .map(ProductResponse.FindAllDTO::new)
                .collect(Collectors.toList());
        Mockito.when(productService.findAllPaging(0)).thenReturn(mockProductsPaging);
        Mockito.when(productService.toFindAllDTO(mockProductsPaging)).thenReturn(mockDtoProducts);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .queryParam(name,value)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
}
