package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.item.OrderItemRespDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.json.JSONObject;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest {
    private OrderItemRespDTO orderItemRespDTO;

    @Autowired
    private MockMvc mvc;
    private String responseBody;

    @Test
    @WithMockUser
    // orders/save
    public void Add_test() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(2));
        resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[0].price").value(100000));
        resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[1].id").value(5));
        resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[1].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[1].price").value(109000));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(209000));
        resultActions.andExpect(jsonPath("$.error").value(nullValue()));
    }

    // orders/1
    @Test
    @WithMockUser
    public void Check_test() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/1")
        );


        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // JSON 파싱
        JSONObject jsonObject = new JSONObject(responseBody);

        // 특정 값을 추출
        String specificValue = jsonObject.getString("response");

        // 만약 아직 구매를 하지 않은 경우
        if (specificValue == null)
        {
            resultActions.andExpect(jsonPath("$.success").value("true"));
            resultActions.andExpect(jsonPath("$.response").value(nullValue()));
            resultActions.andExpect(jsonPath("$.error").value(nullValue()));
        }
        else
        {
            // verify ( 구매를 한 경우 )
            resultActions.andExpect(jsonPath("$.success").value("true"));
            resultActions.andExpect(jsonPath("$.response.id").value(2));
            resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
            resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[0].id").value(4));
            resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
            resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[0].quantity").value(10));
            resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[0].price").value(100000));
            resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[1].id").value(5));
            resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
            resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[1].quantity").value(10));
            resultActions.andExpect(jsonPath("$.response.orderProjectDTO[0].orderItemDTO[1].price").value(109000));
            resultActions.andExpect(jsonPath("$.response.totalPrice").value(209000));
            resultActions.andExpect(jsonPath("$.error").value(nullValue()));
        }

    }

}
