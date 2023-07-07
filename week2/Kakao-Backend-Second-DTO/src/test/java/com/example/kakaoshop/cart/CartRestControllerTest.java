package com.example.kakaoshop.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    // 전체 상품 목록 조회
    public void findAll_test() throws Exception {

        String Authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4MDI3Njd9.fKRctzvLSsiOz08ZgLIdWyCuMXxHYgYeI7EmiS2CcS2OPV18m9cUe7yTOhttfom24rsfsgLP8s1Wu23uKy4TnA";

        MockHttpServletRequestBuilder request = get("/carts")
                .header("Authorization", Authorization);
        // when
        ResultActions resultActions = mvc.perform(request);

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(jsonPath("$.success").value("true"));

        resultActions.andExpect(jsonPath("$.response.totalPrice").value(475800));
        resultActions.andExpect(jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.id").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.optionName").value("2겹 식빵수세미 6매"));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.price").value(8900));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].quantity").value(3));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].price").value(26700));

        resultActions.andExpect(jsonPath("$.response.products[1].id").value(3));
        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!"));

        resultActions.andExpect(jsonPath("$.response.products[1].carts[0].id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[1].carts[0].option.id").value(10));
        resultActions.andExpect(jsonPath("$.response.products[1].carts[0].option.optionName").value("JR310BT (무선 전용) - 레드"));
        resultActions.andExpect(jsonPath("$.response.products[1].carts[0].option.price").value(49900));
        resultActions.andExpect(jsonPath("$.response.products[1].carts[0].quantity").value(4));
        resultActions.andExpect(jsonPath("$.response.products[1].carts[0].price").value(199600));

        resultActions.andExpect(jsonPath("$.response.products[1].carts[1].id").value(3));
        resultActions.andExpect(jsonPath("$.response.products[1].carts[1].option.id").value(11));
        resultActions.andExpect(jsonPath("$.response.products[1].carts[1].option.optionName").value("JR310BT (무선 전용) - 그린"));
        resultActions.andExpect(jsonPath("$.response.products[1].carts[1].option.price").value(49900));
        resultActions.andExpect(jsonPath("$.response.products[1].carts[1].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[1].carts[1].price").value(249500));

        resultActions.andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    @WithMockUser
    public void cartUpdate_test() throws Exception {

        // when
        String Authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4MDI3Njd9.fKRctzvLSsiOz08ZgLIdWyCuMXxHYgYeI7EmiS2CcS2OPV18m9cUe7yTOhttfom24rsfsgLP8s1Wu23uKy4TnA";

        List<Map<String, Object>> cartItems = new ArrayList<>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("cartId", 1);
        item1.put("quantity", 3);
        Map<String, Object> item2 = new HashMap<>();
        item2.put("cartId", 2);
        item2.put("quantity", 5);
        cartItems.add(item1);
        cartItems.add(item2);

        // ObjectMapper를 사용하여 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(cartItems);

        MockHttpServletRequestBuilder request = put("/carts/update")
                .header("Authorization", Authorization)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);
        // when
        ResultActions resultActions = mvc.perform(request);

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(jsonPath("$.success").value("true"));

        resultActions.andExpect(jsonPath("$.response.totalPrice").value(525700));
        resultActions.andExpect(jsonPath("$.response.carts[0].cartId").value(1));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionId").value(5));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionName").value("2겹 식빵수세미 6매"));
        resultActions.andExpect(jsonPath("$.response.carts[0].quantity").value(3));
        resultActions.andExpect(jsonPath("$.response.carts[0].price").value(26700));

        resultActions.andExpect(jsonPath("$.response.carts[1].cartId").value(2));
        resultActions.andExpect(jsonPath("$.response.carts[1].optionId").value(10));
        resultActions.andExpect(jsonPath("$.response.carts[1].optionName").value("JR310BT (무선 전용) - 레드"));
        resultActions.andExpect(jsonPath("$.response.carts[1].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.carts[1].price").value(249500));

        resultActions.andExpect(jsonPath("$.response.carts[2].cartId").value(3));
        resultActions.andExpect(jsonPath("$.response.carts[2].optionId").value(11));
        resultActions.andExpect(jsonPath("$.response.carts[2].optionName").value("JR310BT (무선 전용) - 그린"));
        resultActions.andExpect(jsonPath("$.response.carts[2].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.carts[2].price").value(249500));

        resultActions.andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    @WithMockUser
    public void cartAdd_test() throws Exception {
        // when
        String Authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4MDI3Njd9.fKRctzvLSsiOz08ZgLIdWyCuMXxHYgYeI7EmiS2CcS2OPV18m9cUe7yTOhttfom24rsfsgLP8s1Wu23uKy4TnA";

        List<Map<String, Object>> cartItems = new ArrayList<>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("optionId", 1);
        item1.put("quantity", 5);
        Map<String, Object> item2 = new HashMap<>();
        item2.put("optionId", 2);
        item2.put("quantity", 5);
        cartItems.add(item1);
        cartItems.add(item2);

        // ObjectMapper를 사용하여 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(cartItems);

        MockHttpServletRequestBuilder request = post("/carts/add")
                .header("Authorization", Authorization)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);
        // when
        ResultActions resultActions = mvc.perform(request);

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response").doesNotExist());
        resultActions.andExpect(jsonPath("$.error").doesNotExist());
    }


}
