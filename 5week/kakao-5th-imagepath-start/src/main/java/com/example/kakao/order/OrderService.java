package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final UserJPARepository userJPARepository;
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    // 결재하기 기능 구현 ( 장바구니 초기화 )
    @Transactional
    public OrderResponse saveOrderList(int userId) {
        // 해당하는 유저 가져오기
        User user = userJPARepository.findById(userId)
                .orElseThrow(() -> new Exception400("해당하는 유저가 존재하지 않습니다. userId : " + userId));


        // order 생성
        Order order = Order.builder()
                .user(user)
                .build();

        orderJPARepository.save(order);

        // 유저의 카트 리스트 가져오기
        List<Cart> cartList = cartJPARepository.findAllByUserId(userId);

        // 응답값 items 만들기
        List<Item> itemList = cartList.stream()
                .map(cart -> Item.builder()
                        .order(order)
                        .option(cart.getOption())
                        .quantity(cart.getQuantity())
                        .price(cart.getPrice())
                        .build())
                .collect(Collectors.toList());
        
        // 저장
        itemJPARepository.saveAll(itemList);
        
        // 장바구니 초기화
        cartJPARepository.deleteAllByUserId(userId);

        return new OrderResponse(order, itemList);
    }

    @Transactional
    public OrderResponse findById(int orderId, int userId) {
        Order order = orderJPARepository.findById(orderId)
                .orElseThrow(() -> new Exception400("주문 내역이 존재하지 않습니다. orderId : " + orderId));

        // 인증
        if(orderId != userId) {
            throw new Exception400("권한이 없습니다. orderId : " + orderId);
        }
        // itemList 가져오기
        List<Item> itemList = itemJPARepository.findAllByOrderId(orderId);

        return new OrderResponse(order, itemList);

    }

}
