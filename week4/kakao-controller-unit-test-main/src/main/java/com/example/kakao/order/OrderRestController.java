package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final OrderService orderService;

    // (기능12) 결제
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        try{
            OrderResponse.FindByIdDTO responseDTO = orderService.save(userDetails);
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }
        catch(RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, HttpServletRequest request) {
        try{
            OrderResponse.FindByIdDTO responseDTO = orderService.findById(id);
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }catch(Exception400 e){
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }catch(RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
    }
}
