package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.ProductRestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    // @WebMvcTest를 하면 MockMvc가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private MockMvc mvc;

    // @WebMvcTest를 하면 ObjectMapper가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private ObjectMapper om;
    @DisplayName("장바구니 확인하기 테스트")
    @Test
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    public void findAllCarts_test() throws Exception {
        //given
        //실제 서비스처럼 유저 아이디를 설정하여 메서드를 만드려 하였으나 레포, 서비스단을 사용불가하기에 단순한 컨트롤러 테스트만 작성했다.
//        int userId = 1;
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[1].quantity").value(10));

    }

    @DisplayName("장바구니 업데이트 테스트")
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

    @DisplayName("장바구니 업데이트에러 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void update_error_test() throws Exception {
        // given
        // 올바르지 않은 타입 형식
        // DefaultHandlerExceptionResolver에서 먼저 처리되는 모습을 보여주었다.
        String invalidRequestBody = "[{ \"cartId\": null, \"quantity\": \"abc\" }]";
        System.out.println("테스트 : "+invalidRequestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(invalidRequestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        //단순한 400에러 캐치는 ok, 하지만 Controller에서 발생시킨 Exception이 아니다.
        // 강사님께 질문을 드렸었는데
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @DisplayName("장바구니 추가 테스트")
    @Test
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    public void addCartList_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOList = new ArrayList();
        CartRequest.SaveDTO item1 = new CartRequest.SaveDTO();
        item1.setOptionId(1);
        item1.setQuantity(5);
        CartRequest.SaveDTO item2 = new CartRequest.SaveDTO();
        item2.setOptionId(2);
        item2.setQuantity(5);
        requestDTOList.add(item1);
        requestDTOList.add(item2);

        String requestBody = this.om.writeValueAsString(requestDTOList);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }


}
