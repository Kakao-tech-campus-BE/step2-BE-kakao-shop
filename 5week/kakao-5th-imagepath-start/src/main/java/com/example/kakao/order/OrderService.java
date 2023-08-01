package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    //주문 저장
    @Transactional
    public OrderResponse.SaveDTO saveOrder(User sesssionUser){
        List<Cart> carts = cartJPARepository.findAll();
        //장바구니가 비었을 때
        if (carts.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다.");
        }

        Order order = Order.builder().user(sesssionUser).build();
        Order savedOrder = orderJPARepository.save(order);

        List<Item> items = carts.stream().map(cart -> {
            Option option = cart.getOption();
            int quantity = cart.getQuantity();
            int price = cart.getPrice();

            return Item.builder()
                    .option(option)
                    .order(savedOrder)
                    .quantity(quantity)
                    .price(price)
                    .build();
        }).collect(Collectors.toList());

        List<Item> savedItems = itemJPARepository.saveAll(items);
        cartJPARepository.deleteAll();

        return new OrderResponse.SaveDTO(savedOrder, savedItems);
    }

    //id찾기
    @Transactional
    public OrderResponse.findByIdDTO findById(int orderId, User sessionUser){
        Order order = orderJPARepository.findById(orderId)
                .orElseThrow(() -> new Exception400("주문번호가 존재하지 않습니다"));
        List<Item> items = itemJPARepository.mfindByOrderId(orderId);
        return new OrderResponse.findByIdDTO(Optional.of(order), items);
    }
}
