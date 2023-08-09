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

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    final private OrderJPARepository orderJPARepository;

    final private ItemJPARepository itemJPARepository;

    final private CartJPARepository cartJPARepository;

    @Transactional
    public OrderResponse.SaveDTO save(User user) {
        List<Cart> carts = cartJPARepository.findAllByUserIdFetchOptionAndProduct(user.getId());

        if (carts.isEmpty()) throw new Exception404("사용자의 장바구니가 존재하지 않습니다.");

        Order order = Order.builder()
                .user(user)
                .build();

        List<Item> orderItems = carts.stream()
                .map(cart ->
                        Item.builder()
                                .option(cart.getOption())
                                .order(order)
                                .quantity(cart.getQuantity())
                                .price(cart.getPrice())
                                .build())
                .collect(Collectors.toList());

        orderJPARepository.save(order);
        itemJPARepository.saveAll(orderItems);

        cartJPARepository.deleteAllInBatch(carts);

        return new OrderResponse.SaveDTO(orderItems);
    }

    public OrderResponse.SaveDTO getOrder(User user, int id) {
        Order order = orderJPARepository.findById(id).orElseThrow(() ->
                new Exception404("존재하지 않는 주문입니다.")
        );
        if (order.getUser().getId() != user.getId())
            throw new Exception403("허용되지 않은 접근입니다.");

        List<Item> orderItems = itemJPARepository.findAllByOrderId(order.getId());

        return new OrderResponse.SaveDTO(orderItems);
    }
}
