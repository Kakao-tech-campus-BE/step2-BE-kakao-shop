package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.Order;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.ProductOption;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest extends DummyEntity {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FakeStore fakeStore;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper om;

    List<CartRequest.SaveDTO> requestDTOs;

    CartRequest.SaveDTO cartDTO1;
    CartRequest.SaveDTO cartDTO2;

    User user;

    Cart cart1;
    Cart cart2;
    Product product;
    ProductOption productOption1;
    Order order;

    @BeforeEach
    public void setUp() {
        user = newUser("sohyun");

        user = newUser("sohyun");
        order = newOrder(user);
        //Product newProduct(String productName, int imageNumber, int price)
        product = newProduct("test", 1, 10000);
        //ProductOption newOption(Product product, String optionName, int price)
        productOption1 = newOption(product, "testOption1", 2000);
        //newCart(User user, ProductOption productOption, Integer quantity)

        requestDTOs = new ArrayList<>();
        cartDTO1 = new CartRequest.SaveDTO();
        cartDTO1.setOptionId(1);
        cartDTO1.setQuantity(5);
        requestDTOs.add(cartDTO1);
        cart1 = newCart(user, productOption1, 5);

        cartDTO2 = new CartRequest.SaveDTO();
        cartDTO2.setOptionId(2);
        cartDTO2.setQuantity(5);
        requestDTOs.add(cartDTO2);
    }

    @Test
    public void testAddCart() throws Exception {

        User user = newUser("sohyun");
        UserDetails userDetails = new CustomUserDetails(user);

        // 사용자 정보로 인증 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        // SecurityContextHolder에 인증 객체 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String requestBody = om.writeValueAsString(requestDTOs);

/*
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
*/
        mvc.perform(MockMvcRequestBuilders.post("/carts").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    public void testAddCartUnAuthorized() throws Exception {
        String requestBody = om.writeValueAsString(requestDTOs);

        // Check the response status and body
        mvc.perform(MockMvcRequestBuilders.post("/carts").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"))
                .andExpect(jsonPath("$.error.status").value(401));
    }

    @Test
    public void addCartInvalidQuantity() throws Exception {

        User user = newUser("sohyun");
        UserDetails userDetails = new CustomUserDetails(user);

        // 사용자 정보로 인증 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        // SecurityContextHolder에 인증 객체 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CartRequest.SaveDTO invalidQuantityDTO = new CartRequest.SaveDTO();
        invalidQuantityDTO.setOptionId(1);
        invalidQuantityDTO.setQuantity(-5);

        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        requestDTOs.add(invalidQuantityDTO);

        String requestBody = om.writeValueAsString(requestDTOs);

/*
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
*/
        // Check the response status and body
        mvc.perform(MockMvcRequestBuilders.post("/carts").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("잘못된 수량입니다."+-5))
                .andExpect(jsonPath("$.error.status").value(400));

    }

    @Test
    public void findAllAuthenticatedUser() throws Exception {
        User user = newUser("sohyun");
        UserDetails userDetails = new CustomUserDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
/*
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
*/
        // Check the response status and body
        mvc.perform(MockMvcRequestBuilders.get("/carts"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.products").isEmpty())
                .andExpect(jsonPath("$.response.totalPrice").value(0))
                .andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    public void findAllUnauthenticatedUser() throws Exception {
        // 인증되지 않은 사용자로 가정하기 위해 SecurityContextHolder의 인증 객체를 null로 설정
        SecurityContextHolder.getContext().setAuthentication(null);

        // Check the response status and body
        mvc.perform(MockMvcRequestBuilders.get("/carts"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"))
                .andExpect(jsonPath("$.error.status").value(401))
                .andExpect(jsonPath("$.response").doesNotExist());
    }

    @Test
    public void testUpdateCart() throws Exception {
        // 인증된 사용자로 가정하기 위해 CustomUserDetails 객체를 생성하여 SecurityContextHolder에 설정
        User user = newUser("sohyun");
        UserDetails userDetails = new CustomUserDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CartRequest.UpdateDTO updateDTO1 = new CartRequest.UpdateDTO();
        updateDTO1.setCartId(2);
        updateDTO1.setQuantity(10);

        CartRequest.UpdateDTO updateDTO2 = new CartRequest.UpdateDTO();
        updateDTO2.setCartId(4);
        updateDTO2.setQuantity(10);

        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        requestDTOs.add(updateDTO1);
        requestDTOs.add(updateDTO2);

        String requestBody = om.writeValueAsString(requestDTOs);
/*
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .put("/carts")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
*/
        // PUT 요청 수행
        mvc.perform(MockMvcRequestBuilders.put("/carts").content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.carts").isEmpty())
                .andExpect(jsonPath("$.response.totalPrice").value(0))
                .andExpect(jsonPath("$.error").doesNotExist());

    }

    @Test
    public void updateCartUnauthenticated() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);

        String requestBody = om.writeValueAsString(requestDTOs);

        // Check the response status and body
        mvc.perform(MockMvcRequestBuilders.put("/carts").content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"))
                .andExpect(jsonPath("$.error.status").value(401))
                .andExpect(jsonPath("$.response").doesNotExist());
    }


    @Test
    public void testDeleteCartAuthenticated() throws Exception {
        User user = newUser("sohyun");
        UserDetails userDetails = new CustomUserDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 장바구니를 비우는 요청을 보냄
        ResultActions result = mvc.perform(MockMvcRequestBuilders.delete("/carts"));

        // HTTP 상태 코드가 200 OK인지 확인
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").doesNotExist());
    }

    @Test
    public void testDeleteCartUnauthenticated() throws Exception {
        // 사용자가 인증되지 않은 상태로 요청을 보냄
        ResultActions result = mvc.perform(MockMvcRequestBuilders.delete("/carts"));
/*
        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
*/
        // HTTP 상태 코드가 401 Unauthorized인지 확인
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("인증되지 않았습니다"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(401));
    }


}


