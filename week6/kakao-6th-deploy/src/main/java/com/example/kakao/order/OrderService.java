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


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    public OrderResponse.FindByIdDTO findById(int id, User user){
        List<Order> orderList = orderJPARepository.findByUserId(user.getId());
        //해당 사용자의 주문 내역이 없을 경우
        if(orderList.isEmpty()){
            throw new Exception400("해당 사용자의 주문 내역이 없습니다 : " + user.getId());
        }
        Order order = orderList.get(id - 1);
        List<Item> itemList = itemJPARepository.findByOrderId(order.getId());
        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    @Transactional
    public int save(User user){
        Order order = Order.builder().user(user).build();
        List<Cart> cartItemList = cartJPARepository.findAllByUserId(user.getId());
        //장바구니 초기화
        cartJPARepository.deleteByUserId(user.getId());
        for(Cart cart : cartItemList){
            itemJPARepository.save(Item.builder().option(cart.getOption()).order(order).quantity(cart.getQuantity()).price(cart.getPrice()).build());
        }
        orderJPARepository.save(order);

        return order.getId();
    }
}