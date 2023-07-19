package com.example.kakao.cart;

import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsNull.nullValue;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

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
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        HttpStatus status = HttpStatus.valueOf(result.andReturn().getResponse().getStatus());
        String errorMessage = result.andReturn().getResponse().getErrorMessage();
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 Status: " + status);
        System.out.println("테스트 Error Message: " + errorMessage);
        System.out.println("테스트 Response Body: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].cartId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(100000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void add_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(1);
        d1.setQuantity(10);
        CartRequest.SaveDTO d2 = new CartRequest.SaveDTO();
        d2.setOptionId(2);
        d2.setQuantity(10);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        HttpStatus status = HttpStatus.valueOf(result.andReturn().getResponse().getStatus());
        String errorMessage = result.andReturn().getResponse().getErrorMessage();
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 Status: " + status);
        System.out.println("테스트 Error Message: " + errorMessage);
        System.out.println("테스트 Response Body: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findAll_test() throws Exception {
        // given


        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/carts")
        );

        HttpStatus status = HttpStatus.valueOf(result.andReturn().getResponse().getStatus());
        String errorMessage = result.andReturn().getResponse().getErrorMessage();
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 Status: " + status);
        System.out.println("테스트 Error Message: " + errorMessage);
        System.out.println("테스트 Response Body: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].price").value(10000*5));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].option.id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].price").value(10900*5));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void clear_test() throws Exception {
        // given


        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/clear")
        );

        HttpStatus status = HttpStatus.valueOf(result.andReturn().getResponse().getStatus());
        String errorMessage = result.andReturn().getResponse().getErrorMessage();
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 Status: " + status);
        System.out.println("테스트 Error Message: " + errorMessage);
        System.out.println("테스트 Response Body: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }
}
