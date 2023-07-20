package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final FakeStore fakeStore;
    private final OrderService orderService;
    private final GlobalExceptionHandler globalExceptionHandler;

    // (기능12) 결재
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal User user, HttpServletRequest request) {
        try {
            orderService.save(user.getId());
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
//        Order order = fakeStore.getOrderList().get(0);
//        List<Item> itemList = fakeStore.getItemList();
//        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
//        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int orderId,HttpServletRequest request,@AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok().body(ApiUtils.success(orderService.findById(orderId,user.getId())));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
//        Order order = fakeStore.getOrderList().get(id-1);
//        List<Item> itemList = fakeStore.getItemList();
//        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
//        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

}
