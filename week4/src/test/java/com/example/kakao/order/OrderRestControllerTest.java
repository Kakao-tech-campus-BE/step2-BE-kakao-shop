package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductRestController;
import com.example.kakao.product.ProductService;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionService;
import com.example.kakao.user.User;
import com.example.kakao.user.UserResponse;
import com.example.kakao.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// GlobalExceptionHandler 와 UserRestController를 SpringContext에 등록합니다.

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {

    // 객체의 모든 메서드는 추상메서드로 구현됩니다. (가짜로 만들면)
    // 해당 객체는 SpringContext에 등록됩니다.
    @MockBean
    private OrderService orderService;

    @MockBean
    private UserService userService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    // @WebMvcTest를 하면 MockMvc가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private MockMvc mvc; //요청 보낼때 사용

    // @WebMvcTest를 하면 ObjectMapper가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private ObjectMapper om; //직렬화

    @MockBean
    private CustomUserDetails userDetails;


    @BeforeEach
    void beforeEach(){
        //stub
        Product product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        Option option1 = new Option(1, product1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product1,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        User u = new User(1, "user1@nate,com", "fake", "user1", "USER");
        AtomicInteger counter = new AtomicInteger(1);
        Order order = new Order(1, u);
        List<Cart> cartList = Arrays.asList(
                new Cart(1, u, option1, 5, option1.getPrice()),
                new Cart(2, u, option2, 5, option2.getPrice())
        );
        List<Item> itemList = cartList.stream().map(
                cart -> new Item(counter.getAndIncrement(), cart.getOption(), order, cart.getQuantity(), cart.getPrice())
        ).collect(Collectors.toList());

        BDDMockito.given(userService.findById(any())).willReturn(
                new UserResponse.FindById(u));

        // 인증 객체 설정
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        BDDMockito.given(userDetails.getUser()).willReturn(u);

        BDDMockito.given(orderService.save(any())).willReturn(
                new OrderResponse.FindByIdDTO(
                        order, itemList));
        BDDMockito.given(orderService.findById(anyInt())).willReturn(
                new OrderResponse.FindByIdDTO(
                        order, itemList
                ));

    }

    @Test
    @WithMockUser(username = "user1@nate.com", roles = "USER") //가짜 인증객체 만들기
    @DisplayName("주문 저장하기 테스트")
    public void save_test() throws Exception{
        // given

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response.id").value(1));
        result.andExpect(jsonPath("$.response.totalPrice").value(104500));

        result.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        result.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));

        result.andExpect(jsonPath("$.response.products[0].items[1].id").value(2));
        result.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(5));
        result.andExpect(jsonPath("$.response.products[0].items[1].price").value(54500));
    }

    @Test
    @WithMockUser(username = "user1@nate.com", roles = "USER") //가짜 인증객체 만들기
    @DisplayName("주문 결과 확인 테스트")
    public void findById_test() throws Exception{
        //given
        int id =1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+id)); //요청 uri

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response.id").value(1));
        result.andExpect(jsonPath("$.response.totalPrice").value(104500));

        result.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        result.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));

        result.andExpect(jsonPath("$.response.products[0].items[1].id").value(2));
        result.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(5));
        result.andExpect(jsonPath("$.response.products[0].items[1].price").value(54500));
    }


}
