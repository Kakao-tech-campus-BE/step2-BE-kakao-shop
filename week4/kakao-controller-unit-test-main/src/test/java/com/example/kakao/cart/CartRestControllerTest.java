package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
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

import static org.mockito.ArgumentMatchers.any;

@Import({
        FakeStore.class,
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private UserService userService;

    @MockBean
    private CartService cartService;

    @Autowired
    private FakeStore fakeStore;

    String jwt = "";

    @BeforeEach
    public void setup(){
        User user = User.builder().id(1).username("ssar").email("ssar@nate.com").roles("ROLE_USER").build();
        jwt = JWTProvider.create(user);
    }

    @Order(1)
    @DisplayName("카트 조회 테스트")
    @Test
    public void cart_find_test() throws Exception {
        // given
        CartResponse.FindAllDTO expectedCart = new CartResponse.FindAllDTO(fakeStore.getCartList());

        // stub
        Mockito.when(userService.login(any())).thenReturn(jwt);
        Mockito.when(cartService.findAll(any())).thenReturn(expectedCart);
        System.out.println(expectedCart.getTotalPrice());
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
                        .header("Authorization", "Bearer " + jwt)
                        .accept(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("카트 조회 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(104500));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.id").value(1));

    }

    @Order(2)
    @DisplayName("카트 추가 테스트")
    @Test
    public void cart_add_test() throws Exception {
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
        System.out.println("카트 추가 테스트 : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .header("Authorization", "Bearer " + jwt)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("카트 추가 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
    @DisplayName("카트 추가 실패 테스트 - 카트가 중복된 경우")
    @Test
    public void cart_add_fail_duplicated_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(2);
        d1.setQuantity(5);

        CartRequest.SaveDTO d2 = new CartRequest.SaveDTO();
        d2.setOptionId(2);
        d2.setQuantity(5);
        requestDTOs.add(d1);
        requestDTOs.add(d2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("카트 추가 실패 테스트 : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .header("Authorization", "Bearer " + jwt)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("카트 추가 실패 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("장바구니는 중복되면 안됩니다."));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }
    @DisplayName("카트 추가 실패 테스트 - 카트 담은 양이 1일 경우")
    @Test
    public void cart_add_fail_quantity0_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(1);
        d1.setQuantity(0);

        CartRequest.SaveDTO d2 = new CartRequest.SaveDTO();
        d2.setOptionId(2);
        d2.setQuantity(5);
        requestDTOs.add(d1);
        requestDTOs.add(d2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("카트 추가 실패 테스트 : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .header("Authorization", "Bearer " + jwt)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("카트 추가 실패 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("장바구니의 수량은 1이상이어야 합니다."));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }
    @DisplayName("카트 추가 실패 테스트 - 카트 id가 잘못된 경우")
    @Test
    public void cart_add_fail_wrongID_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(123);
        d1.setQuantity(5);

        CartRequest.SaveDTO d2 = new CartRequest.SaveDTO();
        d2.setOptionId(2);
        d2.setQuantity(5);
        requestDTOs.add(d1);
        requestDTOs.add(d2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("카트 추가 실패 테스트 : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .header("Authorization", "Bearer " + jwt)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("카트 추가 실패 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("잘못된 장바구니 ID입니다."));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));
    }
    @Order(3)
    @DisplayName("카트 삭제 테스트")
    @Test
    public void cart_delete_test() throws Exception {
        // given

        // stub
        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/clear")
                        .header("Authorization", "Bearer " + jwt)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("카트 삭제 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Order(4)
    @DisplayName("카트 업데이트 테스트")
    @Test
    public void cart_update_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO d1 = new CartRequest.UpdateDTO();
        d1.setCartId(1);
        d1.setQuantity(10); // original: 5
        CartRequest.UpdateDTO d2 = new CartRequest.UpdateDTO();
        d2.setCartId(2);
        d2.setQuantity(10); // original: 5
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("카트 업데이트 테스트 : "+requestBody);

        // stub
        List<Cart> cartList = new ArrayList<>(fakeStore.getCartList());
        cartList.get(0).update(10, 1000000);
        cartList.get(1).update(10, 1000000);

        CartResponse.UpdateDTO responseDTOs = new CartResponse.UpdateDTO(cartList);

        Mockito.when(cartService.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(responseDTOs);
        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .header("Authorization", "Bearer " + jwt)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("카트 업데이트 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].cartId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(100000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[1].cartId").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
    }
    @DisplayName("카트 업데이트 실패 테스트 - 카트가 중복된 경우")
    @Test
    public void cart_update_fail_duplicated_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO d1 = new CartRequest.UpdateDTO();
        d1.setCartId(2);
        d1.setQuantity(10); // original: 5
        CartRequest.UpdateDTO d2 = new CartRequest.UpdateDTO();
        d2.setCartId(2);
        d2.setQuantity(10); // original: 5
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("카트 업데이트 실패 테스트 : "+requestBody);

        // stub
        List<Cart> cartList = new ArrayList<>(fakeStore.getCartList());
        cartList.get(0).update(10, 1000000);
        cartList.get(1).update(10, 1000000);

        CartResponse.UpdateDTO responseDTOs = new CartResponse.UpdateDTO(cartList);

        Mockito.when(cartService.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(responseDTOs);
        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .header("Authorization", "Bearer " + jwt)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("카트 업데이트 실패 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("장바구니는 중복되면 안됩니다."));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }
    @DisplayName("카트 업데이트 실패 테스트 - 카트 담은 양이 0일 경우")
    @Test
    public void cart_update_fail_quantity0_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO d1 = new CartRequest.UpdateDTO();
        d1.setCartId(1);
        d1.setQuantity(0); // original: 5
        CartRequest.UpdateDTO d2 = new CartRequest.UpdateDTO();
        d2.setCartId(2);
        d2.setQuantity(10); // original: 5
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("카트 업데이트 실패 테스트 : "+requestBody);

        // stub
        List<Cart> cartList = new ArrayList<>(fakeStore.getCartList());
        cartList.get(0).update(10, 1000000);
        cartList.get(1).update(10, 1000000);

        CartResponse.UpdateDTO responseDTOs = new CartResponse.UpdateDTO(cartList);

        Mockito.when(cartService.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(responseDTOs);
        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .header("Authorization", "Bearer " + jwt)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("카트 업데이트 실패 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("장바구니의 수량은 1이상이어야 합니다."));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
    }
    @DisplayName("카트 업데이트 실패 테스트- 카트 id가 잘못된 경우")
    @Test
    public void cart_update_fail_wrongID_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO d1 = new CartRequest.UpdateDTO();
        d1.setCartId(123);
        d1.setQuantity(5); // original: 5
        CartRequest.UpdateDTO d2 = new CartRequest.UpdateDTO();
        d2.setCartId(2);
        d2.setQuantity(10); // original: 5
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("카트 업데이트 실패 테스트 : "+requestBody);

        // stub
        List<Cart> cartList = new ArrayList<>(fakeStore.getCartList());
        cartList.get(0).update(10, 1000000);
        cartList.get(1).update(10, 1000000);

        CartResponse.UpdateDTO responseDTOs = new CartResponse.UpdateDTO(cartList);

        Mockito.when(cartService.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(responseDTOs);
        Mockito.when(userService.login(any())).thenReturn(jwt);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .header("Authorization", "Bearer " + jwt)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("카트 업데이트 실패 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("잘못된 장바구니 ID입니다."));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));
    }
}
