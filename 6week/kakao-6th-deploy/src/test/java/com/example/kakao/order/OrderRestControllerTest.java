package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.example.kakao._core.util.CustomRequestPostProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @DisplayName("주문 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void orderAll_test() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response.id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value(3));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].price").value(250000));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @DisplayName("주문 에러 테스트")
    @WithUserDetails(value = "nomango@nate.com")
    @Test
    public void orderAll_error_test() throws Exception {
        // given -> cart에 담긴 물건이 없음

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(new CustomRequestPostProcessor())
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("장바구니가 비어있습니다."));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @DisplayName("주문 찾기 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void order_findById_test() throws Exception {
        // given
        Integer orderId = 1;
        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + orderId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value(3));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].price").value(250000));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("주문 찾기 에러 테스트(잘못된 id)")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void order_findById_error_test() throws Exception {
        // given
        Integer orderId = 2;
        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + orderId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(new CustomRequestPostProcessor())
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 주문이 유효하지 않거나 접근권한이 없습니다."));
        resultActions.andExpect(jsonPath("$.error.status").value(401));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @DisplayName("주문 찾기 에러 테스트(잘못된 접근)")
    @WithUserDetails(value = "nomango@nate.com")
    @Test
    public void order_findById_wrongUser_error_test() throws Exception {
        // given
        Integer orderId = 1;
        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + orderId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(new CustomRequestPostProcessor())
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 주문이 유효하지 않거나 접근권한이 없습니다."));
        resultActions.andExpect(jsonPath("$.error.status").value(401));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }
}