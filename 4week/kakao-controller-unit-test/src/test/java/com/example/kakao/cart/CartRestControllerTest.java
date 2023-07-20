package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.CustomUserDetailsService;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.user.User;
import com.example.kakao.user.UserResponse;
import com.example.kakao.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import org.springframework.security.core.userdetails.UserDetails;
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

    @MockBean
    private CartService cartService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    /**
     * 추가로 만들어야 할 거
     *
     * 1. 장바구니 담기
     * 2. 장바구니 보기
     * 3. 장바구니 주문하기(업데이트)는 구현되어 있음
     * 4. 장바구니 비우기
     *
     */
    @DisplayName("장바구니 담기 controller 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void add_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(1);
        d1.setQuantity(10);
        CartRequest.SaveDTO d2 = new CartRequest.SaveDTO();
        d2.setOptionId(2);
        d2.setQuantity(10);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        User user = User.builder().id(1).roles("ROLE_USER").build();
        String jwt = JWTProvider.create(user);



        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwt)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @DisplayName("장바구니 주문하기 controller 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void update_test() throws Exception {
        // given
        int cartId_1 = 1;
        int quantity_1 = 10;
        int cartId_2 = 2;
        int quantity_2 = 10;

        List<CartRequest.UpdateDTO> requestDTOs = List.of(
                new CartRequest.UpdateDTO(cartId_1,quantity_1),
                new CartRequest.UpdateDTO(cartId_2,quantity_2)
        );
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        User user = User.builder().id(1).roles("ROLE_USER").build();
        String jwt = JWTProvider.create(user);

        //stub
        List<CartResponse.UpdateDTO.CartDTO> carts = List.of(
                new CartResponse.UpdateDTO.CartDTO(cartId_1,1,"01. 슬라이딩 지퍼백 크리스마스에디션 4종",quantity_1,100000),
                new CartResponse.UpdateDTO.CartDTO(cartId_2,2,"02. 슬라이딩 지퍼백 크리스마스에디션 4종",quantity_2,50000)
        );
        CartResponse.UpdateDTO userResponse = new CartResponse.UpdateDTO(carts,150000);
        Mockito.when(cartService.update(any(),any())).thenReturn(userResponse);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwt)
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
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[1].cartId").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[1].optionId").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[1].optionName").value("02. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[1].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[1].price").value(50000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(150000));

    }

    @DisplayName("장바구니 보기 controller 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findAll_test() throws Exception {
        // given
        CartResponse.FindAllDTO.ProductDTO.CartDTO.OptionDTO op1 = new CartResponse.FindAllDTO.ProductDTO.CartDTO.OptionDTO(1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 100000);
        CartResponse.FindAllDTO.ProductDTO.CartDTO.OptionDTO op2 = new CartResponse.FindAllDTO.ProductDTO.CartDTO.OptionDTO(2, "02. 슬라이딩 지퍼백 크리스마스에디션 4종", 50000);
        List<CartResponse.FindAllDTO.ProductDTO.CartDTO> cartDTOS = List.of(
                new CartResponse.FindAllDTO.ProductDTO.CartDTO(1, op1, 10, 1000000),
                new CartResponse.FindAllDTO.ProductDTO.CartDTO(2, op2, 5, 250000)
        );
        List<CartResponse.FindAllDTO.ProductDTO> productDTOS = List.of(new CartResponse.FindAllDTO.ProductDTO(1, "상품명!!!", cartDTOS));
        CartResponse.FindAllDTO userResponse = new CartResponse.FindAllDTO(productDTOS, 1250000);

        User user = User.builder().id(1).roles("ROLE_USER").build();
        String jwt = JWTProvider.create(user);

        //stub
        Mockito.when(cartService.findAll(any())).thenReturn(userResponse);


        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwt)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("상품명!!!"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.price").value(100000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].price").value(1000000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].option.id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].option.optionName").value("02. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].option.price").value(50000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].price").value(250000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(1250000));

    }

    @DisplayName("장바구니 비우기 controller 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void clear_test() throws Exception {
        //given
        User user = User.builder().id(1).roles("ROLE_USER").build();
        String jwt = JWTProvider.create(user);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/clear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwt)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
}
