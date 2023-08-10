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
    private final CartJPARepository cartRepository;
    private final OrderJPARepository orderRepository;
    private final ItemJPARepository itemRepository;

    public OrderResponse.SaveCartListDTO saveCartList(User sessionUser) {
        List<Cart> cartListPS = cartRepository.findByUserIdOrderByOptionIdAsc(sessionUser.getId());
        if (cartListPS.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다");
        }
        Order order = Order.builder().user(sessionUser).build();
        orderRepository.save(order);

        List<Item> itemListPS = new ArrayList<>();
        for (Cart cartPS : cartListPS) {
            Item item = Item.builder().option(cartPS.getOption()).order(order)
                    .quantity(cartPS.getQuantity()).price(cartPS.getPrice()).build();
            itemListPS.add(item);
        }
        itemRepository.saveAll(itemListPS);
        cartRepository.deleteAllByUserId(sessionUser.getId());

        return new OrderResponse.SaveCartListDTO(itemListPS);
    }

    public OrderResponse.FindByIdDTO findById(int id, User sessionUser) {
        List<Item> itemListPS = itemRepository.findByOrderIdJoinOrder(id, sessionUser.getId());
        if (itemListPS.isEmpty()) {
            throw new Exception404("해당 주문을 찾을 수 없습니다 : " + id);
        }
        return new OrderResponse.FindByIdDTO(itemListPS);
    }
}
