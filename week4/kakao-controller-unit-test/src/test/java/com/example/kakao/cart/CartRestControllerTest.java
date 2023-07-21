package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
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

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({
        FakeStore.class,
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {

    @Autowired
    private MockMvc mvc;//

    @Autowired
    private ObjectMapper om;//직렬화에 사용할 object mapper

    @Autowired
    private FakeStore fakeStore;

    @Autowired

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @MockBean
    private CartService cartService;

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
        //가짜결과
        CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(fakeStore.getCartList());

        // stub - update서비스가 실행된다면 주문내역을 찾는 쿼리를 실행하고 결과값을 반환할 것입니다.
        Mockito.when(cartService.update(anyList())).thenReturn(responseDTO);

        System.out.println("request테스트 : "+requestBody);
        System.out.println("response테스트 : "+responseDTO);
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
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(50000));
    }

    //update쿼리를 요청할 때 사용자 쪽에서 cart의 id와 수량의 변경을 보내오는데 user와 cart는 일대다 관계이기 때문에 user id로 cart를 찾았을 때, 존재하지 않는
    //카트의 변경을 요청한다면 오류로 처리해야 한다.
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void badrequest_cart_update_test() throws Exception{
        // given
        List<String> requestDTOs = new ArrayList<>();
        requestDTOs.add("이상한 요청");
        requestDTOs.add("쓰레기 요청");
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);
        //가짜결과
        CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(fakeStore.getCartList());

        // stub - update서비스가 실행된다면 주문내역을 찾는 쿼리를 실행하고 결과값을 반환할 것입니다.
        Mockito.when(cartService.update(anyList())).thenReturn(responseDTO);

        System.out.println("request테스트 : "+requestBody);
        System.out.println("response테스트 : "+responseDTO);
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
        result.andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void cart_addCartList_test() throws Exception {

        // given
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
        System.out.println("테스트 : "+requestBody);

        // stub - addCartList서비스가 실행된다면 주문내역을 찾는 쿼리를 실행하고 결과값을 반환할 것입니다.
        Mockito.when(cartService.addCartList(requestDTOs)).thenReturn(0);
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
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void badrequest_cart_addCartList_test() throws Exception {
        // given
        List<String> requestDTOs = new ArrayList<>();
        requestDTOs.add("이상한 요청");
        requestDTOs.add("쓰레기 요청");
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        // stub - addCartList서비스가 실행된다면 주문내역을 찾는 쿼리를 실행하고 결과값을 반환할 것입니다.
//        Mockito.when(anyList()).thenThrow(new Exception400("존재하지 않는 옵션이 포함되어 있습니다."));

        // when

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isBadRequest());


    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void cart_findAll_test()throws Exception {
        //given
        List<Cart> cartList = fakeStore.getCartList();
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartList);
        // stub - findById서비스가 실행된다면 주문내역을 찾는 쿼리를 실행하고 결과값을 반환할 것입니다.
        Mockito.when(cartService.findAll()).thenReturn(responseDTO);
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.price").value(10000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(5));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void cart_clear_test()throws Exception {
        // stub - findById서비스가 실행된다면 주문내역을 찾는 쿼리를 실행하고 결과값을 반환할 것입니다.
        Mockito.when(cartService.clear()).thenReturn(0);
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/clear")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }

    @Test
    public void anonymous_update_test() throws Exception {
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
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("인증되지 않았습니다"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(401));
    }

    @Test
    public void anonymous_cart_addCartList_test() throws Exception {
        // given
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
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("인증되지 않았습니다"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(401));
    }

    @Test
    public void anonymous_cart_findAll_test()throws Exception {
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("인증되지 않았습니다"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(401));
    }

    @Test
    public void anonymous_cart_clear_test()throws Exception {
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/clear")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("인증되지 않았습니다"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(401));
    }
}
