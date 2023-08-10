package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception401;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.cart.CartResponse;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;


    @Transactional
    public OrderResponse.SaveDTO save(User user) {
        List<Cart> orderItemList = cartJPARepository.findAllByUserId(user.getId());

        if (orderItemList.isEmpty()){
            throw new Exception400("EMPTY_CART:" + orderItemList);
        }

        Order order = Order.builder().user(user).build();
        orderJPARepository.save(order);

        for (Cart cart : orderItemList){
            //Item item = Item.builder().order(order).price(cart.getPrice()).option(cart.getOption()).quantity(cart.getQuantity()).build();
            Item item = Item.builder().order(order).price(cart.getOption().getPrice()*cart.getQuantity()).option(cart.getOption()).quantity(cart.getQuantity()).build();
            itemJPARepository.save(item);
        }

        cartJPARepository.deleteByUserId(user.getId());

        return new OrderResponse.SaveDTO(order, orderItemList);
    }

    @Transactional(readOnly = true)
    public OrderResponse.FindByIdDTO findById(int orderItemId, User user) {

        if (orderItemId <= 0) {
            throw new Exception400("INVALID_ORDER_ID:" + orderItemId);
        }

        Order order= orderJPARepository.findByUserIdAndOrderItemId(user.getId(), orderItemId);


        if (order == null) {
            throw new Exception400("ORDER_NOT_FOUND:" + order);
        }

        List<Item> itemList = itemJPARepository.findAllByOrderId(order.getId());

        return new OrderResponse.FindByIdDTO(order, itemList);
    }

}
