package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    // 주문 생성
    // 하나의 트랜잭션 내에서 실행되도록 설정
    @Transactional
    public OrderResponse.FindByIdDTO save(User user) {

        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 장바구니 비어있는 경우 예외처리
        if(cartList.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다.");
        }

        Order order = Order.builder()
                .user(user)
                .build();
        orderJPARepository.save(order);

        // cartList의 각 요소를 가공해 새로운 리스트인 itemList을 생성
        List<Item> itemList = cartList.stream()
                .map(cart -> Item.builder()
                        .option(cart.getOption())
                        .order(order)
                        .quantity(cart.getQuantity())
                        .price(cart.getPrice())
                        .build())
                .collect(Collectors.toList());

        itemJPARepository.saveAll(itemList);

        // 주문 처리 후 장바구니 초기화
        cartJPARepository.deleteAllByUserId(user.getId());

        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    // 주문 조회
    public OrderResponse.FindByIdDTO findById(int id, User user) {

        // 잘못된 주문id로 접근하는 경우 예외처리
        Order order = orderJPARepository.findById(id).orElseThrow(
                () -> new Exception400("해당 주문 내역을 찾을 수 없습니다.")
        );

        List<Item> itemList = itemJPARepository.findAllByOrderId(id);

        return new OrderResponse.FindByIdDTO(order, itemList);
    }
}