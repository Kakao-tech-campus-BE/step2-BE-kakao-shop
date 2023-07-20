package com.example.kakao.order;


import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.CartResponse;
import com.example.kakao.cart.CartService;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerMockTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private FakeStore fakeStore;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test//save() 성공 테스트
    public void save_test() throws Exception{
        //given
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();

        //stub
        Mockito.when(orderService.save()).thenReturn(new OrderResponse.FindByIdDTO(order, itemList));

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test//findById() 성공 테스트
    public void findById_test() throws Exception{
        /*
        @SpringBootTest로 Repository를 빈으로 등록해서 id를 가져오는 대신
        MockMvc로 가볍게 테스트하기 위해 Mock 데이터를 이용해서 id 값을 가져왔습니다.
         */
        //given
        int id = fakeStore.getOrderList().get(0).getId();

        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();

        //stub
        Mockito.when(orderService.findById(id)).thenReturn(new OrderResponse.FindByIdDTO(order, itemList));

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+id)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test//findById() 성공 테스트
    public void findById_wrong_id_test() throws Exception{
        //given
        final int id = 1000; // 없는 id 값

        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();

        //stub
        Mockito.when(orderService.findById(id)).thenThrow(new Exception404("해당 상품을 찾을 수 없습니다"));

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+id)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpectAll(
                status().is4xxClientError(), // 404 오류 확인
                MockMvcResultMatchers.jsonPath("$.success").value("false")
        );
    }
}
