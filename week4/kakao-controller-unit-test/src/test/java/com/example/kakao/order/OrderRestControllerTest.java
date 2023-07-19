package com.example.kakao.order;

import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
class OrderRestControllerTest extends DummyEntity {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;


    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    void save_test() throws Exception {

        // given

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andDo(print());

        // then
        result.andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.jsonPath("$.success").value("true"),
                MockMvcResultMatchers.jsonPath("$.response.id").value(1),
                MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1),
                MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(2),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value(5),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value(54500),
                MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(104500),
                MockMvcResultMatchers.jsonPath("$.error").isEmpty()
        );
     }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    void findById_test() throws Exception {

        // given
        int orderId = 1;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andDo(print());

        // then
        result.andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.jsonPath("$.success").value("true"),
                MockMvcResultMatchers.jsonPath("$.response.id").value(1),
                MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1),
                MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(2),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value(5),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value(54500),
                MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(104500),
                MockMvcResultMatchers.jsonPath("$.error").isEmpty()
        );
    }
}