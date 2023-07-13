package com.example.kakaoshop.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class OrderRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    void save() throws Exception {

        //when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.info("Order Test save(): " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(209000));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(100000));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(109000));
    }

    @Test
    @WithMockUser
    void findById() throws Exception {

        //given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.info("Order Test findById(): " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(209000));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(100000));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(109000));
    }

    @Test
    @WithMockUser
    void findById_fail() throws Exception {

        //given
        int id = 2;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.info("Order Test findById_fail(): " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value((Object)null));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 주문을 찾을 수 없습니다."));
    }
}