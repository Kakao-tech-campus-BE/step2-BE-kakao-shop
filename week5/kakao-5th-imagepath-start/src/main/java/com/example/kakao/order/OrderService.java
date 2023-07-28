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
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.SaveDTO saveOrder(User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 장바구니가 비어있을 경우
        if (cartList.isEmpty()) {
            throw new Exception400("장바구니가 비었습니다.");
        }

        Order order = Order.builder()
                .user(user)
                .build();
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

        return new OrderResponse.SaveDTO(itemList);
    }

    @Transactional
    public OrderResponse.FindByIdDTO findById(int id, User user) {
        Order order = orderJPARepository.findById(id)
                // 존재하지 않는 id가 들어올 경우
                .orElseThrow(() -> new Exception400("주문 내역이 존재하지 않습니다."));

        List<Item> itemList = itemJPARepository.findAllByOrderId(id);
        return new OrderResponse.FindByIdDTO(itemList);
    }
}