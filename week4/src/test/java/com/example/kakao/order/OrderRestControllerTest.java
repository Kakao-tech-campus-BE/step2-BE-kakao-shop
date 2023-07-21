package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.DependsOn;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private FakeStore fakeStore;

    int id;
    private  Order order;
    private List<Item> itemList;
    private List<Option> optionList;

    private Product product;
    @BeforeEach
    public void init(){
        id = 1;
        User user = createUser("ssar");
        product = createProduct("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");

        order = createOrder(user, id);
        Option option1 = createOption(product, id, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = createOption(product, id, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        optionList = Arrays.asList(option1, option2);
        itemList = Arrays.asList(
                createItem(1, order, option1, 5),
                createItem(1, order, option2, 5)
        );

        when(fakeStore.getOrderList()).thenReturn(Arrays.asList(order));
        when(fakeStore.getItemList()).thenReturn(itemList);
        when(fakeStore.getProductList()).thenReturn(Arrays.asList(product));
        when(fakeStore.getOptionList()).thenReturn(optionList);
    }
    @DisplayName("주문하기")
    @WithMockUser(username = "ssar", roles = "USER")
    @Test
    public void save_test() throws Exception{

        OrderResponse.FindByIdDTO requestDTO = new OrderResponse.FindByIdDTO(order, itemList);

        String requestBody = om.writeValueAsString(requestDTO);
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/orders/save")
                        .content(requestBody)
                        .contentType("application/json")
        );
        System.out.println("result = " + result.andReturn().getResponse().getContentAsString());

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
    }
    @DisplayName("주문 조회하기")
    @WithMockUser(username = "ssar", roles = "USER")
    @Test
    public void findById_test() throws Exception{

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/orders/{id}",id)
                        .contentType("application/json")
        );
        System.out.println("result = " + result.andReturn().getResponse().getContentAsString());

        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
    }



    private User createUser(String username) {
        return User.builder().id(id).username(username).build();
    }

    private Product createProduct( String productName) {
        return Product.builder().id(id).productName("Product name").build();
    }
    private Order createOrder(User user, int id) {
        return Order.builder().user(user).id(id).build();
    }

    private Option createOption(Product product, int id, String optionName, int price) {
        return Option.builder().product(product).id(id).optionName(optionName).price(price).build();
    }

    private Item createItem(int id, Order order, Option option, int quantity) {
        return Item.builder().id(id).order(order).option(option).quantity(quantity).build();
    }

}
