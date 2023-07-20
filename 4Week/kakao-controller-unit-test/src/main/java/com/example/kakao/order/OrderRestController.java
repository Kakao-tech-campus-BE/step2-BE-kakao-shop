package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final FakeStore fakeStore;
    private final OrderService orderService;
    private final GlobalExceptionHandler globalExceptionHandler;

    // (기능12) 결재
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        Order order;
        List<Item> itemList;

        try {
            order = orderService.saveOrder(userDetails);
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }

        try {
            itemList = orderService.saveItemByOrder(order);
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }

        OrderResponse.FindByIdDTO responseDTO = orderService.findByIdDTO(order, itemList);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, HttpServletRequest request) {
        Order order;
        List<Item> itemList;

        try {
            order = orderService.findById(id);
            itemList = orderService.findByOrderId(order.getId());
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
        OrderResponse.FindByIdDTO responseDTO = orderService.findByIdDTO(order, itemList);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

}
