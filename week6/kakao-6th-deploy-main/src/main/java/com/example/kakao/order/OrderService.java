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
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    @Transactional
    public OrderResponse.FindByIdDTO save(User user) {
        List<Cart> cartList = cartJPARepository.findByUserId(user.getId());

        if(cartList.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다.");
        }

        Order order = saveOrder(user);
        List<Item> itemList = saveItemList(cartList, order);
        cartJPARepository.deleteByUserId(user.getId());

        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    private Order saveOrder(User user) {
        return orderJPARepository.save(Order.builder().user(user).build());
    }

    private List<Item> saveItemList(List<Cart> cartList, Order order) {
        return itemJPARepository.saveAll(cartList.stream()
                .map(cart -> Item.builder()
                        .option(cart.getOption())
                        .order(order)
                        .quantity(cart.getQuantity())
                        .price(cart.getPrice())
                        .build())
                .collect(Collectors.toList()));
    }

    public OrderResponse.FindByIdDTO findById(int id, User user) {
        Order order = orderJPARepository.findById(id).orElseThrow(
                () -> new Exception400("해당 주문 내역을 찾을 수 없습니다.")
        );

        List<Item> itemList = itemJPARepository.mFindByOrderId(id);

        return new OrderResponse.FindByIdDTO(order, itemList);
    }
}