package com.example.kakaoshop.order;

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
    MockMvc mvc;

    @Test
    @WithMockUser
    public void findById_test() throws Exception{
        int id = 1;

        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.printf("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.order_id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItems[0].cart_id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItems[0].option_name").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItems[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItems[0].price").value(100000));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItems[1].cart_id").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItems[1].option_name").value("02. 슬라이딩 지퍼백 플라워에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItems[1].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItems[1].price").value(109000));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(209000));
    }
}
