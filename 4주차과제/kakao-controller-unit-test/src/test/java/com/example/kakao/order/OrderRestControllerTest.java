package com.example.kakao.order;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@Import({
        SecurityConfig.class,
        FakeStore.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FakeStore fakeStore;


    @Test
    @WithMockUser(username = "ssar@nate.com",roles = "USER")
    public void save_test() throws Exception{
        //given
        Order order = fakeStore.getOrderList().get(0);
        List<Item> items = fakeStore.getItemList();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, items);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String res = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("res : " + res);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(responseDTO.getId()));

    }

    @Test
    @WithMockUser(username = "ssar@nate.com",roles = "USER")
    public void findById_test() throws Exception{

        //given
        int id = 1;
        Order order = fakeStore.getOrderList().get(id-1);
        List<Item> items = fakeStore.getItemList();
        OrderResponse.FindByIdDTO dto = new OrderResponse.FindByIdDTO(order, items);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String res = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("res : " + res);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(dto.getId()));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value(dto.getProducts().get(0).getProductName()));
    }


}
