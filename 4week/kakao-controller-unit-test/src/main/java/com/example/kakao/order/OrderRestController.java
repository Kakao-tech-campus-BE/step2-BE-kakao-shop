package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
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

    private final GlobalExceptionHandler globalExceptionHandler;
    private final FakeStore fakeStore;

    // (기능12) 결재
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        try {
            List<Order> orders = fakeStore.getOrderList();
            //order이 비었거나 null이거나
            if(orders==null || orders.isEmpty()){
                throw new Exception404("결제를 진행할 주문이 존재하지 않습니다.");
            }
            List<Item> itemList = fakeStore.getItemList();
            if (itemList==null){
                throw new Exception404("주문 내에 주문상품이 존재하지 않습니다.");
            }
            OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(orders.get(0), itemList);
            System.out.println("테스트: " + responseDTO);
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e,request);
        }
    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 1. 더미데이터 가져오기
        Order order = fakeStore.getOrderList().stream().filter(o -> o.getId() == id).findFirst().orElse(null);
        // 2. null 체크
        if(order==null){
            Exception404 ex = new Exception404("해당 주문을 찾을 수 없습니다:"+id);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        List<Item> itemList = fakeStore.getItemList();

        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

}
