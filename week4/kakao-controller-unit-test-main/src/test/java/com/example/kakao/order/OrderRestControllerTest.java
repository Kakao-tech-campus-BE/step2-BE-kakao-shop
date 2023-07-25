package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
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

import java.util.List;

@Import({
        FakeStore.class,
        GlobalExceptionHandler.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {

    @MockBean
    OrderService orderService;
    @MockBean
    ErrorLogJPARepository errorLogJPARepository;
    @Autowired
    FakeStore fakeStore;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("결재 테스트")
    @WithMockUser
    public void order_save_test() throws Exception {
        //given
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);

        String requestBody = om.writeValueAsString(responseDTO);
        System.out.println("테스트 : "+requestBody);

        // stub
        BDDMockito.willDoNothing().given(orderService).saveOrder(responseDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("주문 내역 조회 테스트")
    @WithMockUser
    public void order_find_by_id_test() throws Exception{
        // given
        int id = 1;

        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);

        String requestBody = om.writeValueAsString(responseDTO);
        System.out.println("테스트 : "+requestBody);

        // stub
        Mockito.when(orderService.findOrderById(id)).thenReturn(responseDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));

    }

    @Test
    @DisplayName("주문 내역 조회 실패 테스트")
    @WithMockUser
    public void order_find_by_id_fail_test() throws Exception{

        // given
        int id = 10;

        // stub
        Mockito.when(orderService.findOrderById(id)).thenThrow(new Exception404("해당 주문 내역을 찾을 수 없습니다."));

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        result.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }
}
