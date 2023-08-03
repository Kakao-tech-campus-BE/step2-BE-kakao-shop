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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
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

    // TODO: 실패 테스트

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("주문하기")
    public void save1_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(2));

        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(6));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(250000));

        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].id").value(5));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].quantity").value(1));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].price").value(10900));

        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));


//        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
//        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
//        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
//        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
//        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
//        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(5));
//        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
//        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(1));
//        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(10900));
//
//        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
//        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value(6));
//        resultActions.andExpect(jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
//        resultActions.andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5));
//        resultActions.andExpect(jsonPath("$.response.products[1].items[0].price").value(250000));
//        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("주문하기 후 장바구니가 초기화 되었는가")
    public void save2_test() throws Exception {
        // given
        mvc.perform(post("/orders/save"));

        // when
        ResultActions resultActions = mvc.perform(
                get("/carts")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.products").isEmpty());
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


    @WithUserDetails(value = "jminkkk@nate.com")
    @Test
    @DisplayName("주문하기 시 유저의 장바구니가 존재하지 않을 경우")
    public void save_fail_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("장바구니가 비어있습니다."));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("주문 내역 조회")
    public void findById_test() throws Exception {
        // given teardown.sql
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("조회할 주문이 존재하지 않을 경우")
    public void findById_fail_test() throws Exception {
        // given teardown.sql
        int id = 2;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 주문을 찾을 수 없습니다."));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
