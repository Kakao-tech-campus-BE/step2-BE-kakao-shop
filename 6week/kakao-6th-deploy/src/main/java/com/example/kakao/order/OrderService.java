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
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.SaveDTO save(User user){
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다.");
        }

        Order order = Order.builder().user(user).build();
        orderJPARepository.save(order);
        List<Item> itemList = new ArrayList<>();
        for (Cart cart : cartList) {
            Item item = Item.builder().option(cart.getOption()).order(order).quantity(cart.getQuantity()).price(cart.getPrice()).build();
            itemList.add(item);
            itemJPARepository.save(item);
        }

        //장바구니 초기화
        cartJPARepository.deleteByUserId(user.getId());

        return new OrderResponse.SaveDTO(order, itemList);
    }

    @Transactional
    public OrderResponse.findByIdDTO findById(User user, int id){
        Optional<Order> orderOP = orderJPARepository.findByIdAndUserId(id, user.getId());
        if (orderOP.isPresent()){
            Order orderPS = orderOP.get();
            List<Item> itemList = itemJPARepository.findAllByOrderId(id);
            return new OrderResponse.findByIdDTO(orderPS, itemList);
        }
        else {
            throw new Exception404("해당 주문을 찾을 수 없습니다 : " + id);
        }
    }

}
