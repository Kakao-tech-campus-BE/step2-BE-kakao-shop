package com.example.kakao._core.order;

import com.example.kakao._core.MyRestDoc;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao.cart.Cart;
import com.example.kakao.order.OrderResponse;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;


    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void save_test() throws Exception {

        //given teardown.sql

        // when
        String requestBody = om.writeValueAsString("");

        ResultActions resultActions = mvc.perform(post("/orders/save")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON)
        );

        //console
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.response.id").value(2))

                .andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(jsonPath("$.response.products[0].items[0].id").value(1))
                .andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
                .andExpect(jsonPath("$.response.products[0].items[0].price").value(50000))
                .andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5))

                .andExpect(jsonPath("$.response.products[0].items[1].id").value(2))
                .andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"))
                .andExpect(jsonPath("$.response.products[0].items[1].price").value(10900))
                .andExpect(jsonPath("$.response.products[0].items[1].quantity").value(1))

                .andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"))
                .andExpect(jsonPath("$.response.products[1].items[0].id").value(3))
                .andExpect(jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"))
                .andExpect(jsonPath("$.response.products[1].items[0].price").value(250000))
                .andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5))

                .andExpect(jsonPath("$.response.totalPrice").value(310900));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @WithUserDetails(value = "ssarmango2@nate.com")
    @Test
    public void save_noOrder_test() throws Exception {

        String requestBody = om.writeValueAsString("");


        ResultActions resultActions = mvc.perform(post("/orders/save")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON)
        );

        //console
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);


        //then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("EMPTY_CART:[]"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findById_test() throws Exception {

        //given teardown.sql
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(get("/orders/" + id));

        //console
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.response.id").value(1))

                .andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(jsonPath("$.response.products[0].items[0].id").value(1))
                .andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
                .andExpect(jsonPath("$.response.products[0].items[0].price").value(50000))
                .andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5))

                .andExpect(jsonPath("$.response.products[0].items[1].id").value(2))
                .andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"))
                .andExpect(jsonPath("$.response.products[0].items[1].price").value(10900))
                .andExpect(jsonPath("$.response.products[0].items[1].quantity").value(1))

                .andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"))
                .andExpect(jsonPath("$.response.products[1].items[0].id").value(3))
                .andExpect(jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"))
                .andExpect(jsonPath("$.response.products[1].items[0].price").value(250000))
                .andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5))

                .andExpect(jsonPath("$.response.totalPrice").value(310900));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findById_inValidId_test() throws Exception {

        //given teardown.sql
        int id = -1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        //console
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);


        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("INVALID_ORDER_ID:"+id));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango2@nate.com")
    @Test
    public void findById_orderNotFound_test() throws Exception {

        //given teardown.sql
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        //console
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);


        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("ORDER_NOT_FOUND:null"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


}
