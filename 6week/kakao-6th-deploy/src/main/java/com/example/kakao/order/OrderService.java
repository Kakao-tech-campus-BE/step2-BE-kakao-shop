package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;

    private final ItemJPARepository itemJPARepository;
    @Transactional
    public OrderResponse.SaveDTO saveOrder(User user) {
        Order order = Order.builder().user(user).build();
        orderJPARepository.save(order);

        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        for(Cart cart : cartList){
            Item item = Item.builder().option(cart.getOption()).order(order).quantity(cart.getQuantity()).price(cart.getPrice()).build();
            itemJPARepository.save(item);
        }

        cartJPARepository.deleteByUserId(user.getId());

        List<Item> itemList = itemJPARepository.findAllByOrderId(order.getId());
        return new OrderResponse.SaveDTO(itemList);
    }

    public OrderResponse.FindByIdDTO findById(int id) {
        Optional<Order> order = orderJPARepository.findById(id);
        if(order.isEmpty()) throw new Exception403("존재하지 않는 주문번호 입니다");

        List<Item> itemList = itemJPARepository.findAllByOrderId(id);
        return new OrderResponse.FindByIdDTO(itemList);
    }
}
