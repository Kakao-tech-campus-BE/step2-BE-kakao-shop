package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class OrderRestController {
    private final OrderService orderService;
    private final GlobalExceptionHandler globalExceptionHandler;

    // (기능12) 결재
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal User user, HttpServletRequest request) {
        return ResponseEntity.ok().body(ApiUtils.success(orderService.save(user.getId())));

    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> findById(@PathVariable int orderId,HttpServletRequest request,@AuthenticationPrincipal User user) {

        return ResponseEntity.ok().body(ApiUtils.success(orderService.findById(orderId, user.getId())));
    }
}

