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

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    @Transactional
    public OrderResponse.FindByIdDTO findById(int id) {
        Order order = orderJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 주문을 찾을 수 없습니다 : "+id));
        List<Item> itemList = itemJPARepository.findByOrder(order);
        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    @Transactional
    public OrderResponse.FindByIdDTO save(User user) {
        List<Cart> carts = cartJPARepository.findAllByUserId(user.getId());
        if (carts.isEmpty()) {
            throw new Exception404("장바구니에 아무 내역도 존재하지 않습니다");
        }
        Order order = Order.builder().user(user).build();
        order = orderJPARepository.save(order);

        List<Item> itemList = new ArrayList<>();
        for (Cart cart : carts) {
            Item item = Item.builder().option(cart.getOption()).order(order).quantity(cart.getQuantity()).price(cart.getPrice()).build();
            itemJPARepository.save(item);
            itemList.add(item);
        }
        cartJPARepository.deleteByUserId(user.getId());
        return new OrderResponse.FindByIdDTO(order, itemList);
    }
}
