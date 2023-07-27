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
@RequestMapping("/orders")
public class OrderRestController {

    private final OrderService orderService;


    // (기능9) 결재하기 - (주문 인서트) POST
    // /orders/save
    // (기능10) 주문 결과 확인 GET
    @PostMapping("/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.FindAllDTO responseDTO = orderService.orderAll(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // /orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.FindByIdDTO responseDTO = orderService.findById(id, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}
