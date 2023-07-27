package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception401;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.OrderItem;
import com.example.kakao.order.item.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class OrderRestController {
    // 유효성 검사 추가하기
    // error 잡기

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    // url 수정
    // (기능12) 결재
    @PostMapping("/orders")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            Exception401 ex = new Exception401("인증되지 않았습니다"+userDetails);
            return new ResponseEntity<>(
                            ex.body(),
                            ex.status()
            );
        }

        //Order order = fakeStore.getOrderList().get(0);
        Order order = orderService.findAll().get(0);

        if(order == null){
            Exception404 ex = new Exception404("해당 주문을 찾을 수 없습니다.:"+ 0);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        //List<OrderItem> orderItemList = fakeStore.getOrderItemList();
        List<OrderItem> orderItemList = orderItemService.findAll();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, orderItemList);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
        //return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        if (id <= 0 || id >= Math.pow(2,32)) {
            Exception404 ex = new Exception404("유효하지 않은 주문 ID입니다. ID: " + id);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }
        //Order order = fakeStore.getOrderList().get(id-1);
        Order order = orderService.findById(id-1);

        if(order == null){
            Exception404 ex = new Exception404("해당 주문을 찾을 수 없습니다.:"+id);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        //List<OrderItem> orderItemList = fakeStore.getOrderItemList();
        List<OrderItem> orderItemList = orderItemService.findAll();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, orderItemList);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

}
