package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
//        FakeStore.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest  extends DummyEntity {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private FakeStore fakeStore;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private ObjectMapper om;

    private List<Product> productList;

    @BeforeEach
    public void setUp() throws Exception {
        productList = productDummyList();
    }
    @Test
    public void findAll_test() throws Exception {
        // Given

        List<Product> pagedProductList = productList.stream()
                .skip(0)
                .limit(10)
                .collect(Collectors.toList());
        
        List<ProductResponse.FindAllDTO> responseDTOs = pagedProductList.stream()
                .map(ProductResponse.FindAllDTO::new)
                .collect(Collectors.toList());

        when(fakeStore.getProductList()).thenReturn(productList);

        String responseBody = om.writeValueAsString(ApiUtils.success(responseDTOs));
        System.out.println("Request : "+responseBody);
        // When
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        System.out.println("Response : "+result.andReturn().getResponse().getContentAsString());
    }



    @Test
    public void findById_test() throws Exception {
    }



}
