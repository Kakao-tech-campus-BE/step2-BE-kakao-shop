package com.example.kakaoshop.cart;

import com.example.kakaoshop.cart.response.CartReqRespDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    @DisplayName("장바구니 조회")
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

    @Test
    @WithMockUser
    @DisplayName("장바구니 담기")
    public void addCart_test() throws Exception {
        //given
        List<CartReqRespDTO.CartDTO> cartDTOList = new ArrayList<>();
        CartReqRespDTO.CartDTO cartDTO1 = new CartReqRespDTO.CartDTO(1,5);
        CartReqRespDTO.CartDTO cartDTO2 = new CartReqRespDTO.CartDTO(2,5);
        cartDTOList.add(cartDTO1);
        cartDTOList.add(cartDTO2);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(cartDTOList);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestData)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response").doesNotExist()); //null인지 확인
        resultActions.andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    @WithMockUser
    @DisplayName("장바구니 수정")
    public void updateCart_test() throws Exception {
        //given
        List<CartReqRespDTO.CartUpdateRequestDTO> cartList = new ArrayList<>();
        CartReqRespDTO.CartUpdateRequestDTO cartDTO1 = new CartReqRespDTO.CartUpdateRequestDTO(4,10);
        CartReqRespDTO.CartUpdateRequestDTO cartDTO2 = new CartReqRespDTO.CartUpdateRequestDTO(5,10);
        cartList.add(cartDTO1);
        cartList.add(cartDTO2);
        //JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(cartList);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestData)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(209000));

        resultActions.andExpect(jsonPath("$.response.carts[0].cartId").value(4));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionId").value(1));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.carts[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.carts[0].price").value(100000));

        resultActions.andExpect(jsonPath("$.response.carts[1].cartId").value(5));
        resultActions.andExpect(jsonPath("$.response.carts[1].optionId").value(2));
        resultActions.andExpect(jsonPath("$.response.carts[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.carts[1].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.carts[1].price").value(109000));

        resultActions.andExpect(jsonPath("$.error").doesNotExist());
    }
}
