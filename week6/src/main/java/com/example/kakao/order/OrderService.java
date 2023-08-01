package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.errors.exception.Exception404;
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

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    @Transactional
    public OrderResponse.FindByIdDTO save(User user) {
        List<Cart> carts = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId()).orElseThrow(
                () -> new Exception403("장바구니가 비어있습니다.")
        );

        Order savedOrder = orderJPARepository.save(Order.builder().user(user).build());
        List<Item> items = carts
                .stream()
                .map(cart -> Item.builder()
                        .option(cart.getOption())
                        .order(savedOrder)
                        .quantity(cart.getQuantity())
                        .price(cart.getPrice())
                        .build())
                .collect(Collectors.toList());

        items = items
                .stream()
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList());
        List<Item> savedItems = itemJPARepository.saveAll(items);
        cartJPARepository.deleteAll(carts);
        return new OrderResponse.FindByIdDTO(savedOrder, savedItems);
    }

    public OrderResponse.FindByIdDTO findById(User user, int id) {
        Order order = orderJPARepository.findById(id).orElseThrow(() -> new Exception404("존재하지 않는 주문 번호입니다."));

        if (order.getUser().getId() != user.getId()) {
            throw new Exception403("현재 계정으로 해당 주문을 조회할 수 없습니다.");
        }

        List<Item> items = itemJPARepository.findAllByOrderId(id);
        return new OrderResponse.FindByIdDTO(order, items);
    }
}