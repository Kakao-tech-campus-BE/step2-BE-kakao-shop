package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        System.out.println(itemList);

        //카트초기화
        cartJPARepository.deleteByUserId(sessionUser.getId());
        return new OrderResponse.saveDTO(itemList);
    }
}
