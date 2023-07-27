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
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final OrderJPARepository orderJPARepository;

    @Transactional
    public OrderResponse.SaveDTO saveOrder(User user) {
        List<Cart> carts = cartJPARepository.findAllByUserId(user.getId());

        if(carts.isEmpty()) {
            throw new Exception400("해당 유저의 장바구니는 비어있습니다.");
        }

        Order order = Order.builder()
                .user(user)
                .build();
        orderJPARepository.save(order);

        List<Item> items = carts.stream()
                .map(cart -> Item.builder()
                        .option(cart.getOption())
                        .order(order)
                        .quantity(cart.getQuantity())
                        .price(cart.getPrice())
                        .build())
                .collect(Collectors.toList());

        itemJPARepository.saveAll(items);
        cartJPARepository.deleteByUserId(user.getId());

        return new OrderResponse.SaveDTO(items);
    }

    public OrderResponse.FindDTO findOrder(int id, User user) {
        Order order = orderJPARepository.findById(id)
                .orElseThrow(() -> new Exception400("해당 주문이 존재하지 않습니다."));

        if(order.getUser().getId() !=  user.getId()) {
            throw new Exception400("해당 주문은 다른 유저의 주문입니다.");
        }

        List<Item> items = itemJPARepository.findByOrderId(order.getId());

        return new OrderResponse.FindDTO(items);
    }
}
