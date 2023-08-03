package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
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

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.SaveDTO saveOrder(User user) {
        // 장바구니 찾고
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
        if (cartList.isEmpty()) throw new Exception404("장바구니가 비어있습니다.");

        // 주문 저장
        Order order = Order.builder()
                .user(user)
                .build();

        orderJPARepository.save(order);

        int totalPrice = 0;

        List<Item> items = new ArrayList<>();

        // 주문 아이템 저장
        for (Cart cart : cartList) {
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice())
                    .build();

            items.add(item);
            totalPrice += cart.getPrice();
        }
        itemJPARepository.saveAll(items);

        // 장바구니 초기화
        cartJPARepository.deleteByUserId(user.getId());

        return new OrderResponse.SaveDTO(items, order, totalPrice);
    }

    @Transactional
    public OrderResponse.FindByIdDTO findById(User user, int id) {
        List<Item> items = itemJPARepository.findAllByOrderId(id);
        Order order = orderJPARepository.findById(id)
                .orElseThrow(() -> new Exception400("해당 주문을 찾을 수 없습니다."));

        return new OrderResponse.FindByIdDTO(items, order);
    }
}
