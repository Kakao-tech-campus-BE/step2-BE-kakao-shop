package com.example.kakao.cart;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import({SecurityConfig.class})
@WebMvcTest(controllers = {CartRestController.class})
class CartRestControllerTest extends DummyEntity {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private FakeStore fakeStore;

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    void update_test() throws Exception {

        // given
        List<Cart> mockCarts = cartList;
        int cartId1 = 1;
        int quantity1 = 10;
        int cartId2 = 2;
        int quantity2 = 10;

        CartRequest.UpdateDTO d1 = new CartRequest.UpdateDTO(cartId1, quantity1);
        CartRequest.UpdateDTO d2 = new CartRequest.UpdateDTO(cartId2, quantity2);
        List<CartRequest.UpdateDTO> requestDTOs = List.of(d1, d2);
        String requestBody = om.writeValueAsString(requestDTOs);

        // stub
        when(fakeStore.getCartList()).thenReturn(mockCarts);
        /* stub을 이용하여 mockCart를 이용하게 하였음.
         이를 통하여, main 코드의 getCartList() 메서드를 낚아채고, test용 DummyEntity를 이용하도록 하였음.
         main의 DummyEntity를 이용하게 된다면, 만약 main의 entity가 변경되거나 삭제된다면,
         기존에 작성했던 테스트코드에서는 직접 main의 DummyEntity값들을 넣어줬기 때문에
         테스트코드까지 실패하게 되는 상황이 발생할 것이라고 생각하여 이렇게 구현했음.
         또한, 이렇게 함으로써 기존 테스트코드에서의 테스트 순서가 무작위인 상황에서 [조회 -> 수정]을 했을 때 조회 결과와,
         [수정 -> 조회]를 수행했을 때 조회 결과가 달라지는 문제를 해결할 수 있었음.
         (테스트의 결과가 다른 테스트의 결과에 의존하는 현상을 해결하였음)
        */

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].cartId").value(mockCarts.get(0).getId()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(mockCarts.get(0).getOption().getId()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value(mockCarts.get(0).getOption().getOptionName()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(quantity1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(mockCarts.get(0).getOption().getPrice() * quantity1));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    void addCartList_test() throws Exception { // addCartList()를 수행해도, 어떻게하든 sout만 실행하고, success가 뜬다.

        // given
        int optionId1 = 1;
        int quantity1 = 5;
        int optionId2 = 2;
        int quantity2 = 5;

        CartRequest.SaveDTO s1 = new CartRequest.SaveDTO(optionId1, quantity1);
        CartRequest.SaveDTO s2 = new CartRequest.SaveDTO(optionId2, quantity2);
        List<CartRequest.SaveDTO> saveDTOS = Arrays.asList(s1, s2);
        String requestBody = om.writeValueAsString(saveDTOS);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andDo(print());

        // then
        result.andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.jsonPath("$.success").value("true"),
                MockMvcResultMatchers.jsonPath("$.response").isEmpty(),
                MockMvcResultMatchers.jsonPath("$.error").isEmpty()
        );

    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    void findAll_test() throws Exception {

        // given
        List<Cart> mockCarts = cartList;

        // stub
        when(fakeStore.getCartList()).thenReturn(mockCarts);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
                        .accept(MediaType.APPLICATION_JSON));

        result.andDo(print());

        // then
        result.andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.jsonPath("$.success").value("true"),
                MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(mockCarts.get(0).getOption().getProduct().getId()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value(mockCarts.get(0).getOption().getProduct().getProductName()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(mockCarts.get(0).getId()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.id").value(mockCarts.get(0).getOption().getId()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.optionName").value(mockCarts.get(0).getOption().getOptionName()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.price").value(mockCarts.get(0).getOption().getPrice()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(mockCarts.get(0).getQuantity()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].price").value(mockCarts.get(0).getPrice()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].id").value(mockCarts.get(1).getId()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].option.id").value(mockCarts.get(1).getOption().getId()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].option.optionName").value(mockCarts.get(1).getOption().getOptionName()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].option.price").value(mockCarts.get(1).getOption().getPrice()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].quantity").value(mockCarts.get(1).getQuantity()),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].price").value(mockCarts.get(1).getPrice()),
                MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(
                        mockCarts.stream().map(Cart::getPrice).mapToInt(Integer::intValue).sum()
                ),
                MockMvcResultMatchers.jsonPath("$.error").isEmpty()
        );
    }
}
