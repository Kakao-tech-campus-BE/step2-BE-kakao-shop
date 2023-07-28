package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class OrderRestController {

	private final GlobalExceptionHandler globalExceptionHandler;
	private final OrderService orderService;

    // (기능12) 결재
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
    	try {
    		OrderResponse.FindByIdDTO responseDTO = orderService.saveOrder();
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
    	} catch(RuntimeException e) {
    		return globalExceptionHandler.handle(e, request);
    	}
    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, HttpServletRequest request) {
    	try {
    		OrderResponse.FindByIdDTO responseDTO = orderService.getOrder(id);
            return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    	} catch(RuntimeException e) {
    		return globalExceptionHandler.handle(e, request);
    	}
    	
    }

}
