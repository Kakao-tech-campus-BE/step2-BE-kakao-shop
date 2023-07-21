package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@Import({
        GlobalExceptionHandler.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {
    @MockBean
    private CartService cartService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    private final MockMvc mvc;

    private final ObjectMapper om;
    @Autowired
    public CartRestControllerTest(MockMvc mvc, ObjectMapper om) {
        this.mvc = mvc;
        this.om = om;
    }

    @DisplayName("장바구니 담기 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void add_test() throws Exception {
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
        System.out.println("테스트 : " + requestBody);

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
    }

    @DisplayName("장바구니 조회 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void check_test() throws Exception{
        User user = new User(1,"ssarmeta@nate.com", "meta1234!@", "ssarmeta", "ROLE_USER");
        Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/path", 1000);
        Option option1 = new Option(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        given(cartService.checkCart(any())).willReturn(
                Arrays.asList(
                        new Cart(1, user, option1, 5, 50000),
                        new Cart(2, user, option2, 5, 5*10900)
                )
        );

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
        );


        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
    }

    @DisplayName("장바구니 초기화 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void clear_test() throws Exception {
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/clear")
        );

        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
    }

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

        User user = new User(1,"ssarmeta@nate.com", "meta1234!@", "ssarmeta", "ROLE_USER");
        Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/path", 1000);
        Option option1 = new Option(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        given(cartService.checkCart(any())).willReturn(
                Arrays.asList(
                        new Cart(1, user, option1, 10, 50000),
                        new Cart(2, user, option2, 10, 5*10900)
                )
        );

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
}
