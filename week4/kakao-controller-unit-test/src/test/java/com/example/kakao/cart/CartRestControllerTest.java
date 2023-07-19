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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest extends DummyEntity {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private FakeStore fakeStore;

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
        System.out.println("테스트 : "+requestBody);

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
    void addCartList_test() throws Exception { // addCartList()를 수행해도, 어떻게하든 sout만 실행하고, success가 뜬다.

        // given
        CartRequest.SaveDTO s1 = new CartRequest.SaveDTO(1, 5);
        CartRequest.SaveDTO s2 = new CartRequest.SaveDTO(2, 5);
        List<CartRequest.SaveDTO> saveDTOS = Arrays.asList(s1, s2);
        String requestBody = om.writeValueAsString(saveDTOS);
        System.out.println("테스트 : " + requestBody);

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
        List<Cart> mockCartList = cartDummyList(optionList);

        // stub
        when(fakeStore.getCartList()).thenReturn(mockCartList);

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
                MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1),
                MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(1),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.id").value(1),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.price").value(10000),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(5),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].price").value(50000),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].id").value(2),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].option.id").value(2),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].option.optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].option.price").value(10900),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].quantity").value(5),
                MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].price").value(54500),
                MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(104500),
                MockMvcResultMatchers.jsonPath("$.error").isEmpty()
        );
    }
}
