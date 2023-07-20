package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.log.ErrorLogJPARepository;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@Import({
        GlobalExceptionHandler.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {CartRestController.class})
class CartRestControllerTest extends DummyEntity {

    @MockBean
    private CartService cartService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @WithMockUser(username = "gihae0805@nate.com", roles = "USER")
    @Test
    @DisplayName("장바구니 저장 성공")
    void add_success_test() throws Exception {
        //given
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
        System.out.println("requestBody=" + requestBody);

        //stub - 필요하지 않음

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }

    @WithMockUser(username = "gihae0805@nate.com", roles = "USER")
    @Test
    @DisplayName("장바구니 저장 실패 - 최소 수량 미달")
    void add_fail_test1() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(1);
        d1.setQuantity(-1); //5에서 -1로 수정

        requestDTOs.add(d1);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //stub - 필요하지 않음

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("수량은 최소 1 이상 이어야 합니다."));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }

    @WithMockUser(username = "gihae0805@nate.com", roles = "USER")
    @Test
    @DisplayName("장바구니 저장 실패 - 최대 수량 초과")
    void add_fail_test2() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(1);
        d1.setQuantity(1000); //5에서 1000으로 수정

        requestDTOs.add(d1);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //stub - 필요하지 않음

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("최대 수량은 100개를 넘을 수 없습니다."));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }

    @WithMockUser(username = "gihae0805@nate.com", roles = "USER")
    @Test
    @DisplayName("장바구니 저장 실패 - 옵션 없음")
    void add_fail_test3() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setQuantity(1); //option id 정보 누락

        requestDTOs.add(d1);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //stub - 필요하지 않음

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("옵션 아이디는 필수 입력 값입니다."));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }

    @WithMockUser(username = "gihae0805@nate.com", roles = "USER")
    @Test
    @DisplayName("장바구니 조회")
    void findAll_test() throws Exception {
        //given

        //stub
        List<Cart> cartList = new ArrayList<>();
        List<Option> optionList = optionDummyList(productDummyList());
        cartList.add(newCart(4, newUser("gihae0805"), optionList.get(0), 5));
        cartList.add(newCart(5, newUser("gihae0805"), optionList.get(1), 5));

        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartList);
        Mockito.when(cartService.findAll()).thenReturn(responseDTO);
        System.out.println("responseDTO= " + responseDTO);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody= " + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(104500));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(4));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.price").value(10000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].price").value(50000));
    }

    @WithMockUser(username = "gihae0805@nate.com", roles = "USER")
    @Test
    @DisplayName("장바구니 수정 성공")
    void update_success_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO req1 = new CartRequest.UpdateDTO();
        req1.setCartId(1);
        req1.setQuantity(10);

        CartRequest.UpdateDTO req2 = new CartRequest.UpdateDTO();
        req2.setCartId(2);
        req2.setQuantity(10);

        requestDTOs.add(req1);
        requestDTOs.add(req2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //stub
        List<Cart> cartList = new ArrayList<>();
        List<Option> optionList = optionDummyList(productDummyList());
        cartList.add(newCart(1, newUser("gihae0805"), optionList.get(0), 10));
        cartList.add(newCart(2, newUser("gihae0805"), optionList.get(1), 10));

        CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(cartList);
        Mockito.when(cartService.update(any())).thenReturn(responseDTO);
        System.out.println("responseDTO=" + responseDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        System.out.println("result=" + result);
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].cartId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(100000));
    }

    @WithMockUser(username = "gihae0805@nate.com", roles = "USER")
    @Test
    @DisplayName("장바구니 수정 실패 - 최소 수량 미달")
    void update_fail_test1() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO req1 = new CartRequest.UpdateDTO();
        req1.setCartId(1);
        req1.setQuantity(-1); //10에서 -1로 수정

        CartRequest.UpdateDTO req2 = new CartRequest.UpdateDTO();
        req2.setCartId(2);
        req2.setQuantity(10);

        requestDTOs.add(req1);
        requestDTOs.add(req2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //stub - 필요하지 않음

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("수량은 최소 1 이상 이어야 합니다."));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }

    @WithMockUser(username = "gihae0805@nate.com", roles = "USER")
    @Test
    @DisplayName("장바구니 수정 실패 - 최대 수량 초과")
    void update_fail_test2() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO req1 = new CartRequest.UpdateDTO();
        req1.setCartId(1);
        req1.setQuantity(1000); //10에서 1000으로 수정

        CartRequest.UpdateDTO req2 = new CartRequest.UpdateDTO();
        req2.setCartId(2);
        req2.setQuantity(10);

        requestDTOs.add(req1);
        requestDTOs.add(req2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //stub - 필요하지 않음

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("최대 수량은 100개를 넘을 수 없습니다."));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }

    @WithMockUser(username = "gihae0805@nate.com", roles = "USER")
    @Test
    @DisplayName("장바구니 수정 실패 - 카트 없음")
    void update_fail_test3() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO req1 = new CartRequest.UpdateDTO();
        req1.setQuantity(1); //cart id 정보 누락

        CartRequest.UpdateDTO req2 = new CartRequest.UpdateDTO();
        req2.setCartId(2);
        req2.setQuantity(10);

        requestDTOs.add(req1);
        requestDTOs.add(req2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //stub - 필요하지 않음

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("장바구니 아이디는 필수 입력 값입니다."));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }
}
