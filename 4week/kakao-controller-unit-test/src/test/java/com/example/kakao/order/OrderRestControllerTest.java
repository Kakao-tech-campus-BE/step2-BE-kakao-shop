package com.example.kakao.order;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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

    @BeforeEach
    public void setUp() {
        User user = new User(1, "ssar@nate.com", "1234", "ssar", "USER");
        Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        List<Option> options = Arrays.asList(
                new Option(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
                new Option(2, product, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
                new Option(3, product, "고무장갑 베이지 S(소형) 6팩", 9900)
        );
        List<Order> orders = Arrays.asList(new Order(1, user));
        List<Item> items = Arrays.asList(
                new Item(1, options.get(0), orders.get(0), 3, options.get(0).getPrice()*3),
                new Item(2, options.get(1), orders.get(0), 5, options.get(0).getPrice()*5)
        );
        Mockito.when(fakeStore.getOrderList()).thenReturn(orders);
        Mockito.when(fakeStore.getItemList()).thenReturn(items);
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void save_test() throws Exception {
        // given

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        result.andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(3));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(30000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value(54500));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(84500));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findById_test() throws Exception {
        // given
        int order_id = 1;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/{id}", order_id)
        );
        result.andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(order_id));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(3));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(30000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value(54500));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(84500));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }
}
