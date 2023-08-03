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

    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.SaveDTO Save(User user){

        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        System.out.println(cartList.size());
        if (cartList.size() == 0) throw new Exception400("장바구니에 구매할 물건이 없습니다: ");

        Order order = Order.builder().user(user).build();
        orderJPARepository.save(order);

        for (Cart cart : cartList) {
            Item item = Item.builder().order(order).option(cart.getOption()).quantity(cart.getQuantity()).price(cart.getPrice()).build();
            itemJPARepository.save(item);
        }

        System.out.println("Cart Delete 전 :" + user.getId());
        cartJPARepository.deleteByUserId(user.getId());
        System.out.println("Cart Delete 후 :" + user.getId());
        return new OrderResponse.SaveDTO(cartList);
    }

    public OrderResponse.FindByIdDTO FindById(int orderId, User user) {
        List<Item> itemList = itemJPARepository.findByOrderIdAndUserId(orderId, user.getId());
        return new OrderResponse.FindByIdDTO(itemList);
    }
}
