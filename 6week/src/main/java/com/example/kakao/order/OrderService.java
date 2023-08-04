package com.example.kakao.order;

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
    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.SaveDTO save(User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        if (cartList.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다 : " + user.getId());
        }

        Order order = Order.builder().user(user).build();
        orderJPARepository.save(order);

        List<Item> itemList = cartList.stream()
                .map(cart -> Item.builder()
                        .option(cart.getOption())
                        .order(order)
                        .quantity(cart.getQuantity())
                        .price(cart.getPrice())
                        .build())
                .collect(Collectors.toList());

        itemJPARepository.saveAll(itemList);
        cartJPARepository.deleteByUserId(user.getId());

        return new OrderResponse.SaveDTO(order, itemList);

    }

    @Transactional
    public OrderResponse.FindByIdDTO findById(int id) {
        Order order = orderJPARepository.findById(id).orElseThrow(
                () -> new Exception404("해당 주문 정보가 존재하지 않습니다 : " + id)
        );

        List<Item> itemList = itemJPARepository.findByOrderId(id);

        return new OrderResponse.FindByIdDTO(order, itemList);

    }
}
