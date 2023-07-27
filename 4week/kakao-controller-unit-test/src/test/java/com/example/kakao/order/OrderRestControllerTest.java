package com.example.kakao.order;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.Cart;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Arrays;

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

    // (기능 12) 결재
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void save_test() throws Exception {
        // given
        User user = User.builder()
                .id(1)
                .roles("ROLE_USER")
                .build();

        // stub
        Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);

        Option option1 = new Option(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        Order order = new Order(1, user);

        Mockito.when(fakeStore.getOrderList()).thenReturn(
                Arrays.asList(order)
        );

        Mockito.when(fakeStore.getItemList()).thenReturn(
                Arrays.asList(
                        new Item(1, option1, order, 5, 50000),
                        new Item(2, option2, order, 5, 54500)
                )
        );


        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value("1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value("1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value("1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value("5"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value("50000"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value("2"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value("5"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value("54500"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value("104500"));
    }

    // (기능13) 주문 결과 확인
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findAll_test() throws Exception {
        // given
        User user = User.builder()
                .id(1)
                .roles("ROLE_USER")
                .build();

        // stub
        Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);

        Option option1 = new Option(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        Order order = new Order(1, user);

        Mockito.when(fakeStore.getOrderList()).thenReturn(
                Arrays.asList(order)
        );

        Mockito.when(fakeStore.getItemList()).thenReturn(
                Arrays.asList(
                        new Item(1, option1, order, 5, 50000),
                        new Item(2, option2, order, 5, 54500)
                )
        );

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value("1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value("1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value("1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value("5"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value("50000"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value("2"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value("5"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value("54500"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value("104500"));
    }
}
