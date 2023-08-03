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

    @DisplayName("주문하기 성공")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void order_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value("2"));
        resultActions.andExpect(jsonPath("$.response.products[0].id").value("1"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value("1"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @DisplayName("주문하기 실패 - 장바구니가 비어있는 경우")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void order_fail_empty_cart_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        ResultActions resultActions2 = mvc.perform(
                post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        String responseBody2 = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody2);

        // verify
        resultActions2.andExpect(jsonPath("$.success").value("false"));
        resultActions2.andExpect(jsonPath("$.error.message").value("장바구니가 비어있습니다."));
        resultActions2.andExpect(jsonPath("$.error.status").value("400"));
        resultActions2.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @DisplayName("주문 조회하기 성공")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findOrderById_test() throws Exception {
        // given
        int orderId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + orderId)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value("1"));
        resultActions.andExpect(jsonPath("$.response.products[0].id").value("1"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value("1"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @DisplayName("주문 조회하기 실패 - 잘못된 cartid")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findOrderById_fail_invalid_cartId_test() throws Exception {
        // given
        int orderId = 1234;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + orderId)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 주문을 찾을 수 없습니다."));
        resultActions.andExpect(jsonPath("$.error.status").value("404"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
