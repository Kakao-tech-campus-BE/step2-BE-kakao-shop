package com.example.kakao.order;
// GlobalExceptionHandler 와 UserRestController를 SpringContext에 등록합니다.

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

//order컨트롤러를 테스트하는 클래스
@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
        FakeStore.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {
    // 다른 클래스의 객체를 autowired로 쉽게 import할수있음
    @Autowired
    private MockMvc mvc;//

    @Autowired
    private ObjectMapper om;//직렬화에 사용할 object mapper

    @Autowired
    private FakeStore fakeStore;
    //에러로그 레파지토리랑 service는 테스트 범위 밖이므로 조정합니다.
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @MockBean
    private OrderService orderService;

    @WithMockUser(username = "ssar@nate.com", roles = "USER")//설정한 유저로 테스트를 진행할 수 있다.
    @Test
    public void order_save_test() throws Exception{
        // given - fakeStore의 리스트를 예시 request데이터로 하여 주문을 저장하는 컨트롤러의 테스트를 해보았습니다.
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO requestDTO = new OrderResponse.FindByIdDTO(order, itemList);
        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("테스트 : "+requestBody);
//        // stub - save서비스가 실행된다면 저장하는 레파지토리를 실행하고 결과값을 반환할 것입니다.
        Mockito.when(orderService.save(any())).thenReturn(requestDTO);
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(104500));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value(54500));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void order_findById_test() throws Exception{
        // given
        //cart에서 끌고 와 주문을 할 더미리스트
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO requestDTO = new OrderResponse.FindByIdDTO(order, itemList);
        // stub - findById서비스가 실행된다면 주문내역을 찾는 쿼리를 실행하고 결과값을 반환할 것입니다.
        Mockito.when(orderService.findById(anyInt())).thenReturn(requestDTO);
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/1")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(104500));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].price").value(54500));
    }

    //주문내역을 찾지 못했을 경우
//    @WithMockUser(username = "ssar@nate.com", roles = "USER")
//    @Test
//    public void not_found_order_findById_test() throws Exception{
//        //stub - service 레이어에서 db검증 시 404를 던진다.
//        Mockito.when(orderService.findById(anyInt())).thenThrow(new Exception404("주문내역이 존재하지 않습니다."));
//        // when
//        ResultActions result = mvc.perform(
//                MockMvcRequestBuilders
//                        .get("/orders/10000")
//        );
//
//        String responseBody = result.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 : "+responseBody);
//
//        //then
//        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
//        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
//        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("주문내역이 존재하지 않습니다."));
//        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));
//    }


    //--------------------익명 유저 테스트
    @Test
    public void anonymous_order_save_test() throws Exception {
        // given - fakeStore의 리스트를 예시 request데이터로 하여 주문을 저장하는 컨트롤러의 테스트를 해보았습니다.
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO requestDTO = new OrderResponse.FindByIdDTO(order, itemList);
        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("테스트 : " + requestBody);
//        // stub - save서비스가 실행된다면 저장하는 레파지토리를 실행하고 결과값을 반환할 것입니다.
        Mockito.when(orderService.save(any())).thenReturn(requestDTO);
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("인증되지 않았습니다"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(401));
    }

    @Test
    public void anonymous_order_findById_test() throws Exception{
        // given
        //cart에서 끌고 와 주문을 할 더미리스트
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO requestDTO = new OrderResponse.FindByIdDTO(order, itemList);
        // stub - findById서비스가 실행된다면 주문내역을 찾는 쿼리를 실행하고 결과값을 반환할 것입니다.
        Mockito.when(orderService.findById(anyInt())).thenReturn(requestDTO);
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/1")
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
