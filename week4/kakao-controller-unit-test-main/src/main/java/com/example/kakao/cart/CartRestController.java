package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartRestController {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final FakeStore fakeStore;

// [
//     {
//         "optionId":1,
//         "quantity":5
//     },
//     {
//         "optionId":2,
//         "quantity":5
//     }
// ]
    // (기능8) 장바구니 담기
    @PostMapping("/add")
    public ResponseEntity<?> addCartList(@RequestBody List<CartRequest.SaveDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (requestDTOs.isEmpty()) {
            return globalExceptionHandler.getApiErrorResultResponseEntity(new Exception403("잘못된 요청입니다."));
        }
        requestDTOs.forEach(
                saveDTO -> System.out.println("요청 받은 장바구니 옵션 : "+saveDTO.toString())
        );
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Cart> cartList = fakeStore.getCartList();
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartList);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }


// [
//     {
//         "cartId":1,
//         "quantity":10
//     },
//     {
//         "cartId":2,
//         "quantity":10
//     }
// ]
    // (기능11) 주문하기 - (장바구니 업데이트)
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (requestDTOs.isEmpty()) {
            return globalExceptionHandler.getApiErrorResultResponseEntity(new Exception403("잘못된 요청입니다."));
        }
        requestDTOs.forEach(
                updateDTO -> System.out.println("요청 받은 장바구니 수정 내역 : "+updateDTO.toString())
        );

        // 기존 장바구니에 없는 요청인지 확인한다
        List<Integer> cartIds = fakeStore.getCartList().stream().map(x->x.getId()).collect(Collectors.toList());
        if (requestDTOs.stream().filter(x->!cartIds.contains(x.getCartId())).collect(Collectors.toList()).isEmpty()){
            // 가짜 저장소의 값을 변경한다.
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                for (Cart cart : fakeStore.getCartList()) {
                    if(cart.getId() == updateDTO.getCartId()){
                        cart.update(updateDTO.getQuantity(), cart.getPrice() * updateDTO.getQuantity());
                    }
                }
            }
        }
        else {
            return globalExceptionHandler.getApiErrorResultResponseEntity(new Exception403("유효하지 않은 요청입니다."));
        }


        // DTO를 만들어서 응답한다.
        CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(fakeStore.getCartList());
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }


    @PostMapping("/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
