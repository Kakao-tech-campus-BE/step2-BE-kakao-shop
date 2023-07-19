package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.ArgumentMatchers.any;

@Import({
        FakeStore.class,
        GlobalExceptionHandler.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @MockBean OrderItemService orderItemService;
    private List<Order> mockorderList;
    private List<Item> mockItemList;

    @BeforeEach
    public void setUp() {
        FakeStore fakeStore = new FakeStore();
        mockorderList = fakeStore.getOrderList();
        mockItemList = fakeStore.getItemList();
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void save_test() throws Exception {
        //given
        int userId = 1;

        //stub
        Order order = mockorderList.stream()
                .filter(x -> x.getUser().getId() == userId).findFirst()
                .orElseThrow(
                        () -> new Exception404("해당 유저에 대한 주문을 찾을 수 없습니다")
                );

        List<Item> itemList = mockItemList.stream()
                .filter(x -> x.getOrder().getId() == order.getId())
                .collect(Collectors.toList());

       OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);

        Mockito.when(orderItemService.saveOrder(any())).thenReturn(responseDTO);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
}
