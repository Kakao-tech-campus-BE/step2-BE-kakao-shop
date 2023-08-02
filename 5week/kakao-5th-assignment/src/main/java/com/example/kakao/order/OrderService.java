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

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    public OrderResponse.SaveDTO save(User user) {
        List<Cart> orderItemList = cartJPARepository.findAllByUserId(user.getId());

        if (orderItemList.isEmpty()){
            throw new Exception400("주문할 상품이 존재하지 않습니다. : " + orderItemList);
        }

        Order order = Order.builder().user(user).build();
        orderJPARepository.save(order);

        for (Cart cart : orderItemList){
            Item item = Item.builder().order(order).price(cart.getPrice()).option(cart.getOption()).quantity(cart.getQuantity()).build();
            itemJPARepository.save(item);
        }

        return new OrderResponse.SaveDTO(order, orderItemList);
    }

    public OrderResponse.FindByIdDTO findById(int orderItemId, User user) {

        if (orderItemId <= 0) {
            throw new Exception400("존재하지 않는 주문 아이디입니다. : " + orderItemId);
        }

        Order order = orderJPARepository.findByUserId(user.getId());

        if (order == null) {
            throw new Exception400("존재하지 않는 주문 결과입니다. : " + order);
        }

        List<Item> itemList = itemJPARepository.findAllByOrderId(order.getId());

        if (itemList.isEmpty()){
            throw new Exception400("주문의 상품 목록이 존재하지 않습니다. : " + itemList);
        }

        return new OrderResponse.FindByIdDTO(order, itemList);
    }

}
