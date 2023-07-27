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

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    public Order saveOrder(User sessionUser){
        Order order = Order.builder().user(sessionUser).build();

        orderJPARepository.save(order);

        return order;
    }

    public OrderResponse.FindByIdDTO saveItemByOrder(Order order){
        List<Cart> cartList = cartJPARepository.findAllByUserId(order.getUser().getId());
        validateCartList(cartList);

        List<Item> itemList = new ArrayList<>();
        for (Cart cart : cartList) {
            Item item = createItemFromCart(cart, order);
            itemJPARepository.save(item);
            itemList.add(item);
        }

        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    private Item createItemFromCart(Cart cart, Order order) {
        int quantity = cart.getQuantity();
        int price = cart.getOption().getPrice() * quantity;

        return Item.builder()
                .option(cart.getOption())
                .quantity(quantity)
                .order(order)
                .price(price)
                .build();
    }

    private void validateCartList(List<Cart> cartList){
        if(cartList.isEmpty()){
            throw new Exception400("장바구니의 상품을 조회할 수 없습니다");
        }
    }
}
