package com.example.kakao.order;

import com.example.kakao._core.errors.exception.cart.CartException;
import com.example.kakao._core.errors.exception.order.Item.ItemException;
import com.example.kakao._core.errors.exception.order.OrderException;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.*;

@ActiveProfiles("test")
@DisplayName("주문_서비스_테스트")
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderJPARepository orderJPARepository;

    @Mock
    private ItemJPARepository itemJPARepository;

    @Mock
    private CartJPARepository cartJPARepository;

    @DisplayName("주문_추가_테스트")
    @Test
    public void order_add_test() {
        // given
        User user = new User(1, "ssarmango@nate.com", "meta12!", "ssarmango", "ROLE_USER");
        List<Option> optionList = createOptionList();
        List<Cart> cartList = List.of(
                Cart.builder().id(1).option(optionList.get(0)).user(user).quantity(5).price(optionList.get(0).getPrice()*5).build(),
                Cart.builder().id(2).option(optionList.get(1)).user(user).quantity(3).price(optionList.get(1).getPrice()*5).build()
        );

        // mocking
        given(cartJPARepository.findAllByUserIdFetchJoin(anyInt())).willReturn(cartList);

        // when & then
        OrderResponse.CreateDTO resultDTO = orderService.create(user);
    }

    @DisplayName("주문_추가_테스트_실패_빈_장바구니")
    @Test
    public void order_add_test_fail_empty_carts() {
        // given
        User user = new User(1, "ssarmango@nate.com", "meta12!", "ssarmango", "ROLE_USER");

        // mocking
        given(cartJPARepository.findAllByUserIdFetchJoin(anyInt())).willReturn(List.of());

        // when & then
        assertThatThrownBy(() -> orderService.create(user)).isInstanceOf(CartException.CartNotFoundException.class);

    }

    @DisplayName("주문_추가_테스트_실패_주문_추가_중_오류")
    @Test
    public void order_add_test_fail_order_save_error() {
        // given
        User user = new User(1, "ssarmango@nate.com", "meta12!", "ssarmango", "ROLE_USER");
        List<Option> optionList = createOptionList();
        List<Cart> cartList = List.of(
                Cart.builder().id(1).option(optionList.get(0)).user(user).quantity(5).price(optionList.get(0).getPrice()*5).build(),
                Cart.builder().id(2).option(optionList.get(1)).user(user).quantity(3).price(optionList.get(1).getPrice()*5).build()
        );

        // mocking
        given(cartJPARepository.findAllByUserIdFetchJoin(anyInt())).willReturn(cartList);
        given(orderJPARepository.save(any())).willThrow(new RuntimeException("db error"));

        // when & then
        assertThatThrownBy(() -> orderService.create(user)).isInstanceOf(OrderException.OrderSaveException.class);
    }

    @DisplayName("주문_추가_테스트_실패_주문제품_추가_중_오류")
    @Test
    public void order_add_test_fail_item_save_error() {
        // given
        User user = new User(1, "ssarmango@nate.com", "meta12!", "ssarmango", "ROLE_USER");
        List<Option> optionList = createOptionList();
        List<Cart> cartList = List.of(
                Cart.builder().id(1).option(optionList.get(0)).user(user).quantity(5).price(optionList.get(0).getPrice()*5).build(),
                Cart.builder().id(2).option(optionList.get(1)).user(user).quantity(3).price(optionList.get(1).getPrice()*5).build()
        );

        // mocking
        given(cartJPARepository.findAllByUserIdFetchJoin(anyInt())).willReturn(cartList);
        given(itemJPARepository.saveAll(any())).willThrow(new RuntimeException("db error"));

        // when & then
        assertThatThrownBy(() -> orderService.create(user)).isInstanceOf(ItemException.ItemSaveException.class);
    }

    @DisplayName("주문_추가_테스트_실패_장바구니_비우기_오류")
    @Test
    public void order_add_test_fail_carts_clear_error() {
        // given
        User user = new User(1, "ssarmango@nate.com", "meta12!", "ssarmango", "ROLE_USER");
        List<Option> optionList = createOptionList();
        List<Cart> cartList = List.of(
                Cart.builder().id(1).option(optionList.get(0)).user(user).quantity(5).price(optionList.get(0).getPrice()*5).build(),
                Cart.builder().id(2).option(optionList.get(1)).user(user).quantity(3).price(optionList.get(1).getPrice()*5).build()
        );

        // mocking
        given(cartJPARepository.findAllByUserIdFetchJoin(anyInt())).willReturn(cartList);
        willThrow(new RuntimeException("db error")).given(cartJPARepository).deleteAllIn(List.of(1, 2));

        // when & then
        assertThatThrownBy(() -> orderService.create(user)).isInstanceOf(CartException.CartDeleteException.class);
    }

    @DisplayName("주문_조회_테스트")
    @Test
    public void order_findById_test() {
        // given
        User user = new User(1, "ssarmango@nate.com", "meta12!", "ssarmango", "ROLE_USER");
        List<Option> optionList = createOptionList();
        Order order = Order.builder().id(1).user(user).build();
        List<Item> itemList = List.of(
                Item.builder().id(1).order(order).option(optionList.get(0)).build(),
                Item.builder().id(2).order(order).option(optionList.get(1)).build()
        );

        // mocking
        given(orderJPARepository.findById(anyInt())).willReturn(Optional.of(order));
        given(itemJPARepository.findByOrderId(anyInt())).willReturn(itemList);

        // when & then
        orderService.findById(order.getId(), user);
    }

    @DisplayName("주문_조회_테스트_실패_주문_없음")
    @Test
    public void order_findById_test_fail_order_not_found() {
        // given
        User user = new User(1, "ssarmango@nate.com", "meta12!", "ssarmango", "ROLE_USER");
        List<Option> optionList = createOptionList();
        Order order = Order.builder().id(1).user(user).build();

        // mocking
        given(orderJPARepository.findById(anyInt())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> orderService.findById(order.getId(), user))
                .isInstanceOf(OrderException.OrderNotFoundException.class);
    }

    @DisplayName("주문_조회_테스트_실패_주문_회원과_다른_회원_접근")
    @Test
    public void order_findById_test_fail_Mismatch_user() {
        // given
        User user = new User(1, "ssarmango@nate.com", "meta12!", "ssarmango", "ROLE_USER");
        User anotherUser = new User(2, "ssar@nate.com", "meta12!", "ssar", "ROLE_USER");
        Order order = Order.builder().id(1).user(user).build();

        // mocking
        given(orderJPARepository.findById(anyInt())).willReturn(Optional.of(order));

        // when & then
        assertThatThrownBy(() -> orderService.findById(order.getId(),anotherUser))
                .isInstanceOf(OrderException.ForbiddenOrderAccess.class);
    }

    @DisplayName("주문_조회_테스트_실패_빈_주문제품")
    @Test
    public void order_findById_test_fail_items_not_found() {
        // given
        User user = new User(1, "ssarmango@nate.com", "meta12!", "ssarmango", "ROLE_USER");
        Order order = Order.builder().id(1).user(user).build();

        // mocking
        given(orderJPARepository.findById(anyInt())).willReturn(Optional.of(order));
        given(itemJPARepository.findByOrderId(anyInt())).willReturn(List.of());

        // when & then
        assertThatThrownBy(() -> orderService.findById(order.getId(),user))
                .isInstanceOf(ItemException.ItemNotFoundException.class);
    }

    private List<Option> createOptionList() {
        Product product = Product.builder().id(1).productName("테스트 상품 1").price(5000).build();
        return List.of(
                Option.builder().id(1).optionName("테스트 옵션 1").product(product).price(5000).build(),
                Option.builder().id(2).optionName("테스트 옵션 2").product(product).price(7000).build()
        );
    }
}
