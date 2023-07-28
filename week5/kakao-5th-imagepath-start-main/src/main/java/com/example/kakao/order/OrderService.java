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

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.SaveDTO save(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByCartIdOptionIdAsc(user.getId());
        if (cartList.isEmpty()){
            throw new Exception400("장바구니가 비어있습니다.");
        }
        Order mOrder = Order.builder().user(user).build();
        orderJPARepository.save(mOrder);

        List<Item> itemList = cartList.stream().map(
                cart -> Item.builder()
                        .id(cart.getId())
                        .order(mOrder)
                        .price(cart.getPrice())
                        .option(cart.getOption())
                        .quantity(cart.getQuantity())
                        .build()
        ).collect(Collectors.toList());

        itemJPARepository.saveAll(itemList);
        cartJPARepository.deleteByUserId(user.getId());
        return new OrderResponse.SaveDTO(mOrder, itemList);
    }

    public OrderResponse.FindByIdDTO findById(int id, User user) {
        Order order = orderJPARepository.findByOrderId(id, user.getId()).orElseThrow(
                () -> new Exception404("해당 주문을 찾을 수 없습니다.")
        );
        List<Item> itemList = itemJPARepository.findByOrderId(order.getId());
        return new OrderResponse.FindByIdDTO(order, itemList);
    }
}
