package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;

    // (기능12) 결재
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.FindByIdDTO responseDTO = orderService.save();
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        OrderResponse.FindByIdDTO responseDTO = orderService.findById(id);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

}
