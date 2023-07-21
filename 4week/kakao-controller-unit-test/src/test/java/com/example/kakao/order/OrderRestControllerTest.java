package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
        FakeStore.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest extends DummyEntity {

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FakeStore fakeStore;

    @Autowired
    private ObjectMapper om;

    // 주문 결과 확인
    @WithMockUser(username="ssar@nate.com", roles = "user")
    @Test
    public void checkOrder() throws Exception {
        // given


        // stub
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/1")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // when

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value("1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value("1"));

    }

    // 결제하기 (주문 인서트)
    @WithMockUser(username="ssar@nate.com", roles = "USER")
    @Test
    public void saveOrder() throws Exception {
        // given
        List<Order> orderList = fakeStore.getOrderList();
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO requestDTO = new OrderResponse.FindByIdDTO(orderList.get(0),itemList);
        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("requestBody: " + requestBody);

        // stub



        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();        System.out.println("테스트3: " + result.andReturn().getResponse().getOutputStream());
        System.out.println("테스트 : " + responseBody);
        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value("1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value("1"));

    }

}
