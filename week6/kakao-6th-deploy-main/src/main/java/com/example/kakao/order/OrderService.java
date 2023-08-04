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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;

    @Transactional
    public OrderResponse.saveDTO save(User sessionUser)
    {
        Order order = Order.builder().user(sessionUser).build();

        //order 저장
        orderJPARepository.save(order);

        List<Cart> cartItemList = cartJPARepository.findAllByUserId(sessionUser.getId());
        for(Cart cart : cartItemList)
        {
            Item item = Item.builder().option(cart.getOption()).order(order).quantity(cart.getQuantity()).price(cart.getPrice()).build();
            itemJPARepository.save(item);
        }

        List<Item> itemList = itemJPARepository.findAllByOrderId(order.getId());

        //카트초기화
        cartJPARepository.deleteByUserId(sessionUser.getId());
        return new OrderResponse.saveDTO(order, itemList);
    }

    @Transactional
    public OrderResponse.saveDTO findById(int id)
    {
        Order order = orderJPARepository.findById(id).orElseThrow(() -> new Exception404("존재하지않는 orderId 입니다"));

        List<Item> itemList = itemJPARepository.findAllByOrderId(id);
        return new OrderResponse.saveDTO(order, itemList);
    }
}