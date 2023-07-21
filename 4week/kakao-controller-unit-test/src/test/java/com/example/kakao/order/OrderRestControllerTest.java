package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.Cart;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.OrderItem;
import com.example.kakao.order.item.OrderItemService;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductRestController;
import com.example.kakao.product.ProductService;
import com.example.kakao.product.option.ProductOption;
import com.example.kakao.product.option.ProductOptionService;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(OrderRestController.class)
class OrderRestControllerTest extends DummyEntity {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductOptionService productOptionService;

    @MockBean
    private OrderItemService orderItemService;

    @MockBean
    private FakeStore fakeStore;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private ObjectMapper om;

    OrderItem orderItem1;
    OrderItem orderItem2;

    Cart cart1;
    Cart cart2;
    Product product;
    ProductOption productOption1;
    ProductOption productOption2;
    Order order;
    User user;

    List<OrderItem> orderItemList; // 추가: orderItemList을 선언

    @BeforeEach
    void setUp() {
        user = newUser("sohyun");
        order = newOrder(user);
        //Product newProduct(String productName, int imageNumber, int price)
        product = newProduct("test", 1, 10000);
        //ProductOption newOption(Product product, String optionName, int price)
        productOption1 = newOption(product, "testOption1", 2000);
        productOption2 = newOption(product, "testOption2", 3000);
        //newCart(User user, ProductOption productOption, Integer quantity)
        cart1 = newCart(user, productOption1, 10);
        cart2 = newCart(user, productOption2, 5);
        //OrderItem newItem(Cart cart, Order order)
        orderItem1 = newItem(cart1, order);
        orderItem2 = newItem(cart2, order);

        orderItemList = new ArrayList<>(); // 추가: orderItemList을 초기화
        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);
    }

    @Test
    void testSaveOrderSuccess() throws Exception {

        // Mocking
        given(orderService.findAll()).willReturn(List.of(order));
        given(orderItemService.findAll()).willReturn(orderItemList); // 테스트를 위해 orderItemList로 설정

        User user = newUser("sohyun");
        UserDetails userDetails = new CustomUserDetails(user);

        // 사용자 정보로 인증 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        // SecurityContextHolder에 인증 객체 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

/*
        System.out.println(order.getUser().getUsername());
        System.out.println(orderItemList.get(0).getOrder().getUser().getUsername());

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.
                post("/orders")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
*/
        mvc.perform(MockMvcRequestBuilders.post("/orders"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.id").value(order.getId()))
                .andExpect(jsonPath("$.response.products[0].productName").value("test"))
                .andExpect(jsonPath("$.response.products[0].items[0].id").value(0))
                .andExpect(jsonPath("$.response.products[0].items[0].optionName").value("testOption1"))
                .andExpect(jsonPath("$.response.products[0].items[0].quantity").value(10))
                .andExpect(jsonPath("$.response.products[0].items[0].price").value(20000))
                .andExpect(jsonPath("$.response.products[0].items[1].id").value(0))
                .andExpect(jsonPath("$.response.products[0].items[1].optionName").value("testOption2"))
                .andExpect(jsonPath("$.response.products[0].items[1].quantity").value(5))
                .andExpect(jsonPath("$.response.products[0].items[1].price").value(15000))
                .andExpect(jsonPath("$.error").doesNotExist());


        //verify
        verify(orderService, times(1)).findAll();
        verify(orderItemService, times(1)).findAll();
    }

    @Test
    void testSaveOrderUnAuthorized() throws Exception {

        // Mocking
        given(orderService.findAll()).willReturn(List.of(order));
        given(orderItemService.findAll()).willReturn(orderItemList); // 테스트를 위해 orderItemList로 설정


        mvc.perform(MockMvcRequestBuilders.post("/orders"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("인증되지 않았습니다" ))
                .andExpect(jsonPath("$.error.status").value(401));


        //verify
        verify(orderService, times(0)).findAll();
        verify(orderItemService, times(0)).findAll();

    }

    @Test
    void testFindOrderByIdUnAuthorized() throws Exception {

        // Mocking
        int validOrderId = 1;

        given(orderService.findById(validOrderId - 1)).willReturn(order);
        given(orderItemService.findAll()).willReturn(orderItemList);


        mvc.perform(MockMvcRequestBuilders.get("/orders/{id}", validOrderId))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("인증되지 않았습니다" ))
                .andExpect(jsonPath("$.error.status").value(401));


        //verify
        verify(orderService, times(0)).findById(validOrderId - 1);
        verify(orderItemService, times(0)).findAll();

    }

    @Test
    @WithMockUser
    public void testFindOrderByIdValidOrderId() throws Exception {
        // Mocking
        int validOrderId = 1;

        given(orderService.findById(validOrderId-1)).willReturn(order);
        given(orderItemService.findAll()).willReturn(orderItemList);
/*
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/{id}", validOrderId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
*/
        // Test: Valid Order ID
        mvc.perform(MockMvcRequestBuilders.get("/orders/{id}", validOrderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.id").value(order.getId()))
                .andExpect(jsonPath("$.response.products[0].productName").value("test"))
                .andExpect(jsonPath("$.response.products[0].items[0].id").value(0))
                .andExpect(jsonPath("$.response.products[0].items[0].optionName").value("testOption1"))
                .andExpect(jsonPath("$.response.products[0].items[0].quantity").value(10))
                .andExpect(jsonPath("$.response.products[0].items[0].price").value(20000))
                .andExpect(jsonPath("$.response.products[0].items[1].id").value(0))
                .andExpect(jsonPath("$.response.products[0].items[1].optionName").value("testOption2"))
                .andExpect(jsonPath("$.response.products[0].items[1].quantity").value(5))
                .andExpect(jsonPath("$.response.products[0].items[1].price").value(15000))
                .andExpect(jsonPath("$.error").doesNotExist());

        // Verify
        verify(orderService, times(1)).findById(validOrderId - 1);
        verify(orderItemService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    public void testFindOrderByIdInValidOrderId() throws Exception {
        // Mocking
        int invalidOrderId = -1;

        given(orderService.findById(invalidOrderId - 1)).willReturn(null);
        given(orderItemService.findAll()).willReturn(null);
/*
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/-1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
*/
        // Test: Invalid Order ID
        mvc.perform(MockMvcRequestBuilders.get("/orders/{id}", invalidOrderId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("유효하지 않은 주문 ID입니다. ID: "+invalidOrderId))
                .andExpect(jsonPath("$.error.status").value(404))
                .andReturn();

        // Verify
        verify(orderService, times(0)).findById(invalidOrderId - 1);
        verify(orderItemService, times(0)).findAll();
    }

}
