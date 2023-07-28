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

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.FindByIdDTO save(User sessionUser) {
        Order order = Order.builder().user(sessionUser).build();
        orderJPARepository.save(order);

        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(sessionUser.getId());
        List<Item> itemList = new ArrayList<>();
        for (Cart cart: cartList) {
            Item item = Item.builder().option(cart.getOption()).order(order).quantity(cart.getQuantity()).price(cart.getOption().getPrice() * cart.getQuantity()).build();
            itemList.add(item);
        }
        itemJPARepository.saveAll(itemList);

        cartJPARepository.deleteByUserId(sessionUser.getId());

        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    public OrderResponse.FindByIdDTO findById(int id, User user) {
        Order order = orderJPARepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new Exception400("주문 내역이 존재하지 않습니다.")
        );
        List<Item> itemList = itemJPARepository.findByOrderId(id);
        return new OrderResponse.FindByIdDTO(order, itemList);
    }
}
