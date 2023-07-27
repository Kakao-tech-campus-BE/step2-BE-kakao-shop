package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartService;
import com.example.kakao.order.item.Item;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final CartService cartService;
    private final OrderService orderService;


    // (기능9) 결재하기 - (주문 인서트) POST
    // /orders/save
    @PostMapping("orders/save")
    public ResponseEntity<?> save(Error errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.SaveDTO responseDTO = orderService.save(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // (기능10) 주문 결과 확인 GET
    // /orders/{id}
    @GetMapping("orders/{id}")
    public ResponseEntity<?> findById(Error errors, @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int id) {
        OrderResponse.FindByIdDTO responseDTO = orderService.findById(userDetails.getUser(), id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}
