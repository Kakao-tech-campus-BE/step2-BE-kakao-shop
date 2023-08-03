package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.errors.exception.Exception404;
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
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    @Transactional
    public OrderResponse.FindByIdDTO save(User user){
        List<Cart> carts = cartJPARepository.findAllByUserId(user.getId());

        //1. 사용자 장바구니가 비어있을 경우
        if(carts.isEmpty()){
            throw new Exception400("사용자의 장바구니가 비어있습니다.");
        }

        Order order = Order.builder().user(user).build();
        orderJPARepository.save(order);

        List<Item> items = carts.stream()
                .map(cart -> Item.builder()
                        .option(cart.getOption())
                        .order(order)
                        .quantity(cart.getQuantity())
                        .price(cart.getPrice())
                        .build())
                .collect(Collectors.toList());
        itemJPARepository.saveAll(items);
        cartJPARepository.deleteByUserId(user.getId());
        return new OrderResponse.FindByIdDTO(order, items);
    }

    public OrderResponse.FindByIdDTO findById(int id, User user){
        Order order = orderJPARepository.findById(id).orElseThrow(
                () -> new Exception404("주문을 찾을 수 없습니다. : " + id)
        );

        if(user.getId() != order.getUser().getId()){
            throw new Exception403("주문 조회를 할 수 있는 권한이 없습니다.");
        }

        List<Item> items = itemJPARepository.findByOrderId(order.getId());
        return new OrderResponse.FindByIdDTO(order, items);
    }
}

