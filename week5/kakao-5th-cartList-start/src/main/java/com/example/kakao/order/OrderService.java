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

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    public OrderResponse.SaveDTO save(User sessionUser){
        // 주문정보 저장하기
        // 1. 유저가 담고있는 카트 내역을 찾는다
        List<Cart> carts = cartJPARepository.findAllByUserId(sessionUser.getId());
        // 2. 유저가 담고있는 카트정보를 주문정보에 옮긴다
        if(carts.isEmpty()){
            throw new Exception400("장바구니에 아무것도 담겨있지 않습니다.");
        }

        // order 정보 생성 및 저장
        Order order = Order.builder().user(sessionUser).build();
        orderJPARepository.save(order);
        // 카트정보 -> 아이템으로 저장
        List<Item> items = new ArrayList<>();
        for(Cart cart : carts){
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice()).build();
            items.add(item);
            itemJPARepository.save(item);
        }
        // 3. 주문정보에 옮긴 내역을 카트에서 삭제시킨다.
        cartJPARepository.deleteByUserId(sessionUser.getId());
        return new OrderResponse.SaveDTO(order, sessionUser, items);

    }

    public OrderResponse.FindByIdDTO findById(int id, User sessionUser){
        // 유저가 주문한 오더중에 orderId = id인 주문정보 찾아서 조회

        Order order = orderJPARepository.findByIdAndUserId(id, sessionUser.getId());
        if(order == null){
            throw new Exception400("해당하는 주문정보가 존재하지 않습니다.");
        }
        List<Item> items = itemJPARepository.findAllByByOrderID(order.getId());

        return new OrderResponse.FindByIdDTO(order, items);
    }
}
