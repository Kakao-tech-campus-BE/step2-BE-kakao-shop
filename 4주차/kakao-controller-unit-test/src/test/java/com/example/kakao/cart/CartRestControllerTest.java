package com.example.kakao.cart;

import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
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

import java.util.ArrayList;
import java.util.List;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;
    @MockBean
    private CartService cartService;
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;


    @WithMockUser(username="ssar@nate.com", roles = "USER")
    @Test
    public void findAllCart() throws  Exception{
        List<Cart> response = new ArrayList<>();
        Cart cart1 = new Cart().builder()
                .id(1)
                .quantity(10)
                .option(Option.builder()
                        .id(1).optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .price(10000)
                        .product(Product.builder()
                                .id(1)
                                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                                .price(1000)
                                .build()).build())
                .build();


        response.add(cart1);


        //given
        BDDMockito.given(cartService.findAllCart(BDDMockito.any()))
                .willReturn(response);
        //when & then
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
                        .content("장바구니 조회 test")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].price").value(10000));

    }

    @WithMockUser(username="ssar@nate.com", roles = "USER")
    @Test
    public void addTest() throws Exception{
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO s1 = new CartRequest.SaveDTO();
        CartRequest.SaveDTO s2 = new CartRequest.SaveDTO();
        s1.setOptionId(1);
        s1.setQuantity(10);
        s1.setOptionId(2);
        s1.setQuantity(10);
        requestDTOs.add(s1);
        requestDTOs.add(s2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : " + requestBody);
        List<Cart> responses = new ArrayList<>();
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));



    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void updateTest() throws Exception {

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

        List<Cart> responseDTOs = new ArrayList<>();
        Cart cart1 = new Cart().builder()
                .id(1)
                .quantity(10)
                .option(Option.builder().id(1).optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종").price(10000).build())
                .build();
        Cart cart2 = new Cart().builder()
                .id(2)
                .quantity(10)
                .option(Option.builder().id(1).optionName("02. 슬라이딩 지퍼백 크리스마스에디션 4종").price(1000).build())
                .build();

        responseDTOs.add(cart1);
        responseDTOs.add(cart2);

//

        //given
        BDDMockito.given(cartService.updateCart(BDDMockito.any(), BDDMockito.any()))
                .willReturn(responseDTOs);
        //when & then
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].cartId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(100000));
    }
}
