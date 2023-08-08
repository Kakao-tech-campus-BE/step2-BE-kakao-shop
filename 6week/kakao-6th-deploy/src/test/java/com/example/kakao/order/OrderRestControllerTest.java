package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @DisplayName("주문하기 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void save_test() throws Exception {
        // given teardown.sql에 들어 있는 Cart가 그대로 저장됨
        // cartId: [1, 2, 3]
        // optionId : [1, 2, 16]

        // teardown.sql에 orderId: 1, itemId: [1, 2, 3]이  이미 들어 있음
        // 새로 save되는 주문은 orderId: 2, itemId: [4, 5, 6]이 됨


        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));

        resultActions.andExpect(jsonPath("$.response.id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));

        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(10900));

        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value(6));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].price").value(250000));

        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("주문하기 실패 테스트 1 - 인증 필요")
    @Test
    public void save_fail_test_unauthorized() throws Exception {
        // given teardown.sql에 들어 있는 Cart가 그대로 저장됨

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"));
        resultActions.andExpect(status().is(401));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("주문하기 실패 테스트 2 - 장바구니 비어있음")
    @WithUserDetails(value = "ssarorange@nate.com")
    @Test       // user2 : ssarorange
    public void save_fail_test_cart_empty() throws Exception {
        // given teardown.sql에 들어 있는 Cart가 그대로 저장됨

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("장바구니에서 주문할 상품이 없습니다."));
        resultActions.andExpect(status().is(404));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


    @DisplayName("주문 조회 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findById_test() throws Exception {
        // given teardown.sql에 Order, Item이 들어있음
        // orderId : 1
        // itemId : [1, 2, 3]

        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/{id}", id)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));

        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));

        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(10900));

        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value(3));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].price").value(250000));

        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("주문 조회 실패 테스트 1 - 인증 필요")
    @Test
    public void findById_fail_test_unauthorized() throws Exception {
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/{id}", id)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"));
        resultActions.andExpect(status().is(401));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


    @DisplayName("주문 조회 실패 테스트 2 - 해당 주문 없음")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findById_fail_test_order_not_exist() throws Exception {
        // given teardown.sql에 Order, Item이 들어있음
        // orderId : 1
        // itemId : [1, 2, 3]

        int id = 4;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/{id}", id)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 주문을 찾을 수 없습니다."));
        resultActions.andExpect(status().is(404));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}