package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.Cart;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.example.kakao.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@Import({
        FakeStore.class,
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest
public class OrderRestControllerTest extends DummyEntity {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FakeStore fakeStore;
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;
    @MockBean
    private UserService userService;

    // 결재하기 - (주문 인서트)
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void order_save_success_test() throws Exception {
        // given
        User user1 = new User(1, "ssar@nate.com", "meta1234!", "ssar", "USER");
        Product product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        Option option1 = new Option(1, product1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product1, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        Cart cart1 = newCart(1, user1, option1, 10);
        Cart cart2 = newCart(2, user1, option2, 10);
        /* stub - order */
        BDDMockito.given(fakeStore.getOrderList()).willReturn(Arrays.asList(
                newOrder(user1)
        ));
        Order order1 = newOrder(user1);

        /* stub - item */
        BDDMockito.given(fakeStore.getItemList()).willReturn(Arrays.asList(
                newItem(4, cart1, order1),
                newItem(5, cart2, order1)
        ));

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("===== [결재하기(주문 인서트) 테스트] =====");
        System.out.println(responseBody);
        System.out.println();

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(4));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(209000));
    }

    // 주문 결과 확인 - 성공
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void order_check_success_test() throws Exception {
        // given
        User user1 = new User(1, "ssar@nate.com", "meta1234!", "ssar", "USER");
        Product product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        Option option1 = new Option(1, product1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product1, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        Cart cart1 = newCart(1, user1, option1, 10);
        Cart cart2 = newCart(2, user1, option2, 10);
        /* stub - order */
        BDDMockito.given(fakeStore.getOrderList()).willReturn(Arrays.asList(
                newOrder(user1)
        ));
        Order order1 = newOrder(user1);

        /* stub - item */
        BDDMockito.given(fakeStore.getItemList()).willReturn(Arrays.asList(
                newItem(4, cart1, order1),
                newItem(5, cart2, order1)
        ));

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/1")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("===== [주문 결과 확인 테스트] =====");
        System.out.println(responseBody);
        System.out.println();

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(4));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(209000));
    }

    // 주문 결과 확인 - 404 Error
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void order_check_404_error_test() throws Exception {
        // given
        User user1 = new User(1, "ssar@nate.com", "meta1234!", "ssar", "USER");
        Product product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        Option option1 = new Option(1, product1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product1, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        Cart cart1 = newCart(1, user1, option1, 10);
        Cart cart2 = newCart(2, user1, option2, 10);
        /* stub - order */
        BDDMockito.given(fakeStore.getOrderList()).willReturn(Arrays.asList(
                newOrder(user1)
        ));
        Order order1 = newOrder(user1);

        /* stub - item */
        BDDMockito.given(fakeStore.getItemList()).willReturn(Arrays.asList(
                newItem(4, cart1, order1),
                newItem(5, cart2, order1)
        ));

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/2")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("===== [주문 결과 확인 테스트 - 404 Error] =====");
        System.out.println(responseBody);
        System.out.println();

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value("404"));
    }
}
