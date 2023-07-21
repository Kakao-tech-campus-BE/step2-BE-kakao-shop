package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductResponse;
import com.example.kakao.user.UserRequest;
import com.example.kakao.user.UserResponse;
import com.example.kakao.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final FakeStore fakeStore;
    private final OrderService orderService;

    // (기능12) 결재
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        //주문 1개를 하는 과정은 장바구니db에서 내역을 주문save로 가져오는 것이지만 fakeStore(db대체)에서 가져오는 걸로 대체
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();

        OrderResponse.FindByIdDTO requestDTO = new OrderResponse.FindByIdDTO(order, itemList);
        //1.유효성 검사 - 유저의 장바구니의 품목들을 그대로 가져오기 때문에 request바디가 없고 유효성 검사 x

        //2.권한 체크 - jwt필터가 security에 존재하기 때문에 자동으로 걸러질 예정

        //3.서비스 실행 : 주문을 db에 저장하는 서비스 로직을 실행하기 위해 dto를 서비스단으로 던짐.
        //서비스가 정상적으로 실행되고 db에 저장이 되었다면 orders/save api문서에 따라 주문한 내역을 출력해준다.
        //정상 실행되지 않았다면 service부분에서 오류를 던진다.
        OrderResponse.FindByIdDTO responseDTO = orderService.save(requestDTO);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));

    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Order order = fakeStore.getOrderList().get(0);
        //1.유효성 검사
        //get mapping이기 때문에 int가 아니면 걸러지고, request body는 따로 없기 때문에 안해도 될 것 같다.
        // 조회한 id의 주문이 존재하지 않을 때 발생 - service

        //2.권한 체크 - jwt필터가 security에 존재하기 때문에 자동으로 걸러질 예정

        //3.서비스 실행 : 내부에서 터지는 모든 익셉션은 예외 핸들러로 던지기

        OrderResponse.FindByIdDTO responseDTO = orderService.findById(id);

        //정상 가동 시 responseDTO를 반환
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }
}
