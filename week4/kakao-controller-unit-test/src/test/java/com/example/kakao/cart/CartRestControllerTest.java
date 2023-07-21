package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
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

import java.util.ArrayList;
import java.util.List;

import static com.example.kakao._core.utils.PrintUtils.getPrettyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;

@Import({
        FakeStore.class,
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {

    @MockBean
    private CartService cartService;

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
    @DisplayName("장바구니아이템 추가 테스트")
    public void addCartList_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();

        CartRequest.SaveDTO requestDTO1 = new CartRequest.SaveDTO();
        requestDTO1.setOptionId(3);
        requestDTO1.setQuantity(5);
        requestDTOs.add(requestDTO1);

        CartRequest.SaveDTO requestDTO2 = new CartRequest.SaveDTO();
        requestDTO2.setOptionId(4);
        requestDTO2.setQuantity(5);
        requestDTOs.add(requestDTO2);

        System.out.println("========================================requestBody 시작=========================================");
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println(getPrettyString(requestBody));
        System.out.println("========================================requestBody 종료=========================================");

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            // 이미 추가된 옵션 확인
            for (Cart cart : fakeStore.getCartList()) {
                if(cart.getOption().getId() == requestDTO.getOptionId()) {
                    throw new Exception400("이미 추가된 옵션입니다:" + requestDTO.getOptionId());
                }
            }
            // 옵션 유효성 검사
            boolean flag = false;
            for (Option option : fakeStore.getOptionList()) {
                if (option.getId() == requestDTO.getOptionId()) {
                    // 장바구니아이템 저장
                    // fakestore라서 구현 X
                    flag = true;
                    break;
                }
            }
            if (flag==false) {
                throw new Exception404("해당 옵션을 찾을 수 없습니다:" + requestDTO.getOptionId());
            }
        }

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        System.out.println("========================================responseBody 시작=========================================");
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(getPrettyString(responseBody));
        System.out.println("========================================responseBody 종료=========================================");

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
//        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").value("null"));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    @DisplayName("전체 장바구니아이템 조회 테스트")
    public void findAll_test() throws Exception {
        // given
        // 장바구니아이템 조회
        List<Cart> cartListPS = fakeStore.getCartList();

        // DTO 변환
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartListPS);

        // stub
        Mockito.when(cartService.findAll()).thenReturn(responseDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
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
    @DisplayName("장바구니아이템 수량 수정 테스트")
    public void update_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();

        CartRequest.UpdateDTO requestDTO1 = new CartRequest.UpdateDTO();
        requestDTO1.setCartId(1);
        requestDTO1.setQuantity(10);
        requestDTOs.add(requestDTO1);

        CartRequest.UpdateDTO requestDTO2 = new CartRequest.UpdateDTO();
        requestDTO2.setCartId(2);
        requestDTO2.setQuantity(10);
        requestDTOs.add(requestDTO2);

        System.out.println("========================================requestBody 시작=========================================");
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println(getPrettyString(requestBody));
        System.out.println("========================================requestBody 종료=========================================");

        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            // 장바구니아이템 유효성 검사
            boolean flag = false;
            for (Cart cart : fakeStore.getCartList()) {
                if(cart.getId() == requestDTO.getCartId()) {
                    // 장바구니아이템 업데이트
                    cart.update(requestDTO.getQuantity(), cart.getPrice() * requestDTO.getQuantity());
                    flag = true;
                    break;
                }
            }
            if (flag==false) {
                throw new Exception404("해당 장바구니아이템을 찾을 수 없습니다:" + requestDTO.getCartId());
            }
        }

        // 장바구니아이템 조회
        List<Cart> cartListPS = fakeStore.getCartList();

        // DTO 변환
        CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(cartListPS);

        // stub
        Mockito.when(cartService.update(anyList())).thenReturn(responseDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        System.out.println("========================================responseBody 시작=========================================");
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(getPrettyString(responseBody));
        System.out.println("========================================responseBody 종료=========================================");

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
//        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].cartId").value(1));
//        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(1));
//        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
//        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
//        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(100000));
    }
}
