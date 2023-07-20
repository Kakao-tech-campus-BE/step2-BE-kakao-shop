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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        User user=User.builder().id(id).username("ssar").build();
        product = Product.builder().id(id).productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전").build();
        order = Order.builder().user(user).id(id).build();
        Option option1 = Option.builder().product(product).id(id).optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종").price(10000).build();
        Option option2 = Option.builder().product(product).id(id).optionName("02. 슬라이딩 지퍼백 플라워에디션 5종").price(10900).build();
        optionList = Arrays.asList(option1, option2);
        itemList = Arrays.asList(
                Item.builder().id(1).order(order).option(option1).quantity(5).build(),
                Item.builder().id(1).order(order).option(option2).quantity(5).build()

        );
    }
    @DisplayName("주문하기")
    @WithMockUser(username = "ssar", roles = "USER")
    @Test
    public void save_test() throws Exception{

        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
        when(fakeStore.getOrderList()).thenReturn(Arrays.asList(order));
        when(fakeStore.getItemList()).thenReturn(itemList);
        when(fakeStore.getProductList()).thenReturn(Arrays.asList(product));
        when(fakeStore.getOptionList()).thenReturn(optionList);

        String requestBody = om.writeValueAsString(responseDTO);
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/orders/save")
                        .content(requestBody)
                        .contentType("application/json")
        );
        System.out.println("result = " + result.andReturn().getResponse().getContentAsString());

    }
    @DisplayName("주문 결과 확인")
    @WithMockUser(username = "ssar", roles = "USER")
    @Test
    public void findById_test() throws Exception{
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
        when(fakeStore.getOrderList()).thenReturn(Arrays.asList(order));
        when(fakeStore.getItemList()).thenReturn(itemList);
        when(fakeStore.getProductList()).thenReturn(Arrays.asList(product));
        when(fakeStore.getOptionList()).thenReturn(optionList);


        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/orders/{id}",id)
                        .contentType("application/json")
        );
        System.out.println("result = " + result.andReturn().getResponse().getContentAsString());
    }


}
