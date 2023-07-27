package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.Cart;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final FakeStore fakeStore;

    public OrderResponse.FindByIdDTO save() {
        // 장바구니아이템 조회
        List<Cart> cartListPS = fakeStore.getCartList();
        // 장바구니아이템 유효성 검사
        if(cartListPS.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다");
        }
        // 주문 추가
        Order orderPS = fakeStore.getOrderList().get(0);
        // 주문아이템 추가
        List<Item> itemListPS = fakeStore.getItemList();
        // DTO 변환
        return new OrderResponse.FindByIdDTO(orderPS, itemListPS);
    }

    public OrderResponse.FindByIdDTO findById(int id) {
        // 주문 조회
        Order orderPS = fakeStore.getOrderList().stream().filter(order -> order.getId() == id).findFirst().orElse(null);
        // 주문 유효성 검사
        if(orderPS == null) {
            throw new Exception404("해당 주문을 찾을 수 없습니다:"+id);
        }
        // 주문아이템 조회
        List<Item> itemListPS = fakeStore.getItemList().stream().filter(item -> orderPS.getId() == item.getOrder().getId()).collect(Collectors.toList());

        // DTO 변환
        return new OrderResponse.FindByIdDTO(orderPS, itemListPS);
    }
}
