package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception204;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.ProductOption;
import com.example.kakao.product.option.ProductOptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest extends DummyEntity{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductOptionService productOptionService;

    @MockBean
    private FakeStore fakeStore;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;


    private List<Product> products;
    private List<ProductOption> options;

    @BeforeEach
    public void setup() {
        products = productDummyList();
        options = optionDummyList(products);
        when(productService.findAll()).thenReturn(products);
        when(productService.findById(any(Integer.class))).thenReturn(products.get(0));
        when(productOptionService.findAll()).thenReturn(options);
    }


    @Test
    public void testFindAllNotEmpty() throws Exception {
        // Mocking
        given(productService.findAll()).willReturn(products);

        /*
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        */

        // Test
        mockMvc.perform(get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value(products.get(0).getProductName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].description").value(products.get(0).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].image").value(products.get(0).getImage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(products.get(0).getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[1].productName").value(products.get(1).getProductName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[1].description").value(products.get(1).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[1].image").value(products.get(1).getImage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[1].price").value(products.get(1).getPrice()));

        // Verify
        verify(productService, times(1)).findAll();
    }

    @Test
    public void testFindAllWrongPage() throws Exception {
        // Mocking
        given(productService.findAll()).willReturn(products);

        // Test
        ResultActions result = mockMvc.perform(get("/products").param("page", "-1"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("페이지를 찾을 수 없습니다.:-1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));

        // Verify
        verify(productService, times(0)).findAll();
    }

    @Test
    public void testFindAllEmpty() throws Exception {
        // Mocking
        given(productService.findAll()).willReturn(Collections.emptyList());

        // Test
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value("데이터가 없습니다.:0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").doesNotExist());

        // Verify
        verify(productService, times(1)).findAll();
    }

    @Test
    public void testFindById() throws Exception {
        // Mocking
        Product product = products.get(0);
        given(productService.findById(1)).willReturn(product);
        given(productOptionService.findAll()).willReturn(options);

/*
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
*/
        // Test
        mockMvc.perform(get("/products/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value(product.getProductName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.description").value(product.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.image").value(product.getImage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(product.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(options.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].productoptionName").value(options.get(0).getOptionName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(options.get(0).getPrice()));

        // Verify
        verify(productService, times(1)).findById(1);
    }

    @Test
    public void testFindByIdWrongID() throws Exception {
        /// Mocking
        given(productService.findById(anyInt())).willThrow(new Exception404("유효하지 않은 상품 ID입니다. ID: -1"));


/*
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/-1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
*/

            // Test
            mockMvc.perform(get("/products/{id}", -1))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("유효하지 않은 상품 ID입니다. ID: -1"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));

            // Verify
            verify(productService, times(0)).findById(-1);
        }

    @Test
    public void testFindProductByIdProductNotFound() throws Exception {
        int productId = 1;

        // Mocking
        given(productService.findById(productId)).willReturn(null);

        // Test
        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + productId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다.:" + productId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));

        // Verify
        verify(productService, times(1)).findById(productId);
    }

}