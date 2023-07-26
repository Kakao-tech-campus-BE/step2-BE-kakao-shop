package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    public OrderResponse.SaveDTO save(User sessionUser) {
        List<Cart> carts = cartJPARepository.findAllNestedByUserId(sessionUser.getId());
        if (carts.isEmpty()) {
            throw new Exception400("잘못된 요청입니다.");
        }

        Order order = Order.builder()
                .user(sessionUser)
                .build();
        orderJPARepository.save(order);

        List<Item> items = carts.stream()
                .map(x-> Item.builder()
                .option(x.getOption())
                .order(order)
                .quantity(x.getQuantity())
                .price(x.getPrice())
                .build())
                .collect(Collectors.toList());

        itemJPARepository.saveAll(items);
        cartJPARepository.deleteAll(carts);

        return new OrderResponse.SaveDTO(order, items);
    }

    public OrderResponse.SaveDTO findOrderById(int id, User sessionUser) {
        int orderCount = orderJPARepository.countOrderIdByUserId(sessionUser.getId());
        if (orderCount==0 | id > orderCount) {
            throw new Exception400("잘못된 요청입니다.");
        }

        List<Order> orderId = orderJPARepository.findAllOrderByUserIdAndIndex(sessionUser.getId());
        if (orderId.isEmpty()){
            throw new Exception400("주문을 찾을 수 없습니다.");
        }
        Order order = orderId.get(id-1);

        List<Item> items = itemJPARepository.findAllByOrderId(order.getId());
        if (items.isEmpty()) {
            throw new Exception400("잘못된 요청입니다.");
        }

        return new OrderResponse.SaveDTO(order, items);
    }
}
