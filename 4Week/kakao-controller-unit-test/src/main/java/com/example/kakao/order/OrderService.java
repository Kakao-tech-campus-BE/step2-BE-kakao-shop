package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
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
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    public Order saveOrder(CustomUserDetails userDetails) {
        Order order = Order.builder()
                .user(userDetails.getUser())
                .build();

        try {
            orderJPARepository.save(order);
        }catch (Exception e){
            throw new Exception500("unknown server error");
        }

        return order;
    }


    public List<Item> saveItemByOrder(Order order) {
        List<Cart> cartList = findAllCarts();
        validateCartList(cartList);

        List<Item> itemList = new ArrayList<>();
        for (Cart cart : cartList) {
            Item item = createItemFromCart(cart, order);
            saveItem(item);
            itemList.add(item);
        }

        return itemList;
    }

    public OrderResponse.FindByIdDTO findByIdDTO(Order order, List<Item> itemList){
        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    public Order findById(int id){
        return orderJPARepository.findById(id).orElseThrow(
                () -> new Exception400("주문 번호를 찾을 수 없습니다")
        );
    }

    public List<Item> findByOrderId(int orderId){
        List<Item> itemList;

        try {
            itemList = itemJPARepository.FindByOrderId(orderId);
        }catch (Exception e){
            throw new Exception500("unknown server error");
        }

        return itemList;
    }

    private List<Cart> findAllCarts(){
        try {
            return cartJPARepository.findAll();
        }catch (Exception e){
            throw new Exception500("unknown server error");
        }
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

    private void saveItem(Item item) {
        try {
            itemJPARepository.save(item);
        } catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }

    private void validateCartList(List<Cart> cartList){
        if(cartList.isEmpty()){
            throw new Exception400("장바구니의 상품을 조회할 수 없습니다");
        }
    }
}

