package com.example.kakao.order;

import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.order.item.Item;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfig.class})
@WebMvcTest(controllers = {OrderRestController.class})
class OrderRestControllerTest extends DummyEntity {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FakeStore fakeStore;

    @DisplayName("주문 저장")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    void save_test() throws Exception {

        // given
        List<Order> mockOrders = orderList;
        List<Item> items = itemList;

        // mock
        when(fakeStore.getOrderList()).thenReturn(mockOrders);
        when(fakeStore.getItemList()).thenReturn(items);

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
                MockMvcResultMatchers.jsonPath("$.response.id").value(mockOrders.get(0).getId()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(items.get(0).getOption().getProduct().getId()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value(items.get(0).getOption().getProduct().getProductName()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(items.get(0).getId()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value(items.get(0).getOption().getOptionName()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(items.get(0).getQuantity()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(items.get(0).getPrice()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(items.get(1).getId()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value(items.get(1).getOption().getOptionName()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value(items.get(1).getQuantity()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value(items.get(1).getPrice()),
                MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(
                        items.stream().map(Item::getPrice).mapToInt(Integer::intValue).sum()
                ),
                MockMvcResultMatchers.jsonPath("$.error").isEmpty()
        );
     }

    @DisplayName("주문 저장 실패: 인증되지 않은 사용자")
    @Test
    void save_fail_test() throws Exception {

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @DisplayName("주문 조회")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    void findById_test() throws Exception {

        // given
        int orderId = 1;
        List<Order> orders = orderList;
        List<Item> items = itemList;

        // mock
        when(fakeStore.getOrderList()).thenReturn(orders);
        when(fakeStore.getItemList()).thenReturn(items);

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
                MockMvcResultMatchers.jsonPath("$.response.id").value(orderId),
                MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1),
                MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value(items.get(0).getOption().getProduct().getProductName()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(items.get(0).getId()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value(items.get(0).getOption().getOptionName()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(items.get(0).getQuantity()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(items.get(0).getPrice()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(items.get(1).getId()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value(items.get(1).getOption().getOptionName()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value(items.get(1).getQuantity()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value(items.get(1).getPrice()),
                MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(
                        items.stream().map(Item::getPrice).mapToInt(Integer::intValue).sum()),
                MockMvcResultMatchers.jsonPath("$.error").isEmpty()
        );
    }
}