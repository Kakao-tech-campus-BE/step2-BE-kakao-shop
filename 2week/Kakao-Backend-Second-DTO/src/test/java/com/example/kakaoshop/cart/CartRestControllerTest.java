package com.example.kakaoshop.cart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import com.example.kakaoshop.cart.request.CartRequestDTO;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    // 전체 상품 목록 조회
    public void findAll_test() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(
                get("/carts")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(104500));
        resultActions.andExpect(jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.price").value(10000));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].price").value(50000));

    }
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @WithMockUser
    // 카트에 상품 추가
    public void addToCart_test() throws Exception {
        // Given
    	List<CartRequestDTO.AddDTO> addToCartRequests = Arrays.asList(
                new CartRequestDTO.AddDTO(1L, 5),
                new CartRequestDTO.AddDTO(2L, 5)
        );


        String requestBody = objectMapper.writeValueAsString(addToCartRequests);

        // When
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // Verify
        resultActions.andExpect(jsonPath("$.success").value(true));
        
    }

    
    @Test
    @WithMockUser
    public void updateCart_test() throws Exception {
    	CartRequestDTO.UpdateDTO[] updateRequests = new CartRequestDTO.UpdateDTO[]{
                new CartRequestDTO.UpdateDTO(4L, 10),
                new CartRequestDTO.UpdateDTO(5L, 10)
        };

        String content = objectMapper.writeValueAsString(updateRequests);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Test Response : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(209000));
        resultActions.andExpect(jsonPath("$.response.carts[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.carts[0].option.id").value(1));
        resultActions.andExpect(jsonPath("$.response.carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.carts[0].option.price").value(10000));
        resultActions.andExpect(jsonPath("$.response.carts[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.carts[0].price").value(100000));

        resultActions.andExpect(jsonPath("$.response.carts[1].id").value(5));
        resultActions.andExpect(jsonPath("$.response.carts[1].option.id").value(2));
        resultActions.andExpect(jsonPath("$.response.carts[1].option.optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.carts[1].option.price").value(10900));
        resultActions.andExpect(jsonPath("$.response.carts[1].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.carts[1].price").value(109000));
    }
    
}
