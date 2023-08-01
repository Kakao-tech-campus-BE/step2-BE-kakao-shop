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

    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.FindByIdDTO save(User user){
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        if(cartList.isEmpty()){
            throw new Exception400("장바구니에 아무것도 없습니다.");
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

        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    public OrderResponse.FindByIdDTO findOrderById(int id, User user){
        Order order = orderJPARepository.findById(id)
                .orElseThrow(() -> new Exception400("해당 주문이 없습니다."));
        List<Item> itemList = itemJPARepository.findByOrderId(id);

        return new OrderResponse.FindByIdDTO(order, itemList);
    }

}
