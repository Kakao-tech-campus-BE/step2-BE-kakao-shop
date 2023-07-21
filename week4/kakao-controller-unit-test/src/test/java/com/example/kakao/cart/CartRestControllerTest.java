package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@DisplayName("장바구니_컨트롤러_테스트")
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest extends DummyEntity{
    private final MockMvc mvc;
    private final ObjectMapper om;
    private List<Cart> cartList = mock.getCartList();

    @Autowired
    public CartRestControllerTest(MockMvc mockMvc, ObjectMapper om) {
        this.mvc = mockMvc;
        this.om = om;
    }

    @MockBean
    private CartService cartService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @MockBean
    private FakeStore fakeStore;

    @DisplayName("장바구니_추가_mock_컨트롤러_테스트_성공")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void add_mock_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDtos = new ArrayList<>();
        CartRequest.SaveDTO c1 = new CartRequest.SaveDTO();
        c1.setOptionId(3);
        c1.setQuantity(5);
        CartRequest.SaveDTO c2 = new CartRequest.SaveDTO();
        c2.setOptionId(4);
        c2.setQuantity(3);
        requestDtos.add(c1);
        requestDtos.add(c2);

        // when
        Mockito.when(fakeStore.getCartList()).thenReturn(cartList);
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts-mock/add")
                        .content(om.writeValueAsString(requestDtos))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpectAll(
            status().isOk(),
            jsonPath("$.success").value("true"),
            jsonPath("$.response").value(Matchers.nullValue()),
            jsonPath("$.error").value(Matchers.nullValue())
        );
    }


    @DisplayName("장바구니_조회_mock_컨트롤러_테스트_성공")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findAll_mock_test() throws Exception {
        // given

        // when
        Mockito.when(fakeStore.getCartList()).thenReturn(cartList);
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts-mock")
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpectAll(
            status().isOk(),
            jsonPath("$.success").value("true"),
            jsonPath("$.response.products[0].id").value(1),
            jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"),
            jsonPath("$.response.products[0].carts[0].id").value(1),
            jsonPath("$.response.products[0].carts[0].option.id").value(1),
            jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"),
            jsonPath("$.response.products[0].carts[0].option.price").value(10000),
            jsonPath("$.response.products[0].carts[0].quantity").value(5),
            jsonPath("$.response.products[0].carts[0].price").value(50000),
            jsonPath("$.response.products[0].carts[1].id").value(2),
            jsonPath("$.response.products[0].carts[1].option.id").value(2),
            jsonPath("$.response.products[0].carts[1].option.optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"),
            jsonPath("$.response.products[0].carts[1].option.price").value(10900),
            jsonPath("$.response.products[0].carts[1].quantity").value(5),
            jsonPath("$.response.products[0].carts[1].price").value(54500),
            jsonPath("$.response.totalPrice").value(104500),
            jsonPath("$.error").value(Matchers.nullValue())
        );
    }

    @DisplayName("장바구니_수정_mock_컨트롤러_테스트_성공")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void update_mock_test() throws Exception {
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
        Mockito.when(fakeStore.getCartList()).thenReturn(cartList);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts-mock/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpectAll(
            status().isOk(),
            jsonPath("$.success").value("true"),
            jsonPath("$.response.carts[0].cartId").value(1),
            jsonPath("$.response.carts[0].optionId").value(1),
            jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"),
            jsonPath("$.response.carts[0].quantity").value(10),
            jsonPath("$.response.carts[0].price").value(100000),
            jsonPath("$.error").value(Matchers.nullValue())
        );
    }

    @DisplayName("장바구니_초기화_mock_테스트_성공")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void clear_mock_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts-mock/clear")
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpectAll(
                jsonPath("$.success").value("true"),
                jsonPath("$.response").value(Matchers.nullValue()),
                jsonPath("$.error").value(Matchers.nullValue())
        );
    }

    // ========================== real ==========================
    // 장바구니 추가의 경우 반환하는 데이터가 없으므로 추가 구현이 불필요하다.

    @DisplayName("장바구니_추가_컨트롤러_테스트_성공")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void add_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDtos = new ArrayList<>();
        CartRequest.SaveDTO c1 = new CartRequest.SaveDTO();
        c1.setOptionId(3);
        c1.setQuantity(5);
        CartRequest.SaveDTO c2 = new CartRequest.SaveDTO();
        c2.setOptionId(4);
        c2.setQuantity(3);
        requestDtos.add(c1);
        requestDtos.add(c2);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(om.writeValueAsString(requestDtos))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.success").value("true"),
                jsonPath("$.response").value(Matchers.nullValue()),
                jsonPath("$.error").value(Matchers.nullValue())
        );
    }

    @DisplayName("장바구니_조회_테스트_성공")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findAll_test() throws Exception {
        // given

        // when
        CartResponse.FindAllDTO findAllDTO = new CartResponse.FindAllDTO(cartList);
        Mockito.when(cartService.getCartLists(any())).thenReturn(findAllDTO);
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpectAll(
            status().isOk(),
            jsonPath("$.success").value("true"),
            jsonPath("$.response.products[0].id").value(1),
            jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"),
            jsonPath("$.response.products[0].carts[0].id").value(1),
            jsonPath("$.response.products[0].carts[0].option.id").value(1),
            jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"),
            jsonPath("$.response.products[0].carts[0].option.price").value(10000),
            jsonPath("$.response.products[0].carts[0].quantity").value(5),
            jsonPath("$.response.products[0].carts[0].price").value(50000),
            jsonPath("$.response.products[0].carts[1].id").value(2),
            jsonPath("$.response.products[0].carts[1].option.id").value(2),
            jsonPath("$.response.products[0].carts[1].option.optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"),
            jsonPath("$.response.products[0].carts[1].option.price").value(10900),
            jsonPath("$.response.products[0].carts[1].quantity").value(5),
            jsonPath("$.response.products[0].carts[1].price").value(54500),
            jsonPath("$.response.totalPrice").value(104500),
            jsonPath("$.error").value(Matchers.nullValue())
        );
    }

    @DisplayName("장바구니_수정_컨트롤러_테스트_성공")
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
        cartList.get(0).update(10, 10*10000);
        cartList.get(1).update(10, 10*10900);
        CartResponse.UpdateDTO updateDTO = new CartResponse.UpdateDTO(cartList);
        Mockito.when(cartService.update(any())).thenReturn(updateDTO);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andDo(print());

        // then
        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.success").value("true"),
                jsonPath("$.response.carts[0].cartId").value(1),
                jsonPath("$.response.carts[0].optionId").value(1),
                jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"),
                jsonPath("$.response.carts[0].quantity").value(10),
                jsonPath("$.response.carts[0].price").value(100000),
                jsonPath("$.response.carts[1].cartId").value(2),
                jsonPath("$.response.carts[1].optionId").value(2),
                jsonPath("$.response.carts[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"),
                jsonPath("$.response.carts[1].quantity").value(10),
                jsonPath("$.response.carts[1].price").value(109000),
                jsonPath("$.response.totalPrice").value(209000),
                jsonPath("$.error").value(Matchers.nullValue())
        );
    }

    @DisplayName("장바구니_초기화_테스트_성공")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void clear_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/clear")
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpectAll(
                jsonPath("$.success").value("true"),
                jsonPath("$.response").value(Matchers.nullValue()),
                jsonPath("$.error").value(Matchers.nullValue())
        );
    }
}
