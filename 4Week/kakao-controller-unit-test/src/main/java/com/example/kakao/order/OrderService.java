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
        List<Cart> cartList;
        List<Item> itemList;

        try {
            cartList = cartJPARepository.findAll();
        }catch (Exception e){
            throw new Exception500("unknown server error");
        }

        if(cartList.isEmpty()){
            throw new Exception400("장바구니의 상품을 조회할 수 없습니다");
        }else{
            for (Cart cart : cartList) {
                Item item = Item.builder()
                        .option(cart.getOption())
                        .quantity(cart.getQuantity())
                        .order(order)
                        .price(cart.getOption().getPrice() * cart.getQuantity())
                        .build();

                try {
                    itemJPARepository.save(item);
                }catch (Exception e){
                    throw new Exception500("unknown server error");
                }
            }
        }

        try {
            itemList = itemJPARepository.mFindByOrderId(order.getId());
        }catch (Exception e){
            throw new Exception500("unknown server error");
        }

        return itemList;
    }

    public OrderResponse.FindByIdDTO findByIdDTO(Order order, List<Item> itemList){
        return new OrderResponse.FindByIdDTO(order, itemList);
    }


}

