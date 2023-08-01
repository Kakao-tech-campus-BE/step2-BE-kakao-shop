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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    public OrderResponse.FindByIdDTO save(User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
        // 장바구니 비어있으면 예외 처리
        if(cartList.size() == 0)
            throw new Exception400("장바구니가 비어있습니다.");

        Order savedOrder = orderJPARepository.save(Order.builder().user(user).build());
        List<Item> itemList = new ArrayList<>();
        for (Cart cart : cartList) {
            Item item = Item.builder().option(cart.getOption()).order(savedOrder).quantity(cart.getQuantity()).price(cart.getPrice()).build();
            itemList.add(item);
        }
        List<Item> savedItems = itemJPARepository.saveAll(itemList);
        cartJPARepository.deleteByUserId(user.getId());
        return new OrderResponse.FindByIdDTO(savedOrder, savedItems);
    }

    public OrderResponse.FindByIdDTO findById(int id) {
        Optional<Order> order = orderJPARepository.findById(id);
        // 주문이 없을 때 예외 처리
        if(order.isEmpty())
            throw new Exception404("주문 정보가 없습니다.");

        List<Item> itemList = itemJPARepository.findByOrderId(order.get().getId());
        return new OrderResponse.FindByIdDTO(order.get(), itemList);
    }
}
