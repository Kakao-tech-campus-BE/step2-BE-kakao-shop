package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final FakeStore fakeStore;

    // (기능12) 결제
    // save를 없애기
    //@PostMapping("/orders/save")
    // 궁금한 점 : 메소드 명 바꾸기? 좀 더 직관적인 메소드 명 ex. save -> createOrder
    @PostMapping("/orders")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Order order = fakeStore.getOrderList().get(0);
        List<OrderItem> orderItemList = fakeStore.getOrderItemList();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, orderItemList);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // (기능13) 주문 결과 확인
    // 메소드 명 바꾸기 : 좀 더 직관적인 메소드 명으로 ex. findById -> getOrderById
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Order order = fakeStore.getOrderList().get(id-1);
        List<OrderItem> orderItemList = fakeStore.getOrderItemList();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, orderItemList);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

}
