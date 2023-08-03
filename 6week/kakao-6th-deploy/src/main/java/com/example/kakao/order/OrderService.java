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

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    public OrderResponse.SaveDTO save(User user) {

        // 장바구니 가져오기
        List<Cart> carts = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        if (carts.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다.");
        }

        // 주문 생성
        Order order = orderJPARepository.save(Order.builder().user(user).build());

        // 아이템 생성
        List<Item> items = new ArrayList<>();
        for (Cart cart : carts) {
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice())
                    .build();
            itemJPARepository.save(item);
            items.add(item);
        }

        // 장바구니 초기화
        cartJPARepository.deleteByUserId(user.getId());

        return new OrderResponse.SaveDTO(order, items);
    }

    public OrderResponse.SaveDTO findById(int id) {
        Order order = orderJPARepository.findById(id).orElseThrow(() -> new Exception404("해당 주문 번호는 존재하지 않습니다."));
        List<Item> items = itemJPARepository.findAllByOrderId(id);
        return new OrderResponse.SaveDTO(order, items);
    }
}
