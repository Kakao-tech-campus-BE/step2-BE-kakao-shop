package com.example.kakao.cart;

import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerMockTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FakeStore fakeStore;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private CartService cartService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;


    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test//findAll() 성공 테스트
    public void findAll_test() throws Exception{
        //given

        //stub
        Mockito.when(cartService.findAll()).thenReturn(new CartResponse.FindAllDTO(fakeStore.getCartList()));

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.price").value(10000));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test // addCartList() 성공 테스트
    public void add_test() throws Exception{
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO saveDTO1 = new CartRequest.SaveDTO();
        CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();
        saveDTO1.setOptionId(1);
        saveDTO1.setQuantity(5);
        saveDTO2.setOptionId(2);
        saveDTO2.setQuantity(5);
        requestDTOs.add(saveDTO1);
        requestDTOs.add(saveDTO2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test // addCartList() 실패 테스트
    public void add_wrong_quantity_test() throws Exception{
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO saveDTO1 = new CartRequest.SaveDTO();
        CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();
        saveDTO1.setOptionId(1);
        saveDTO1.setQuantity(-5);
        saveDTO2.setOptionId(2);
        saveDTO2.setQuantity(-1);
        requestDTOs.add(saveDTO1);
        requestDTOs.add(saveDTO2);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpectAll(
                status().is4xxClientError(), // 잘못된 수량, 400 오류 확인
                MockMvcResultMatchers.jsonPath("$.success").value("false")
        );
    }

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

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].cartId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(100000));
    }
}
