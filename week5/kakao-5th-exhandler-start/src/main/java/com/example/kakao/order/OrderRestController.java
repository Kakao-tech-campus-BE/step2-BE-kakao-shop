package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.order.item.Item;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;
    // (기능9) 결재하기 - (주문 인서트) POST
    // /orders/save
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        //save로직을 할 때, 화면의 요소를 통해 request를 받거나, api를 재호출하거나 둘 중 하나의 방법을 사용할 수 있을 것 같다.
        //하지만 request를 받으면 검증절차를 다시 거쳐야하기 때문에 비교적 안전하게 cart db에 저장된 데이터를 활용하는게 좋을 것 같다.
        OrderResponse.FindByIdDTO responseDTO = orderService.save(userDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    // (기능10) 주문 결과 확인 GET
    // /orders/{id}
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        //요청한 사람의 주문 결과를 출력해야 하므로 유저id와 주문id를 검증할 수 있는 서비스인 findById로 넘긴다.
        OrderResponse.FindByIdDTO responseDTO = orderService.findById(userDetails.getUser(), id);
        //정상 가동 시 responseDTO를 반환
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

}
