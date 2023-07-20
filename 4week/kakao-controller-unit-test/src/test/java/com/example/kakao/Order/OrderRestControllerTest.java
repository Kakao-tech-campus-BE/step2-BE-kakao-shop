package com.example.kakao.Order;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.CartRestController;
import com.example.kakao.order.Order;
import com.example.kakao.order.OrderRestController;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {
    @MockBean
    private FakeStore fakeStore;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;
    private List<Item> itemList = new ArrayList<>();
    private List<Order> orderList = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        User user = User.builder().id(1).username("rnjsgo@nate.com").roles("USER").build();
        Order order = Order.builder().user(user).id(2).build();
        orderList.add(order);

        Product product= Product.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .build();

        Option option1= Option.builder()
                .product(product)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .price(10000)
                .build();

        Option option2= Option.builder()
                .product(product)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .price(10900)
                .build();

        Item item1 = Item.builder()
                .id(4)
                .order(order)
                .option(option1)
                .quantity(10)
                .price(100000)
                .build();

        Item item2 = Item.builder()
                .id(5)
                .order(order)
                .option(option2)
                .quantity(10)
                .price(109000)
                .build();
        itemList.add(item1);
        itemList.add(item2);
    }

    @WithMockUser(username = "rnjsgo@nate.com", roles = "USER")
    @Test // URL : "/orders/save"
    public void save_test() throws Exception {
        //given

        //stub
        Mockito.when(fakeStore.getOrderList()).thenReturn(orderList);
        Mockito.when(fakeStore.getItemList()).thenReturn(itemList);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(4));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value(109000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(209000));
    }

    @WithMockUser(username = "rnjsgo@nate.com", roles = "USER")
    @Test // URL : "/orders/{id}"
    public void getOrderById() throws Exception {
        //given
        int id=1;

        //stub
        Mockito.when(fakeStore.getOrderList()).thenReturn(orderList);
        Mockito.when(fakeStore.getItemList()).thenReturn(itemList);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+id)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(4));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value(109000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(209000));    }
}
