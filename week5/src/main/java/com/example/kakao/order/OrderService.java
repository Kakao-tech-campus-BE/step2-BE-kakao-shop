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

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    public OrderResponse.SaveDTO save(User user) {

        // 사용자의 장바구니 조회
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
        if (cartList.size() == 0) {
            throw new Exception404("장바구니가 비어있습니다. ");
        }

        // 주문 생성 후 저장
        Order order = Order.builder().user(user).build();
        orderJPARepository.save(order);

        List<Item> itemList = new ArrayList<>();
        for (Cart cart : cartList) {
            itemList.add(Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice())
                    .build());
        }

        List <Item> items = itemJPARepository.saveAll(itemList);    // item 저장
        cartJPARepository.deleteByUserId(user.getId()); // 장바구니 비우기

        return new OrderResponse.SaveDTO(order, items);
    }

    public OrderResponse.FindByIdDTO findById(int id) {

        Order order = orderJPARepository.findById(id).orElseThrow(
                () -> new Exception404("해당 주문을 찾을 수 없습니다 : "+id)
        );

        List<Item> itemList = itemJPARepository.findAllByOrderId(id);
        return new OrderResponse.FindByIdDTO(order, itemList);

    }
}
