package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/orders")
@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;

    // (기능9) 결재하기 - (주문 인서트) POST
    // /orders/save
    @PostMapping("/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        OrderResponse.SaveDTO saveDTO = orderService.save(customUserDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(saveDTO));
    }

    // (기능10) 주문 결과 확인 GET
    // /orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        OrderResponse.FindByIdDTO findByIdDTO = orderService.findById(id);
        return ResponseEntity.ok().body(ApiUtils.success(findByIdDTO));
    }
}
