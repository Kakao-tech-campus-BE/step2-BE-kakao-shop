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

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final CartJPARepository cartRepository;
    private final OrderJPARepository orderRepository;
    private final ItemJPARepository itemRepository;

    /* 결재하기(주문 인서트) Service Layer */
    @Transactional
    public OrderResponse.OrderInsertDTO save(User sessionUser) {
        List<Cart> userCart = cartRepository.findAllByUserId(sessionUser.getId()).orElseThrow(
                // 장바구니 조회가 되지 않는 상황에서 주문 요청을 하는 경우
                () -> new Exception400("비정상적인 주문 요청입니다. 장바구니를 확인하세요")
        );
        Order order = Order.builder()
                .user(sessionUser)
                .build();
        orderRepository.save(order);
        List<Item> itemList = new ArrayList<>();
        for(Cart cart : userCart) {
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice())
                    .build();
            itemRepository.save(item);
            itemList.add(item);
        }
        // Respones DTO 반환
        return new OrderResponse.OrderInsertDTO(itemList);
    }

    @Transactional
    public OrderResponse.OrderCheckDTO orderCheck(int orderNumber) {
        // Item 쿼리
        List<Item> itemList = orderRepository.findByOrderNumber(orderNumber);
        // Response DTO 반환
        return new OrderResponse.OrderCheckDTO(itemList);
    }
}
