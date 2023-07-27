package com.example.kakao.cart;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.IsNull;
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

import java.util.ArrayList;
import java.util.List;


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

    @Autowired
    FakeStore fakeStore;

    @MockBean
    CartRequest.UpdateDTO d1;

    @MockBean
    CartRequest.SaveDTO mockSaveDTO1;


    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void update_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO d2 = new CartRequest.UpdateDTO();
        d2.setCartId(2);
        d2.setQuantity(10);

        // Mockito로 stub 구현
        // stub
        Mockito.when(d1.getCartId()).thenReturn(2);
        Mockito.when(d1.getQuantity()).thenReturn(15);
        d2.setCartId(d1.getCartId());
        d2.setQuantity(d1.getQuantity());
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);



        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].cartId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(50000));
    }

    // 장바구니 담기
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void addCartTest() throws Exception {
        // given
        CartRequest cartRequest = new CartRequest();
        List<CartRequest.SaveDTO> saveDTOList = new ArrayList<>();
        CartRequest.SaveDTO saveDTO = new CartRequest.SaveDTO();
        CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();

        // Mockito로 stub 구현
        // stub
        Mockito.when(mockSaveDTO1.getOptionId()).thenReturn(1);
        Mockito.when(mockSaveDTO1.getQuantity()).thenReturn(5);
        saveDTO.setOptionId(mockSaveDTO1.getOptionId());
        saveDTO.setQuantity(mockSaveDTO1.getQuantity());
        Mockito.when(mockSaveDTO1.getOptionId()).thenReturn(2);
        Mockito.when(mockSaveDTO1.getQuantity()).thenReturn(8);
        saveDTO2.setOptionId(mockSaveDTO1.getOptionId());
        saveDTO2.setQuantity(mockSaveDTO1.getQuantity());

        saveDTOList.add(saveDTO);
        saveDTOList.add(saveDTO2);
        String requestBody = om.writeValueAsString(saveDTOList);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").value(IsNull.nullValue()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(IsNull.nullValue()));


    }


    // /carts/clear
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void clearCarts() throws Exception {
        // given

        // stub

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/clear")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").value(IsNull.nullValue()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(IsNull.nullValue()));


        // then


    }


    // 장바구니 보기
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void showCarts() throws Exception {
        // given

        // stub

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value("1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value("1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.id").value("1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.price").value("10000"));




    }
}
