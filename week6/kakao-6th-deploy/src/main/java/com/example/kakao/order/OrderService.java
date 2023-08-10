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

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.SaveDTO save(User user) {
        // 1. 장바구니가 비어있으면 예외처리
        List<Cart> cartListPS = cartJPARepository.mFindAllByUserIdOrderByOptionIdAsc(user.getId());    // 쿼리문O
        if (cartListPS.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다");
        }

        // 2. 주문하기
        // 주문 생성
        Order order = Order.builder().user(user).build();
        orderJPARepository.save(order); // 쿼리문O

        List<Item> itemListPS = new ArrayList<>();
        for (Cart cartPS : cartListPS) {
            // 주문아이템 생성
            Item item = Item.builder()
                    .order(order)
                    .option(cartPS.getOption()) // option > product : join되어있음
                    .quantity(cartPS.getQuantity())
                    .price(cartPS.getPrice())
                    .build();
            Item itemPS = itemJPARepository.save(item);   // 쿼리문O
            itemListPS.add(itemPS);

            // 장바구니 비우기
            cartJPARepository.deleteById(cartPS.getId());   // 쿼리문O
        }

        return new OrderResponse.SaveDTO(itemListPS);
    }

    public OrderResponse.FindByIdDTO findById(int id, User user) {
        // 1. 유효하지 않은 주문이면 예외처리
        Order orderPS = orderJPARepository.findById(id).orElseThrow(    // 쿼리문O
                () -> new Exception404("해당 주문을 찾을 수 없습니다 : "+id)
        );

        // 2. 유저의 주문이 아니면 예외처리
        if (orderPS.getUser().getId() != user.getId()) {
            throw new Exception403("접근할 수 없습니다 : "+id);
        }

        // 3. 주문 결과 확인
        List<Item> itemListPS = itemJPARepository.mFindAllByOrderId(orderPS.getId());   // 쿼리문O

        return new OrderResponse.FindByIdDTO(itemListPS);
    }
}