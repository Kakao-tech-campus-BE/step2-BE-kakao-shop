package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;


    // 5주차 과제 - 결재하기 기능 구현 (장바구니 초기화 필수 !!)
    // (기능9) 결재하기 - (주문 인서트) POST
    // /orders/save
    @PostMapping({"/orders/save"})
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.FindByIdDTO responseDTO = this.orderService.saveOrder(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }


}
