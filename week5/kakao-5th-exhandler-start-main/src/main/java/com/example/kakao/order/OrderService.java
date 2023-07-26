package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
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
}
