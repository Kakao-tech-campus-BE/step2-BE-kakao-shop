package com.example.kakao.order;

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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    public OrderResponse.SaveDTO save(User sessionUser) {
        // 1. 사용자 장바구니 가져오기
        List<Cart> carts = cartJPARepository.findAllByUserId(sessionUser.getId());
        if (carts.isEmpty()){
            throw new Exception404("장바구니가 비어있습니다.");
        }

        // 2. 주문 생성
        Order order = Order.builder()
                .user(sessionUser)
                .build();
        orderJPARepository.save(order);

        // 3. 아이템 리스트 생성
        List<Item> items = new ArrayList<>();
        for (Cart cart : carts){
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice())
                    .build();
            itemJPARepository.save(item);
            items.add(item);
        }

        // 4. 장바구니 비우기
        cartJPARepository.deleteByUserId(sessionUser.getId());

        return new OrderResponse.SaveDTO(items);
    }

    public OrderResponse.FindByIdDTO findById(int id, User sessionUser) {
        Order order = orderJPARepository.findById(id).orElseThrow(
                () -> new Exception404("해당 주문 내역을 찾을 수 없습니다 :" + id)
        );

        // 주문의 user와 세션 유저가 일치하지 않는 경우 403에러
        if (order.getUser().getId() != sessionUser.getId()){
            throw new Exception403("접근할 수 없는 주문내역입니다 :" + id);
        }

        List<Item> items = itemJPARepository.findAllByOrderId(order.getId());

        return new OrderResponse.FindByIdDTO(items);
    }
}