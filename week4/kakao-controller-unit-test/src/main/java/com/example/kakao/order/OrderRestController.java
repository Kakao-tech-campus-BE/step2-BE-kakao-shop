package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {
    private final CartService cartService;
    private final OrderService orderService;

    // (기능12) 결재
    @PostMapping("/orders/save")
    @Transactional
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Cart> carts = cartService.findAllByUserInRaw(userDetails.getUser());

        if (carts.isEmpty()) {
            return ResponseEntity.ok().body(ApiUtils.error("장바구니가 비어있습니다.", HttpStatus.FORBIDDEN));
        }

        OrderResponse.FindByIdDTO responseDTO;responseDTO = orderService.save(userDetails.getUser(), carts);
        cartService.deleteAllByUser(userDetails.getUser());

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        OrderResponse.FindByIdDTO responseDTO;

        try {
            responseDTO = orderService.findById(id);
        }
        catch (Exception404 exception404) {
            return ResponseEntity.ok().body(ApiUtils.error("존재하지 않는 주문번호 입니다.", HttpStatus.NOT_FOUND));
        }

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

}
