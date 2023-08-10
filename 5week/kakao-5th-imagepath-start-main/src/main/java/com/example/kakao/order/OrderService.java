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

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;


    @Transactional
    public OrderResponse.FindByIdDTO save(User sessionUser){
        List<Cart> cartList = cartJPARepository.findAllByUserId(sessionUser.getId());
        if (cartList.isEmpty()){
            throw new Exception400("장바구니가 비어있습니다.");
        }

        Order order = Order.builder()
                .user(sessionUser)
                .build();
        orderJPARepository.save(order);

        List<Item> items = new ArrayList<>();

        for(Cart cart: cartList){
            Item item = Item.builder()
                    .price(cart.getOption().getPrice() * cart.getQuantity())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .option(cart.getOption())
                    .build();
            items.add(item);
        }

        itemJPARepository.saveAll(items);
        cartJPARepository.deleteByUserId(sessionUser.getId());

        return new OrderResponse.FindByIdDTO(order, items);
    }

    public OrderResponse.FindByIdDTO findById(int id, User sessionUser){
        Order order = orderJPARepository.findByIdAndUserId(id, sessionUser.getId()).orElseThrow(
                () -> new Exception404("해당 주문을 찾을 수 없습니다. : " + id)
        );
        List<Item> items = itemJPARepository.findAllByIdJoinOptionAndProducts(id);
        return new OrderResponse.FindByIdDTO(order, items);
    }
}
