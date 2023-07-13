package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.security.JWTProvider;
import com.example.kakaoshop.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    // 전체 상품 목록 조회
    public void findAll_test() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(
                get("/carts")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(104500));
        resultActions.andExpect(jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.price").value(10000));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].price").value(50000));
    }


    @Test
    @WithMockUser
    void addItem_test() throws Exception {

        // given
        User user = User.builder()
                .email("meta@nate.com")
                .username("meta")
                .password("meta1234!")
                .roles("USER")
                .build();

        String jwt = JWTProvider.create(user);
        System.out.println(jwt);

        List<CartRequest.CartAddDTO> cartAddDTOList = new ArrayList<>();

        CartRequest.CartAddDTO cartAddDTO1 = new CartRequest.CartAddDTO();
        cartAddDTO1.setOptionId(1L);
        cartAddDTO1.setQuantity(10);

        CartRequest.CartAddDTO cartAddDTO2 = new CartRequest.CartAddDTO();
        cartAddDTO2.setOptionId(2L);
        cartAddDTO2.setQuantity(5);

        cartAddDTOList.add(cartAddDTO1);
        cartAddDTOList.add(cartAddDTO2);


        // when
        String content = objectMapper.writeValueAsString(cartAddDTOList);
        System.out.println(content);
        ResultActions resultActions = mvc.perform(
                post("/carts")
                        .header(JWTProvider.HEADER, jwt)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);


        // verify

        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").doesNotExist());
        resultActions.andExpect(jsonPath("$.error").doesNotExist());
    }
}
