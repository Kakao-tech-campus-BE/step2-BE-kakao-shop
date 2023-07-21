package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@Import({
        GlobalExceptionHandler.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {
    @MockBean
    private OrderService orderService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    private final MockMvc mvc;

    private final ObjectMapper om;

    @Autowired
    public OrderRestControllerTest(MockMvc mvc, ObjectMapper om) {
        this.mvc = mvc;
        this.om = om;
    }

    @BeforeEach
    public void setUp(){
        User user = new User(1,"ssarmeta@nate.com", "meta1234!@", "ssarmeta", "ROLE_USER");
        Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/path", 1000);
        Option option1 = new Option(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        Order order = new Order(1, user);
        Item item1 = new Item(1, option1, order, 5, 10000);
        Item item2 = new Item(2, option2, order, 5, 10900);
        given(orderService.saveOrder(any())).willReturn(order);
        given(orderService.saveItemByOrder(any())).willReturn(Arrays.asList(item1, item2));
        given(orderService.findByIdDTO(any(), anyList())).willReturn(new OrderResponse.FindByIdDTO(order, Arrays.asList(item1, item2)));
        given(orderService.findById(anyInt())).willReturn(order);
        given(orderService.findByOrderId(anyInt())).willReturn(Arrays.asList(item1, item2));
    }

    @DisplayName("결재 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void save_test() throws Exception {
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
    }

    @DisplayName("주문 결과 확인 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findById_test() throws Exception {
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/1")
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));

    }
}
