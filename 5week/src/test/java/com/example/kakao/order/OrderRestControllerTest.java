package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.util.WithMockCustomUser;
import com.example.kakao.cart.Cart;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

@Import({
        SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
class OrderRestControllerTest extends DummyEntity {
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @MockBean
    OrderService orderService;
    @Autowired
    private MockMvc mvc;


    @Autowired
    private ObjectMapper om;

    @Test
    @WithMockCustomUser
    @DisplayName("post - /orders/save 주문하기")
    void save() throws Exception {
        // given


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
    }

    @Test
    @WithMockCustomUser(userId = 1)
    @DisplayName("get - /orders 주문조회하기")
    void findById() throws Exception {
        // given
        int  userid=1;
        int orderid=1;
        // stub
        User user= newUser("yunzae");
        List<Product> productDummy = productDummyList();
        List<Option> optionDummy = optionDummyList(productDummy);
        List<Cart> cartDummy = cartDummyList(user,optionDummy,10);
        Order order = new Order(orderid,user);
        List<Item> itemDummy = itemDummyList(cartDummy,order);
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order,itemDummy);
        Mockito.when(orderService.findById(anyInt(),anyInt())).thenReturn(responseDTO);
        String DTOjson = om.writeValueAsString(responseDTO);
        System.out.println("테스트 : "+DTOjson);
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+userid)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(orderid));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(100000));

    }
}