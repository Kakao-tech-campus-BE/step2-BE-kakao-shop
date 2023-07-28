package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.errors.exception.Exception404;
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
        // 유저의 카트 리스트 가져오기 -> 가장 위로 올린 이유 : 카트가 비어있는 경우를 먼저 체크해야, order 의 부정 생성을 막을 수 있음
        // 이 카트를 비어있는 부분도 따로 함수로 빼는게 좋을지?
        List<Cart> cartList = cartJPARepository.findAllByUserId(userId);
        
        // 카트가 비어있다면
        if (cartList.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다.");
        }
        
        // 해당하는 유저 가져오기
        User user = userJPARepository.findById(userId)
                .orElseThrow(() -> new Exception404("해당하는 유저가 존재하지 않습니다. userId : " + userId));

        // order 생성 -> build 후 save 하는 부분을 따로 함수로 빼는게 좋을지?
        Order order = Order.builder()
                .user(user)
                .build();

        orderJPARepository.save(order);

        // 응답값 items 만들기 -> build 후 save 하는 부분을 따로 함수로 빼는게 좋을지?
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
                .orElseThrow(() -> new Exception404("주문 내역이 존재하지 않습니다. orderId : " + orderId));

        // 인증 -> 이런 인증 부분도 따로 함수로 빼는게 좋을지?
        if(orderId != order.getId()) {
            throw new Exception403("권한이 없습니다. orderId : " + orderId + " order.getId() : " + order.getId());
        }

        // userId 인증 -> 이런 인증 부분도 따로 함수로 빼는게 좋을지?
        if(userId != order.getUser().getId()) {
            throw new Exception403("다른 유저의 권한입니다. userId : " + userId + " order.getUser().getId() : " + order.getUser().getId());
        }
        // itemList 가져오기
        List<Item> itemList = itemJPARepository.findAllByOrderId(orderId);

        return new OrderResponse(order, itemList);

    }

}
