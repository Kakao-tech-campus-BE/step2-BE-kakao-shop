package com.example.kakaoshop.cart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest {
    @Autowired
    private MockMvc mvc;

    //6. 장바구니 담기 (POST)
    @Test
    @WithMockUser
    public void addToCart_test() throws Exception{
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODk1NTUzMDV9.CEMCn2i8IeebgoLpVPCJ615wOqZ1xAyKel7b8mEpjkIq2l0ryEUfapABKF6u2WqTVQeq2cnUO9ODXEQSXnxmDw";
//        어떤 방식으로 테스트 하는것이 좋은지?
        //String 방식
        String requestBody = "[{\"optionId\":1, \"quantity\":5},{\"optionId\":2, \"quantity\":5}]"; // JSON array 형태로 바꿔줌. (안바꿔주면 에러발생)
        //Object Mapper
//        CartItemReqDTO item1 = new CartItemReqDTO(1, 5);
//        CartItemReqDTO item2 = new CartItemReqDTO(2, 5);
//        List<CartItemReqDTO> items = Arrays.asList(item1, item2);
//        ObjectMapper mapper = new ObjectMapper();
//        String requestBody = mapper.writeValueAsString(items);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value(true));
    }

    //7. 장바구니 조회 (GET)
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

    //8. 주문하기-장바구니 수정(POST)
    @Test
    @WithMockUser
    public void updateCart_test() throws Exception{

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODk1NTUzMDV9.CEMCn2i8IeebgoLpVPCJ615wOqZ1xAyKel7b8mEpjkIq2l0ryEUfapABKF6u2WqTVQeq2cnUO9ODXEQSXnxmDw";
        String requestBody = "[{\"cartId\":4, \"quantity\":10},{\"cartId\":5, \"quantity\":10}]";  // JSON array 형태로 바꿔줌.

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")  // 엔드포인트 변경
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response.carts[0].cartId").value(4));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionId").value(1));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.carts[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.carts[0].price").value(100000));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(209000));


    }
}
