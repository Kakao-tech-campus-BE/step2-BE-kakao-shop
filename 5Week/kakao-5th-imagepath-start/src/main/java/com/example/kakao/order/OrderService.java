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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    public OrderResponse.FindByIdDTO saveOrder(User sessionUser){
        List<Cart> cartList = cartJPARepository.findAllByUserId(sessionUser.getId());
        validateCartList(cartList);

        Order order = Order.builder().user(sessionUser).build();

        orderJPARepository.save(order);

        return saveItemByOrder(order, cartList);
    }

    private OrderResponse.FindByIdDTO saveItemByOrder(Order order, List<Cart> cartList){
        List<Item> itemList = new ArrayList<>();
        for (Cart cart : cartList) {
            Item item = createItemFromCart(cart, order);
            itemJPARepository.save(item);
            itemList.add(item);
        }

        cartJPARepository.deleteAllById(
                cartList.stream().map(Cart::getId).collect(Collectors.toList())
        );

        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    public Order findById(int id){
        return orderJPARepository.findById(id).orElseThrow(
                () -> new Exception400("주문 번호를 찾을 수 없습니다")
        );
    }

    public OrderResponse.FindByIdDTO findByOrderId(Order order){
        List<Item> itemList = itemJPARepository.findByOrderId(order.getId());
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
