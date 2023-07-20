package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.Cart;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.util.List;

@Import({
        FakeStore.class,
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest extends DummyEntity {
    // stub 구현을 위해
    @MockBean
    private FakeStore fakeStore;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    // fakeStore Mockito
    List<Item> itemList = new ArrayList<>();
    List<Order> orderList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        User user = newUser("ssar");

        Product product = newProduct("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000);

        Option option1 = newOption(product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = newOption(product, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);

        Cart cart1 = newCart(user, option1, 10);
        Cart cart2 = newCart(user, option2, 10);

        Order order = newOrder(user);

        Item item1 = newItem(cart1, order);

        Item item2 = newItem(cart2, order);

        itemList.add(item1);
        itemList.add(item2);

        orderList.add(order);
    }

    // 결재
    @WithMockUser(username = "ssar@name.com", roles = "USER")
    @Test
    public void save_test() throws Exception {
        // given
        Mockito.when(fakeStore.getOrderList()).thenReturn(orderList);
        Mockito.when(fakeStore.getItemList()).thenReturn(itemList);
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(100000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(209000));
    }

    // 주문 결과 확인
}
