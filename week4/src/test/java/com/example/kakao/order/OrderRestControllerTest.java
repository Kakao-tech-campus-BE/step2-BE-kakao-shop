package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.user.UserService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest
public class OrderRestControllerTest extends DummyEntity {

    @MockBean
    private FakeStore fakeStore;

    @MockBean
    private UserService userService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @DisplayName("(기능12)결제_test")
    @WithMockUser(username="ssar@nate.com", roles = "USER")
    @Test
    public void save_test() throws Exception {

        // given

        // stub
        Mockito.when(fakeStore.getOrderList()).thenReturn(orderList);
        Mockito.when(fakeStore.getItemList()).thenReturn(itemList);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        ).andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @DisplayName("(기능13)주문_결과_확인_test")
    @WithMockUser(username="ssar@nate.com", roles = "USER")
    @Test
    public void findById_test() throws Exception {

        // given
        int id = 1;

        // stub
        Mockito.when(fakeStore.getOrderList()).thenReturn(orderList);
        Mockito.when(fakeStore.getItemList()).thenReturn(itemList);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+id)
        ).andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(id));
    }

    @DisplayName("(기능13)주문_결과_확인_실패_test")
    @WithMockUser(username="ssar@nate.com", roles = "USER")
    @Test
    public void findById_fail_test() throws Exception {

        // given
        int id = 1000;

        // stub
        Mockito.when(fakeStore.getOrderList()).thenReturn(orderList);
        Mockito.when(fakeStore.getItemList()).thenReturn(itemList);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+id)
        ).andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("해당 주문을 찾을 수 없습니다:"+id));
    }
}
