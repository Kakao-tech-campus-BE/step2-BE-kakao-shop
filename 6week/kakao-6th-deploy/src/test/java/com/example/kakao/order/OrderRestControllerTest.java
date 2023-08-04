package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.example.kakao.cart.CartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    @DisplayName("결재 기능 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void saveOrder_test() throws Exception {
        // given teardown

        // when
        ResultActions result = mvc.perform(
                post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //verify
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(4));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value(10900));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].items[0].id").value(6));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].items[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].items[0].price").value(250000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(310900));

        result.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("주문 결과 확인")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void orderFindById_test() throws Exception {
        // given teardown

        // when
        ResultActions result = mvc.perform(
                get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //verify
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value(10900));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].items[0].id").value(3));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].items[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].items[0].price").value(250000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(310900));

        result.andDo(MockMvcResultHandlers.print()).andDo(document);

    }
}
