package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("주문_주문상품_컨트롤러_테스트")
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest extends DummyEntity {
    private final ObjectMapper om;
    private final MockMvc mvc;

    private List<Order> orderList = mock.getOrderList();
    private List<Item> itemList = mock.getItemList();

    @MockBean
    private FakeStore fakeStore;
    @MockBean
    private OrderService orderService;
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    public OrderRestControllerTest(ObjectMapper om, MockMvc mvc) {
        this.om = om;
        this.mvc = mvc;
    }

    @DisplayName("주문_저장_mock_성공_테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void order_save_mock_test() throws Exception {
        // given

        // when
        Mockito.when(fakeStore.getOrderList()).thenReturn(orderList);
        Mockito.when(fakeStore.getItemList()).thenReturn(itemList);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders-mock/save")
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(54500));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(104500));
        resultActions.andExpect(jsonPath("$.error").value(Matchers.nullValue()));
    }

    @DisplayName("주문_결과_확인_mock_성공_테스트")
    @WithMockUser(username="ssar@nate.com", roles="USER")
    @Test
    public void order_findById_mock_test() throws Exception {
        // given
        int id = 1;

        // when
        Mockito.when(fakeStore.getOrderList()).thenReturn(orderList);
        Mockito.when(fakeStore.getItemList()).thenReturn(itemList);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders-mock/" + id)
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(54500));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(104500));
        resultActions.andExpect(jsonPath("$.error").value(Matchers.nullValue()));
    }

    @DisplayName("주문_결과_확인_mock_실패_테스트_데이터_없음")
    @WithMockUser(username="ssar@nate.com", roles="USER")
    @Test
    public void order_findById_mock_fail_test_no_data() throws Exception {
        // given
        int id = 100;

        // when
        Mockito.when(fakeStore.getOrderList()).thenReturn(orderList);
        Mockito.when(fakeStore.getItemList()).thenReturn(itemList);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders-mock/" + id)
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(Matchers.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 주문을 찾을 수 없습니다:"+id));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
    }

    // ========================== real ==========================

    @DisplayName("주문_저장_성공_테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void order_save_test() throws Exception {
        // given

        // when
        Order order = orderList.get(0);
        OrderResponse.FindByIdDTO resultDTO = new OrderResponse.FindByIdDTO(order, itemList);
        Mockito.when(orderService.create(any())).thenReturn(resultDTO);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(54500));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(104500));
        resultActions.andExpect(jsonPath("$.error").value(Matchers.nullValue()));
    }

    @DisplayName("주문_결과_확인_성공_테스트")
    @WithMockUser(username="ssar@nate.com", roles="USER")
    @Test
    public void order_findById_test() throws Exception {
        // given
        int id = 1;

        // when
        Order order = orderList.get(0);
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
        Mockito.when(orderService.getOrderAndItems(anyInt())).thenReturn(responseDTO);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/" + id)
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(54500));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(104500));
        resultActions.andExpect(jsonPath("$.error").value(Matchers.nullValue()));
    }

    @DisplayName("주문_결과_확인_실패_테스트_데이터_없음")
    @WithMockUser(username="ssar@nate.com", roles="USER")
    @Test
    public void order_findById_fail_stub_test_no_data() throws Exception {
        // given
        int id = 100;

        // when
        Mockito.when(orderService.getOrderAndItems(id)).thenThrow(new Exception404("해당 주문을 찾을 수 없습니다:" + id));
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/" + id)
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(Matchers.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 주문을 찾을 수 없습니다:"+id));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
    }
}
