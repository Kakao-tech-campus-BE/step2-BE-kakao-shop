package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.CartResponse;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private CartJPARepository cartJPARepository;
    private OrderJPARepository orderJPARepository;
    private ItemJPARepository itemJPARepository;

    public OrderResponse.SaveDTO saveOrder(User user) {
        // 장바구니 찾고
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
        if (cartList.isEmpty()) throw new Exception404("장바구니가 비어있습니다.");

        // 주문 저장
        Order order = orderJPARepository.save(Order.builder()
                .user(user)
                .build());

        orderJPARepository.save(order);

        List<Item> items = new ArrayList<>();

        // 주문 아이템 저장
        for (int i = 0; i < cartList.size(); i++) {
            Cart cart = cartList.get(i);
            Item item = Item.builder()
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice())
                    .option(cart.getOption())
                    .build();

            items.add(item);
            itemJPARepository.save(item);
        }

//        List<Item> items = itemJPARepository.saveAll();

        // 장바구니 초기화
        cartJPARepository.deleteByUserId(user.getId());

        return new OrderResponse.SaveDTO(items, order);
    }

    public OrderResponse.FindByIdDTO findById(int id) {
        Order order = orderJPARepository.findById(id).orElseThrow(
                () -> new Exception404("해당 주문을 찾을 수 없습니다.")
        );

        List<Item> items = itemJPARepository.findAllByOrder(order);
        return new OrderResponse.FindByIdDTO(items, order);
    }
}
