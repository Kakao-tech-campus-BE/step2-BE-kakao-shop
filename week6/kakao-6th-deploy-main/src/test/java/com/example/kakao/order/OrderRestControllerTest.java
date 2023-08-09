package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class OrderRestControllerTest extends MyRestDoc {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @DisplayName("주문 생성 성공")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void save_success_test() throws Exception {
        //given

        //when
        ResultActions result = mvc.perform(
                post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(jsonPath("$.success").value(true));
        result.andExpect(jsonPath("$.response.id").value(2));
        result.andExpect(jsonPath("$.response.totalPrice").value(310900));
        result.andExpect(jsonPath("$.response.products[0].name").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
        result.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("주문 생성 실패 - 인증 실패")
    //@WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void save_fail_test1() throws Exception {
        //given

        //when
        ResultActions result = mvc.perform(
                post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isUnauthorized());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"));
        result.andExpect(jsonPath("$.error.status").value(401));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("주문 생성 실패 - 빈 장바구니")
    @WithUserDetails(value = "gihae0805@nate.com")
    @Test
    void save_fail_test2() throws Exception {
        //given

        //when
        ResultActions result = mvc.perform(
                post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("사용자의 장바구니가 비어있습니다."));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("주문 결과 조회 성공")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void findById_success_test() throws Exception {
        //given
        int orderId = 1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/{id}", orderId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(jsonPath("$.success").value(true));
        result.andExpect(jsonPath("$.response.id").value(1));
        result.andExpect(jsonPath("$.response.totalPrice").value(310900));
        result.andExpect(jsonPath("$.response.products[0].name").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        result.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("주문 결과 조회 실패 - 주문 없음")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void findById_fail_test1() throws Exception {
        //given
        int orderId = 100;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/{id}", orderId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isNotFound());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("주문을 찾을 수 없습니다. : 100"));
        result.andExpect(jsonPath("$.error.status").value(404));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("주문 결과 조회 실패 - 권한 없음")
    @WithUserDetails(value = "gihae0805@nate.com")
    @Test
    void findById_fail_test2() throws Exception {
        //given
        int orderId = 1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/{id}", orderId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isForbidden());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("주문 조회를 할 수 있는 권한이 없습니다."));
        result.andExpect(jsonPath("$.error.status").value(403));
        result.andDo(print()).andDo(document);
    }
}