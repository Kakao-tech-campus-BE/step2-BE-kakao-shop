package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.CustomCollectionValidator;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductService;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {

    @MockBean
    private CartService cartService;

    @MockBean
    private CustomCollectionValidator validator;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    void beforeEach(){

    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER") //가짜 인증객체 만들기
    @Test
    @DisplayName("장바구니 조회")
    public void findAll_test() throws Exception {
        //given
        //stub
        Product product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        Option option1 = new Option(1, product1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product1,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        User u = new User(1, "user1@nate,com", "fake", "user1", "USER");
        BDDMockito.given(cartService.findAll()).willReturn(
                new CartResponse.FindAllDTO(
                        Arrays.asList(
                                new Cart(1, u, option1, 5, option1.getPrice()),
                                new Cart(2, u, option2, 5, option2.getPrice())
                        )));

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts"));
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        //then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response.products[0].id").value(1));
        result.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.products[0].carts[0].id").value(1));
        result.andExpect(jsonPath("$.response.products[0].carts[0].option.id").value(1));
        result.andExpect(jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.products[0].carts[0].option.price").value(10000));
        result.andExpect(jsonPath("$.response.products[0].carts[0].quantity").value(5));
        result.andExpect(jsonPath("$.response.products[0].carts[0].price").value(50000));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER") //가짜 인증객체 만들기
    @Test
    @DisplayName("장바구니 담기")
    public void add_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(1);
        d1.setQuantity(5);
        CartRequest.SaveDTO d2 = new CartRequest.SaveDTO();
        d2.setOptionId(2);
        d2.setQuantity(5);
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
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response").doesNotExist()); //null인지 확인
        result.andExpect(jsonPath("$.error").doesNotExist());
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER") //가짜 인증객체 만들기
    @Test
    @DisplayName("장바구니 수정")
    public void update_test() throws Exception {
        // given
        //요청 DTO 생성
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

        //stub
        Product product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        Option option1 = new Option(1, product1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product1,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        User u = new User(1, "user1@nate,com", "fake", "user1", "USER");
        CartResponse.UpdateDTO updateDTO = new CartResponse.UpdateDTO(
                Arrays.asList(
                        new Cart(1, u, option1, 5, option1.getPrice()),
                        new Cart(2, u, option2, 5, option2.getPrice())
                ));
        BDDMockito.given(cartService.update(any())).willReturn(updateDTO);

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
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response.carts[0].cartId").value(1));
        result.andExpect(jsonPath("$.response.carts[0].optionId").value(1));
        result.andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.carts[0].quantity").value(5));
        result.andExpect(jsonPath("$.response.carts[0].price").value(50000));
    }
}
