package com.example.kakaoshop.cart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    // 전체 상품 목록 조회
    public void findAll_test() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(
                get("/order")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(354000));
        resultActions.andExpect(jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.price").value(10000));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].price").value(50000));

        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.price").value(10900));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].price").value(54500);

        resultActions.andExpect(jsonPath("$.response.products[0].id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("삼성전자 JBL JR310 외 어린이용/ 성인용 헤드셋 3종!"));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.id").value(3));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.optionName").value("JR310BT (무선 전용) - 레드"));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.price").value(49900));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].price").value(249500));

    }
}
