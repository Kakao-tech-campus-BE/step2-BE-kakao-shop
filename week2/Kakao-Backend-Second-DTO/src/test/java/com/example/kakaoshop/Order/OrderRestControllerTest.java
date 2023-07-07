package com.example.kakaoshop.Order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    public void save_test() throws Exception{

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItemList[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItemList[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItemList[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItemList[0].price").value(100000));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItemList[1].id").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItemList[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItemList[1].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.products[0].orderItemList[1].price").value(109000));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(209000));
    }


    @Test
    @WithMockUser
    public void findById_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        if(id == 1){
            // verify
            resultActions.andExpect( jsonPath("$.success").value("true"));
            resultActions.andExpect( jsonPath("$.response.id").value(1));
            resultActions.andExpect( jsonPath("$.response.orderedProducts[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
            resultActions.andExpect( jsonPath("$.response.orderedProducts[0].orderedItems[0].id").value(4));
            resultActions.andExpect( jsonPath("$.response.orderedProducts[0].orderedItems[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스 에디션 4종"));
            resultActions.andExpect( jsonPath("$.response.orderedProducts[0].orderedItems[0].quantity").value(10));
            resultActions.andExpect( jsonPath("$.response.orderedProducts[0].orderedItems[0].price").value(100000));
            resultActions.andExpect( jsonPath("$.response.orderedProducts[0].orderedItems[1].id").value(5));
            resultActions.andExpect( jsonPath("$.response.orderedProducts[0].orderedItems[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
            resultActions.andExpect( jsonPath("$.response.orderedProducts[0].orderedItems[1].quantity").value(10));
            resultActions.andExpect( jsonPath("$.response.orderedProducts[0].orderedItems[1].price").value(109000));
            resultActions.andExpect( jsonPath("$.response.totalPrice").value(209000));
        }else{
            resultActions.andExpect( jsonPath("$.success").value("false"));
            resultActions.andExpect( jsonPath("$.response").value(null));
            resultActions.andExpect( jsonPath("$.error").value("해당 주문정보를 찾을 수 없습니다 : " + id));
        }


    }
}
