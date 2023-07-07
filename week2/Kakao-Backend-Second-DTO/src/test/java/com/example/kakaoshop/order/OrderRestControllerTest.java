package com.example.kakaoshop.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    public void saveOrder_test() throws Exception {

        String Authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4MDI3Njd9.fKRctzvLSsiOz08ZgLIdWyCuMXxHYgYeI7EmiS2CcS2OPV18m9cUe7yTOhttfom24rsfsgLP8s1Wu23uKy4TnA";

        MockHttpServletRequestBuilder request = post("/orders/save")
                .header("Authorization", Authorization);
        // when
        ResultActions resultActions = mvc.perform(request);

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(jsonPath("$.success").value("true"));

        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(475800));

        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("2겹 식빵수세미 6매"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(3));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(26700));

        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value(5));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].optionName").value("JR310BT (무선 전용) - 레드"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].quantity").value(4));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].price").value(199600));

        resultActions.andExpect(jsonPath("$.response.products[1].items[1].id").value(6));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].optionName").value("JR310BT (무선 전용) - 그린"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].price").value(249500));

        resultActions.andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    @WithMockUser
    public void findById_test() throws Exception {

        String Authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4MDI3Njd9.fKRctzvLSsiOz08ZgLIdWyCuMXxHYgYeI7EmiS2CcS2OPV18m9cUe7yTOhttfom24rsfsgLP8s1Wu23uKy4TnA";

        MockHttpServletRequestBuilder request = get("/orders/1")
                .header("Authorization", Authorization);
        // when
        ResultActions resultActions = mvc.perform(request);

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(jsonPath("$.success").value("true"));

        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(354000));

        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));

        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(54500));

        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value(3));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].optionName").value("JR310BT (무선 전용) - 레드"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].price").value(249500));

        resultActions.andExpect(jsonPath("$.error").doesNotExist());
    }
}
