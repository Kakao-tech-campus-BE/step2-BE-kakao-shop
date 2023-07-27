package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.CartRestController;
import com.example.kakao.cart.CartService;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest extends DummyEntity {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;
    @MockBean
    private OrderService orderService;
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true)) // UTF-8로 인코딩 설정
                .build();
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void saveTest() throws Exception {
        // given
        User user = newUser("ssar");
        Product product = newProduct(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000);
        Order order = newOrder(user);
        List<Item> itemList = new ArrayList<>();
        itemList.add(newItem(1, newCart(user, newOption(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000), 10), order));
        itemList.add(newItem(2, newCart(user, newOption(2, product, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10090), 10), order));


        BDDMockito.given(orderService.orderSave(BDDMockito.any()))
                .willReturn(itemList);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(200900));

    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findById() throws Exception {
        // given
        User user = newUser("ssar");
        Product product = newProduct(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000);
        Order order = newOrder(user);

        List<Item> itemList = new ArrayList<>();
        itemList.add(newItem(1,newCart(user,newOption(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),10),order));
        itemList.add(newItem(2, newCart(user, newOption(2, product, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10090), 10), order));

        BDDMockito.given(orderService.orderById(1))
                .willReturn(itemList);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(200900));

    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void noOrderIdTest() throws Exception{
        // given
        User user = newUser("ssar");
        Product product = newProduct(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000);
        Order order = newOrder(user);

        List<Item> itemList = new ArrayList<>();
        itemList.add(newItem(1,newCart(user,newOption(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),10),order));
        itemList.add(newItem(2, newCart(user, newOption(2, product, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10090), 10), order));

        BDDMockito.given(orderService.orderById(1000))
                .willThrow(new Exception400("존재하지 않는 주문ID입니다"));

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/1000")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value("400"));
    }
}
