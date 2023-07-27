package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final FakeStore fakeStore;

    private final CartService cartService;

    // (기능8) 장바구니 담기
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody List<CartRequest.SaveDTO> requestDTOs, Errors errors,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        //입력값 예시 [{"optionId":1, "quantity":5},{"optionId":2, "quantity":5}]
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 e = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }
        //authentiation로직에 따라 인증하지 않은 유저는 add할 수 없음.
        //서비스 로직 작동
        requestDTOs.forEach(
                saveDTO -> System.out.println("요청 받은 장바구니 옵션 : "+saveDTO.toString())
        );
        cartService.addCartList(requestDTOs);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Cart> cartList = fakeStore.getCartList();
        //파라미터나 body입력값이 없기 때문에 유효성검증은 하지 않음
        //2.권한 체크 - jwt필터가 security에 존재하기 때문에 자동으로 걸러질 예정
        //인증된 유저의 id를 통해 장바구니를 탐색함. findByUserId.
        CartResponse.FindAllDTO responseDTO = cartService.findAll();
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // (기능11) 주문하기 - (장바구니 업데이트)
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs,Errors errors,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        //입력값 예시 [{"cartId":1, "quantity":5},{"cartId":2, "quantity":5}]
        //UPDATE할 requestBody의 값
        requestDTOs.forEach(
                updateDTO -> System.out.println("요청 받은 장바구니 수정 내역 : "+updateDTO.toString())
        );
        //유효값 검증
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 e = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }

        // DTO를 만들어서 응답한다.
        CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    //장바구니에 담긴 내용을 모두 초기화한다.
    @PostMapping("/carts/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal CustomUserDetails userDetails) {
        //SERVICE단에 전달
        cartService.clear();
        //response는 ok신호
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
