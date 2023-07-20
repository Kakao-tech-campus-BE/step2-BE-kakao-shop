package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.CustomUserDetailsService;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.cart.CartResponse;
import com.example.kakao.cart.CartRestController;
import com.example.kakao.cart.CartService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {ProductRestController.class})
class ProductRestControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;
    @Autowired
    private FakeStore fakeStore;


    @DisplayName("전체 상품 목록 조회 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findAll_test() throws Exception {
        //given
        int page = 0;

        //stub
        List<Product> productList = fakeStore.getProductList().stream().skip(page*9).limit(9).collect(Collectors.toList());
        List<ProductResponse.FindAllDTO> userResponse = productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
        Mockito.when(productService.findAll(anyInt())).thenReturn(userResponse);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

    }

    @DisplayName("개별 상품 상세 조회 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findById_test() throws Exception {

        // given
        int id = 1;
        Product product = fakeStore.getProductList().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        List<Option> optionList = fakeStore.getOptionList().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());
        ProductResponse.FindByIdDTO userResponse = new ProductResponse.FindByIdDTO(product, optionList);

        //stub
        Mockito.when(productService.findById(anyInt())).thenReturn(userResponse);

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

    }


}