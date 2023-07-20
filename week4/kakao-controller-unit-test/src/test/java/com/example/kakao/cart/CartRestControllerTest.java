package com.example.kakao.cart;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {

    @Autowired
    private FakeStore fakeStore;

    @MockBean
    private CartService cartService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void update_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO d1 = new CartRequest.UpdateDTO();
        d1.setCartId(1);
        d1.setQuantity(10);
        CartRequest.UpdateDTO d2 = new CartRequest.UpdateDTO();
        d2.setCartId(2);
        d2.setQuantity(10);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : " + requestBody);

        // stub
        List<Cart> newCarts = new ArrayList<>();
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            for (Cart cart : fakeStore.getCartList()) {
                if(cart.getId() == updateDTO.getCartId()){
                    newCarts.add(Cart.builder()
                            .id(cart.getId())
                            .option(cart.getOption())
                            .price(cart.getPrice() * updateDTO.getQuantity())
                            .quantity(updateDTO.getQuantity())
                            .user(cart.getUser())
                            .build()
                    );
                }
            }
        }
        Mockito.when(cartService.update(any()))
                .thenReturn(new CartResponse.UpdateDTO(newCarts));

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].cartId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(100000));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findAll_test() throws Exception {
        // given

        // stub
        CartResponse.FindAllDTO response = new CartResponse.FindAllDTO(fakeStore.getCartList());
        Mockito.when(cartService.findAll()).thenReturn(response);
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(104500));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].price").value(50000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].option.id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].price").value(54500));
    }

    @WithMockUser(username = "ssar@nate.com", roles= "USER")
    @Test
    public void add_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(3);
        d1.setQuantity(1);
        CartRequest.SaveDTO d2 = new CartRequest.SaveDTO();
        d2.setOptionId(4);
        d2.setQuantity(2);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }
}
