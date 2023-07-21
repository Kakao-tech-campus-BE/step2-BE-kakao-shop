package com.example.kakao.order;

import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.CartRestController;
import com.example.kakao.cart.CartService;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    public void save_test() throws Exception {
        // given
        User user = User.builder()
                .id(1)
                .roles("ROLE_USER")
                .build();
        String jwt = JWTProvider.create(user);
        Order order = Order.builder().user(user).build();

        // when
//        Mockito.when(orderService.save(user)).thenReturn(order);

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwt));

        // then
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value("1"));
    }

    @Test
    public void findById_test() throws Exception {
        // given
        int id = 1;
        User user = User.builder()
                .id(1)
                .roles("ROLE_USER")
                .build();
        String jwt = JWTProvider.create(user);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwt));

        // then
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value("1"));
    }
}
