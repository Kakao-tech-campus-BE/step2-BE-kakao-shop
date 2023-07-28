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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    public OrderResponse.SaveDTO saveAll(User sessionuser){

        List<Cart> cartList = cartJPARepository.findAllByUserId(sessionuser.getId());
        //1. 카트에 아무것도 없으면 예외 처리
        if( cartList.isEmpty() ){
            throw new Exception400("카트가 비어있습니다.");
        }

        Order order = Order.builder().user(sessionuser).build();
        orderJPARepository.save(order);
        List<Item> itemList = cartList.stream()
                .map( cart -> Item.builder()
                        .option(cart.getOption())
                        .order(order)
                        .quantity(cart.getQuantity())
                        .price(cart.getPrice()).build())
                .collect(Collectors.toList());
        itemJPARepository.saveAll(itemList);

        //2. 장바구니 초기화
        cartJPARepository.deleteByUserId(sessionuser.getId());

        return new OrderResponse.SaveDTO(order,itemList);
    }

    public OrderResponse.FindByIdDTO findById(int id){

        //주문번호가 없을 경우 예외 처리
        if ( orderJPARepository.findByOrderId(id).isEmpty()){
            throw new Exception400("해당 주문번호의 주문이 없습니다.");
        }
        List<Item> itemList = itemJPARepository.findByOrderId(id);

        return new OrderResponse.FindByIdDTO(id,itemList);

    }
}
