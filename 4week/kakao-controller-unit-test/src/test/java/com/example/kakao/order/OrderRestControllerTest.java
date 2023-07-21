package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.ProductRestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;


@Import({
        FakeStore.class,
        GlobalExceptionHandler.class

})
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {OrderRestController.class})
@WithMockUser(username = "ssar@nate.com", roles = "USER")
public class OrderRestControllerTest {

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FakeStore fakeStore;

    @Autowired
    private ObjectMapper om;

    @DisplayName("주문 결제 테스트(구현하지못함)")
    @Test
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    public void testSaveOrder() throws Exception {

        //어떻게 구현하여야지 Controller 단위테스트에 맞는 구현 방법인지 확실하지 않습니다.

//        when(fakeStore.getOrderList()).thenReturn(Arrays.asList(mockOrder));
//        when(fakeStore.getItemList()).thenReturn(mockItemList);
//
//        ResultActions result = mvc.perform(
//                MockMvcRequestBuilders
//                        .post("/orders/save")
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//        String responseBody = result.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트: " + responseBody);
//
//        // then
//        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }


    @DisplayName("모든 주문 조회 테스트")
    @Test
    public void getAllOrders_test() throws Exception {
        //컨트롤러에서는 유저의 id 를 받아와서 해당 유저가 가지고 있는 모든 주문을 가져오는 테스트는 힘든지 궁금합니다.
    }

    @DisplayName("단일 주문 조회 테스트")
    @Test
    public void getOrderById_test() throws Exception {
        // given
        int orderId = 1;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+ orderId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
    }

    @DisplayName("단일 제품 조회에러 테스트")
    @Test
    public void getProductById_404_test() throws Exception {
        // given
        // 올바르지 않은 id 형식
        int orderId = -1;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+ orderId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("해당 주문을 찾을 수 없습니다:"+orderId));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value("404"));
    }

}
