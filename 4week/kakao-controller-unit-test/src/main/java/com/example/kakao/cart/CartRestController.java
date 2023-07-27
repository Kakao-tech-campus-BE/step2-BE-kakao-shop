package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception204;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception401;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CartRestController {
    // 유효성 검사 추가하기
    // error 잡기

    @Autowired
    private final FakeStore fakeStore;

    @Autowired
    private CartService cartService;
//         "optionId":1,
//         "quantity":5
//     },
//     {
//         "optionId":2,
//         "quantity":5
//     }
// ]
    // (기능8) 장바구니 담기
    @PostMapping("/carts")
    public ResponseEntity<?> addCartList(@RequestBody List<CartRequest.SaveDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            Exception401 ex = new Exception401("인증되지 않았습니다"+userDetails);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        if (requestDTOs.get(0).getQuantity() < 0 || requestDTOs.get(0).getQuantity() >= Math.pow(2,32)) {
            Exception400 ex =new Exception400( "잘못된 수량입니다." + requestDTOs.get(0).getQuantity());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        if (requestDTOs == null){
            Exception404 ex = new Exception404("상품이 존재하지 않습니다.");
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        requestDTOs.forEach(
                saveDTO -> System.out.println("요청 받은 장바구니 옵션 : "+saveDTO.toString())
        );
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            Exception401 ex = new Exception401("인증되지 않았습니다"+userDetails);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }
        //List<Cart> cartList = fakeStore.getCartList();
        List<Cart> cartList = cartService.findAll();

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
    @PutMapping("/carts")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            Exception401 ex = new Exception401("인증되지 않았습니다"+userDetails);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        requestDTOs.forEach(
                updateDTO -> System.out.println("요청 받은 장바구니 수정 내역 : "+updateDTO.toString())
        );

        // 가짜 저장소의 값을 변경한다.
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            //for (Cart cart : fakeStore.getCartList()) {
            for (Cart cart : cartService.findAll()) {
                    if(cart.getId() == updateDTO.getCartId()){
                        cart.update(updateDTO.getQuantity(), cart.getPrice() * updateDTO.getQuantity());
                    }
                }
            }


        // DTO를 만들어서 응답한다.
        CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(fakeStore.getCartList());
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }


    @DeleteMapping("/carts")
    public ResponseEntity<?> clear(@AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            Exception401 ex = new Exception401("인증되지 않았습니다"+userDetails);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
