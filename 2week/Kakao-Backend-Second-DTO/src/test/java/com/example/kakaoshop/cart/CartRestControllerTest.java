package com.example.kakaoshop.cart;

import com.example.kakaoshop.cart.response.CartItemDTO;
import com.example.kakaoshop.cart.response.CartUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    // carts/add 조회
    public void findAdd_test() throws Exception {
        List<CartItemDTO.AddCartItemDTO> addCartItemDTO = new ArrayList<>();
        // 카트 아이템 리스트에 담기
        CartItemDTO.AddCartItemDTO cartAddDTO1 = CartItemDTO.AddCartItemDTO.builder()
                .optionId(1L)
                .quantity(5)
                .build();
        addCartItemDTO.add(cartAddDTO1);
        // 카트 아이템 리스트에 담기
        CartItemDTO.AddCartItemDTO cartAddDTO2 = CartItemDTO.AddCartItemDTO.builder()
                .optionId(2L)
                .quantity(5)
                .build();
        addCartItemDTO.add(cartAddDTO2);

        String requestBody = objectMapper.writeValueAsString(addCartItemDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response").value(nullValue()));
        resultActions.andExpect(jsonPath("$.error").value(nullValue()));

    }

    // /carts/update 조회
    @Test
    @WithMockUser
    public void Update_test() throws Exception
    {
        List<CartUpdateDTO.UpdateDTO> cartUpdateDTO = new ArrayList<>();
        // 카트 아이템 리스트에 담기
        CartUpdateDTO.UpdateDTO updateCartDTO1 = CartUpdateDTO.UpdateDTO.builder()
                .cartId(1L)
                .quantity(5)
                .build();
        cartUpdateDTO.add(updateCartDTO1);
        // 카트 아이템 리스트에 담기
        CartUpdateDTO.UpdateDTO updateCartDTO2 = CartUpdateDTO.UpdateDTO.builder()
                .cartId(2L)
                .quantity(5)
                .build();
        cartUpdateDTO.add(updateCartDTO2);

        String requestBody = objectMapper.writeValueAsString(cartUpdateDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.cartUpdateDTO[0].cartId").value(4));
        resultActions.andExpect(jsonPath("$.response.cartUpdateDTO[0].optionId").value(1));
        resultActions.andExpect(jsonPath("$.response.cartUpdateDTO[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.cartUpdateDTO[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.cartUpdateDTO[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.cartUpdateDTO[1].cartId").value(5));
        resultActions.andExpect(jsonPath("$.response.cartUpdateDTO[1].optionId").value(2));
        resultActions.andExpect(jsonPath("$.response.cartUpdateDTO[1].optionName").value("02. 슬라이딩 지퍼백 크리스마스에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.cartUpdateDTO[1].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.cartUpdateDTO[1].price").value(54500));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(209000));
        resultActions.andExpect(jsonPath("$.error").value(nullValue()));
    }

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
}
