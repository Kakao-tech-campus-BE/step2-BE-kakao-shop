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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


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

    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("주문 인서트 통합 테스트")
    @Test
    public void save_test() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value("2"));

        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value("4")); //3번까지는 item이 이미 저장되어 있으므로 4가 맞다
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value("5"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value("50000"));

        resultActions.andExpect(jsonPath("$.response.totalPrice").value("310900"));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarssar@nate.com")
    @DisplayName("주문 인서트 시 장바구니가 비어있는 경우 예외 발생 통합 테스트")
    @Test
    public void save_emptyCart_fail_test() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").isEmpty());
        resultActions.andExpect(jsonPath("$.error.message").value("주문할 상품이 없습니다. 장바구니에 상품을 먼저 담아주세요."));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("주문 결과 확인 통합 테스트")
    @Test
    public void findByOrderId_test() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value("1"));

        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value("1"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value("5"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value("50000"));

        resultActions.andExpect(jsonPath("$.response.totalPrice").value("310900"));
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("존재하지 않는 주문번호 조회 시 예외 발생 통합 테스트")
    @Test
    public void findByOrderId_orderIdNotFound_fail_test() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음
        int id = 5;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").isEmpty());
        resultActions.andExpect(jsonPath("$.error.message").value("존재하지 않는 주문번호입니다. :5"));
        resultActions.andExpect(jsonPath("$.error.status").value("404"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarssar@nate.com")
    @DisplayName("다른 사용자의 주문내역에 접근 시 예외 발생 통합 테스트")
    @Test
    public void findByOrderId_notAuthorizedAccessToOrderHistory_fail_test() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").isEmpty());
        resultActions.andExpect(jsonPath("$.error.message").value("접근 권한이 없습니다."));
        resultActions.andExpect(jsonPath("$.error.status").value("400"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}