package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
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

import java.util.List;
import java.util.stream.Collectors;

import static com.example.kakao._core.utils.PrintUtils.getPrettyString;

@Import({
        FakeStore.class,
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {

    @MockBean
    private OrderService orderService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FakeStore fakeStore;

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    @DisplayName("주문 추가 테스트")
    public void save_test() throws Exception {
        // given

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );

        System.out.println("========================================responseBody 시작=========================================");
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(getPrettyString(responseBody));
        System.out.println("========================================responseBody 종료=========================================");

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    @DisplayName("특정 주문 조회 테스트")
    public void findById_test() throws Exception {
        // given
        int id = 100;

        // 주문 조회
        Order orderPS = fakeStore.getOrderList().stream().filter(order -> order.getId() == id).findFirst().orElse(null);
        // 주문 유효성 검사
        if(orderPS == null) {
            throw new Exception404("해당 주문을 찾을 수 없습니다:"+id);
        }
        // 주문아이템 조회
        List<Item> itemListPS = fakeStore.getItemList().stream().filter(item -> orderPS.getId() == item.getOrder().getId()).collect(Collectors.toList());

        // DTO 변환
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(orderPS, itemListPS);

        // stub
        Mockito.when(orderService.findById(id) ).thenReturn(responseDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+id)
        );

        System.out.println("========================================responseBody 시작=========================================");
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(getPrettyString(responseBody));
        System.out.println("========================================responseBody 종료=========================================");

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
}
