package com.example.kakao.cart;
import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.util.WithMockCustomUser;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@Import({
        GlobalExceptionHandler.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest extends DummyEntity {


    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @MockBean CartService cartService;
    @Autowired
    private MockMvc mvc;


    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("/carts/update 장바구니 수정")
    @WithMockCustomUser(username = "yunzae", roles = "ROLE_USER" , userId = 1 )
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

        // stub
        List<Product> productDummy = productDummyList();
        List<Option> optionDummy = optionDummyList(productDummy);
        List<Cart> carts = new ArrayList<>();
        Cart cart1 = Cart.builder().id(d1.getCartId()).option(optionDummy.get(0)).price(optionDummy.get(0).getPrice()*d1.getQuantity()).quantity(d1.getQuantity()).user(null).build();
        Cart cart2 = Cart.builder().id(d2.getCartId()).option(optionDummy.get(1)).price(optionDummy.get(1).getPrice()*d2.getQuantity()).quantity(d2.getQuantity()).user(null).build();
        carts.add(cart1);
        carts.add(cart2);
        CartResponse.UpdateDTO responseDTO= new CartResponse.UpdateDTO(carts);
        Mockito.when(cartService.update(anyList(),anyInt())).thenReturn(responseDTO);
        String DTOjson = om.writeValueAsString(responseDTO);
        System.out.println("테스트 : "+DTOjson);

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
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(100000));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("post - /carts/add 장바구니 추가")
    void addCartList_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(1);
        d1.setQuantity(50);
        CartRequest.SaveDTO d2 = new CartRequest.SaveDTO();
        d1.setOptionId(2);
        d1.setQuantity(40);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

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

    @Test
    @WithMockCustomUser
    @DisplayName("get - /carts 장바구니 조회")
    void findAll_tset() throws Exception {
        // given
        int cart1Id = 1;
        int cart1Quantity = 10;
        int cart2Id = 2;
        int cart2Quantity = 20;

        // stub

        List<Product> productDummy = productDummyList();
        List<Option> optionDummy = optionDummyList(productDummy);
        List<Cart> carts = new ArrayList<>();
        Cart cart1 = Cart.builder().id(cart1Id).option(optionDummy.get(0)).price(optionDummy.get(0).getPrice()*cart1Quantity).quantity(cart1Quantity).user(null).build();
        Cart cart2 = Cart.builder().id(cart2Id).option(optionDummy.get(1)).price(optionDummy.get(1).getPrice()*cart2Quantity).quantity(cart2Quantity).user(null).build();
        carts.add(cart1);
        carts.add(cart2);
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(carts);
        Mockito.when(cartService.findAll(anyInt())).thenReturn(responseDTO);
        String DTOjson = om.writeValueAsString(responseDTO);
        System.out.println("테스트 : "+DTOjson);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].price").value(100000));
    }


    @Test
    @WithMockCustomUser
    @DisplayName("post - /carts/clear 장바구니 비우기")
    void clear_tset() throws Exception {
        // given
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/clear")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
}
