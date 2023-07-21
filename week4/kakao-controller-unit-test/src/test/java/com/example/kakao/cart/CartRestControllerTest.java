package com.example.kakao.cart;

import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Arrays;
import java.util.List;

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


    // 장바구니 담기 - 성공
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void cart_add_success_test() throws Exception {
        // given
        /* Build Request DTO */
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(1);
        d1.setQuantity(5);
        CartRequest.SaveDTO d2 = new CartRequest.SaveDTO();
        d2.setOptionId(2);
        d2.setQuantity(5);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("===== [장바구니 담기 테스트] =====");
        System.out.println("requestDTO : " + requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);
        System.out.println();

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    // 장바구니 조회 - 성공
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void cart_check_success_test() throws Exception {
        // given
        /* stub - cart */
        Product product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        Option option1 = new Option(1, product1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product1, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        User user1 = new User(1, "ssar@nate.com", "meta1234!", "ssar", "USER");
        BDDMockito.given(fakeStore.getCartList()).willReturn(Arrays.asList(
                newCart(1, user1, option1, 5),
                newCart(2, user1, option2, 5)
        ));

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("===== [장바구니 조회 테스트] =====");
        System.out.println(responseBody);
        System.out.println();

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(104500));
    }

    // 장바구니 수정 - 성공
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void cart_update_success_test() throws Exception {
        // given
        /* Build Request DTO */
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
        System.out.println("===== [장바구니 수정 테스트] =====");
        System.out.println("requestDTO : " + requestBody);

        /* stub - cart */
        Product product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        Option option1 = new Option(1, product1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product1, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        User user1 = new User(1, "ssar@nate.com", "meta1234!", "ssar", "USER");
        BDDMockito.given(fakeStore.getCartList()).willReturn(Arrays.asList(
                newCart(1, user1, option1, 5),
                newCart(2, user1, option2, 5)
        ));

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);
        System.out.println();

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].cartId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(100000));
    }
}
