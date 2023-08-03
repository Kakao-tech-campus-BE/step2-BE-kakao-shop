package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.util.MockJPAResultEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest extends MockJPAResultEntity {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private CartJPARepository cartJPARepository;

    @Mock
    private OrderJPARepository orderJPARepository;

    @Mock
    private ItemJPARepository itemJPARepository;

    @DisplayName("주문 인서트 테스트")
    @Test
    public void save_test() {
        //given
        User sessionUser = newUser("ssarMango", new BCryptPasswordEncoder());


        //mock
        List<Cart> cartList = new ArrayList<>();
        List<Option> givenOptions = optionDummyList(productDummyList());
        //product1 - {option : 1, quantity: 1}, {option : 2, quantity: 2}
        int cartId = 1;
        for (int i = 1; i < 3; i++) {
            cartList.add(newCart(cartId++,
                    sessionUser,
                    givenOptions.get(i - 1),
                    i));
        }

        //product2 - {option : 10, quantity : 5}, {option : 11, quantity: 6}
        for (int i = 10; i < 12; i++) {
            cartList.add(newCart(cartId++,
                    sessionUser,
                    givenOptions.get(i - 1),
                    i - 5));
        }

        //product3 - {option : 28, quantity : 2}
        cartList.add(newCart(cartId++,
                sessionUser,
                givenOptions.get(28 - 1),
                2));


        when(cartJPARepository.findAllByUserIdJoinOptionJoinProduct(anyInt()))
                .thenReturn(cartList);

        //when
        OrderResponse.saveDTO responseDTO = orderService.save(sessionUser);

        //then
        assertThat(responseDTO.getProducts().size()).isEqualTo(3);

        //product1
        OrderResponse.saveDTO.ProductDTO product = responseDTO.getProducts().get(0);
        assertThat(product.getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        assertThat(product.getItems().size()).isEqualTo(2);
        //product1 - {option : 1, quantity: 1}
        OrderResponse.saveDTO.ItemDTO item = product.getItems().get(0);
        assertThat(item.getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        assertThat(item.getQuantity()).isEqualTo(1);
        assertThat(item.getPrice()).isEqualTo(item.getQuantity() * 10000);
        //product1 - {option : 2, quantity: 2}
        item = product.getItems().get(1);
        assertThat(item.getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
        assertThat(item.getQuantity()).isEqualTo(2);
        assertThat(item.getPrice()).isEqualTo(item.getQuantity() * 10900);

        //product2
        product = responseDTO.getProducts().get(1);
        assertThat(product.getProductName()).isEqualTo("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!");
        assertThat(product.getItems().size()).isEqualTo(2);
        //product2 - {option : 10, quantity : 5}
        item = product.getItems().get(0);
        assertThat(item.getOptionName()).isEqualTo("JR310BT (무선 전용) - 레드");
        assertThat(item.getQuantity()).isEqualTo(5);
        assertThat(item.getPrice()).isEqualTo(item.getQuantity() * 49900);
        //product2 - {option : 11, quantity: 6}
        item = product.getItems().get(1);
        assertThat(item.getOptionName()).isEqualTo("JR310BT (무선 전용) - 그린");
        assertThat(item.getQuantity()).isEqualTo(6);
        assertThat(item.getPrice()).isEqualTo(item.getQuantity() * 49900);

        //product3
        product = responseDTO.getProducts().get(2);
        assertThat(product.getProductName()).isEqualTo("플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감");
        assertThat(product.getItems().size()).isEqualTo(1);
        //product2 - {option : 28, quantity : 2}
        item = product.getItems().get(0);
        assertThat(item.getOptionName()).isEqualTo("플레이스테이션 VR2 호라이즌 번들");
        assertThat(item.getQuantity()).isEqualTo(2);
        assertThat(item.getPrice()).isEqualTo(item.getQuantity() * 839000);

        assertThat(responseDTO.getTotalPrice()).isEqualTo(2258700);
    }

    @DisplayName("주문 인서트 시 장바구니가 비어있는 경우 예외 발생 테스트")
    @Test
    public void emptyCartSave_fail_test() {
        //given
        User sessionUser = newUser("ssarMango", new BCryptPasswordEncoder());


        //mock
        //product1
        List<Cart> cartList = new ArrayList<>();
        when(cartJPARepository.findAllByUserIdJoinOptionJoinProduct(anyInt()))
                .thenReturn(cartList);

        //when
        try {
            OrderResponse.saveDTO responseDTO = orderService.save(sessionUser);
        } catch (Exception400 e) {
            //then
            e.printStackTrace();
            assertThat(e.getMessage()).isEqualTo("주문할 상품이 없습니다. 장바구니에 상품을 먼저 담아주세요.");
        }
    }

    @DisplayName("주문 결과 확인 테스트")
    @Test
    public void findByOrderId_test(){
        //given
        int orderId = 1;
        User sessionUser = newUser("ssarMango", new BCryptPasswordEncoder());

        //mock
        Optional<Order> orderOp = Optional.of(newOrder(sessionUser));
        when(orderJPARepository.findById(anyInt()))
                .thenReturn(orderOp);

        List<Cart> cartList = new ArrayList<>();
        List<Option> givenOptions = optionDummyList(productDummyList());
        //product1 - {option : 1, quantity: 1}, {option : 2, quantity: 2}
        int cartId = 1;
        for (int i = 1; i < 3; i++) {
            cartList.add(newCart(cartId++,
                    sessionUser,
                    givenOptions.get(i - 1),
                    i));
        }

        //product2 - {option : 10, quantity : 5}, {option : 11, quantity: 6}
        for (int i = 10; i < 12; i++) {
            cartList.add(newCart(cartId++,
                    sessionUser,
                    givenOptions.get(i - 1),
                    i - 5));
        }

        //product3 - {option : 28, quantity : 2}
        cartList.add(newCart(cartId++,
                sessionUser,
                givenOptions.get(28 - 1),
                2));

        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            itemList.add(newItem(cartList.get(i), orderOp.get()));
        }

        when(itemJPARepository.findAllByOrderIdJoinOptionJoinProduct(anyInt()))
                .thenReturn(itemList);

        //when
        OrderResponse.findByIdDTO responseDTO = orderService.findByOrderId(orderId, sessionUser);

        //then
        assertThat(responseDTO.getProducts().size()).isEqualTo(3);

        //product1
        OrderResponse.findByIdDTO.ProductDTO product = responseDTO.getProducts().get(0);
        assertThat(product.getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        assertThat(product.getItems().size()).isEqualTo(2);
        //product1 - {option : 1, quantity: 1}
        OrderResponse.findByIdDTO.ItemDTO item = product.getItems().get(0);
        assertThat(item.getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        assertThat(item.getQuantity()).isEqualTo(1);
        assertThat(item.getPrice()).isEqualTo(item.getQuantity() * 10000);
        //product1 - {option : 2, quantity: 2}
        item = product.getItems().get(1);
        assertThat(item.getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
        assertThat(item.getQuantity()).isEqualTo(2);
        assertThat(item.getPrice()).isEqualTo(item.getQuantity() * 10900);

        //product2
        product = responseDTO.getProducts().get(1);
        assertThat(product.getProductName()).isEqualTo("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!");
        assertThat(product.getItems().size()).isEqualTo(2);
        //product2 - {option : 10, quantity : 5}
        item = product.getItems().get(0);
        assertThat(item.getOptionName()).isEqualTo("JR310BT (무선 전용) - 레드");
        assertThat(item.getQuantity()).isEqualTo(5);
        assertThat(item.getPrice()).isEqualTo(item.getQuantity() * 49900);
        //product2 - {option : 11, quantity: 6}
        item = product.getItems().get(1);
        assertThat(item.getOptionName()).isEqualTo("JR310BT (무선 전용) - 그린");
        assertThat(item.getQuantity()).isEqualTo(6);
        assertThat(item.getPrice()).isEqualTo(item.getQuantity() * 49900);

        //product3
        product = responseDTO.getProducts().get(2);
        assertThat(product.getProductName()).isEqualTo("플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감");
        assertThat(product.getItems().size()).isEqualTo(1);
        //product2 - {option : 28, quantity : 2}
        item = product.getItems().get(0);
        assertThat(item.getOptionName()).isEqualTo("플레이스테이션 VR2 호라이즌 번들");
        assertThat(item.getQuantity()).isEqualTo(2);
        assertThat(item.getPrice()).isEqualTo(item.getQuantity() * 839000);

        assertThat(responseDTO.getTotalPrice()).isEqualTo(2258700);

    }

    @DisplayName("존재하지 않는 주문번호 조회 시 예외 발생 테스트")
    @Test
    public void findByOrderIdNotFound_fail_test(){
        //given
        int orderId = 2;
        User sessionUser = newUser("ssarMango", new BCryptPasswordEncoder());

        //mock
        Optional<Order> orderOp = Optional.empty();
        when(orderJPARepository.findById(anyInt()))
                .thenReturn(orderOp);

        //when
        try {
            orderService.findByOrderId(orderId, sessionUser);
        } catch(Exception404 e) {
            //then
            e.printStackTrace();
            assertThat(e.getMessage()).isEqualTo("존재하지 않는 주문번호입니다. :2");
        }
    }

    @DisplayName("다른 사용자의 주문 번호에 접근할 때 예외 발생 테스트")
    @Test
    public void findByOrderIdNotAuthorized_fail_test(){
        //given
        int orderId = 2;
        User orderedUser = newUser(1, "ssarMango", new BCryptPasswordEncoder());
        User notOrderedUser = newUser(2, "Mango", new BCryptPasswordEncoder());

        //mock
        Optional<Order> orderOp = Optional.of(newOrder(orderedUser));
        when(orderJPARepository.findById(anyInt()))
                .thenReturn(orderOp);

        //when
        try {
            orderService.findByOrderId(orderId, notOrderedUser);
        } catch(Exception400 e) {
            e.printStackTrace();
            assertThat(e.getMessage()).isEqualTo("접근 권한이 없습니다.");
        }
    }
}