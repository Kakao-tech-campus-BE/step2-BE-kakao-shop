package com.example.kakao.cart;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.LinkedList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({
        FakeStore.class,
        SecurityConfig.class,
        CartRequest.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {
    static LinkedList<CartRequest.SaveDTO> body = new LinkedList<>();

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper om;


    @BeforeEach
    void 환경세팅() {
        body.add(new CartRequest.SaveDTO(4, 10));
        body.add(new CartRequest.SaveDTO(5, 10));
    }


    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void update_test() throws Exception {
        String bodyJson = om.writeValueAsString(body);
        mvc.perform(
                        MockMvcRequestBuilders
                                .post("/carts/update")
                                .content(bodyJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response").hasJsonPath())
                .andExpect(jsonPath("$.response.carts").isArray())
                .andExpect(jsonPath("$.response.carts[0].cartId").hasJsonPath())
                .andExpect(jsonPath("$.response.carts[0].optionId").hasJsonPath())
                .andExpect(jsonPath("$.response.carts[0].optionName").hasJsonPath())
                .andExpect(jsonPath("$.response.carts[0].quantity").hasJsonPath())
                .andExpect(jsonPath("$.response.carts[0].price").hasJsonPath())
                .andExpect(jsonPath("$.response.totalPrice").hasJsonPath())
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void show_test() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .get("/carts")
//                                .header("Authorization", "Bearer (accessToken)")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response").hasJsonPath())
                .andExpect(jsonPath("$.response.products").isArray())
                .andExpect(jsonPath("$.response.products[0].id").hasJsonPath())
                .andExpect(jsonPath("$.response.products[0].productName").hasJsonPath())
                .andExpect(jsonPath("$.response.products[0].carts").isArray())
                .andExpect(jsonPath("$.response.products[0].carts[0].id").hasJsonPath())
                .andExpect(jsonPath("$.response.products[0].carts[0].option").hasJsonPath())
                .andExpect(jsonPath("$.response.products[0].carts[0].option.id").hasJsonPath())
                .andExpect(jsonPath("$.response.products[0].carts[0].option.optionName").hasJsonPath())
                .andExpect(jsonPath("$.response.products[0].carts[0].quantity").hasJsonPath())
                .andExpect(jsonPath("$.response.products[0].carts[0].price").hasJsonPath())
                .andExpect(jsonPath("$.response.totalPrice").hasJsonPath())
                .andExpect(jsonPath("$.error").isEmpty());
    }
}
