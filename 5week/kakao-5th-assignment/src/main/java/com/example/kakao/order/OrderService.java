package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.CartResponse;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.option.Option;
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

    public OrderResponse.SaveDTO save(User user) {
        List<Cart> orderItemList = cartJPARepository.findAllByUserId(user.getId());
        Order order = Order.builder().user(user).build();
        return new OrderResponse.SaveDTO(order, orderItemList);
    }



}
