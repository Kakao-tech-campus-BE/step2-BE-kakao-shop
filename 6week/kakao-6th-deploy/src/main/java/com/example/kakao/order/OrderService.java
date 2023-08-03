package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception403;
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
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    @Transactional
    public OrderResponse.SaveDTO saveAll(User sessionuser){

        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(sessionuser.getId());
        //1. 카트에 아무것도 없으면 예외 처리
        if( cartList.isEmpty() ){
            throw new Exception400("카트가 비어있습니다.");
        }

        Order order = Order.builder().user(sessionuser).build();
        Order orderPS = orderJPARepository.save(order);
        List<Item> itemList = cartList.stream()
                .map( cart -> Item.builder()
                        .option(cart.getOption())
                        .order(orderPS)
                        .quantity(cart.getQuantity())
                        .price(cart.getPrice())
                        .build())
                .collect(Collectors.toList());

        List<Item> itemListPS = itemJPARepository.saveAll(itemList);

        //2. 장바구니 초기화
        cartJPARepository.deleteAll(cartList);

        return new OrderResponse.SaveDTO(orderPS,itemListPS);
    }

    @Transactional
    public OrderResponse.FindByIdDTO findById(int id,User sessionuser){
//        Order order = orderJPARepository.findById(id).orElseThrow(() -> new Exception404("존재하지 않는 주문 번호입니다."));
//        if (order.getUser().getId() != sessionuser.getId()) {
//            throw new Exception403("현재 계정으로 해당 주문을 조회할 수 없습니다.");
//        }
//
//        List<Item> items = itemJPARepository.findByOrderId(id);
//        return new OrderResponse.FindByIdDTO(order, items);
        //주문번호가 없을 경우 예외 처리
        Order order = orderJPARepository.findById(id).orElseThrow(() -> new Exception404("해당 주문번호의 주문이 없습니다."));

        List<Item> itemList = itemJPARepository.findByOrderId(id);

        return new OrderResponse.FindByIdDTO(order,itemList);

    }
}
