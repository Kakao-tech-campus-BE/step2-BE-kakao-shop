package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final FakeStore fakeStore;
    private final CartService cartService;
    private final GlobalExceptionHandler globalExceptionHandler;

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
    @PostMapping("/carts-mock/add")
    public ResponseEntity<?> addCartListMock(@RequestBody List<CartRequest.SaveDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
        requestDTOs.forEach(
                saveDTO -> System.out.println("요청 받은 장바구니 옵션 : "+saveDTO.toString())
        );
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts-mock")
    public ResponseEntity<?> findAllMock(@AuthenticationPrincipal CustomUserDetails userDetails) {

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
    @PostMapping("/carts-mock/update")
    public ResponseEntity<?> updateMock(@RequestBody List<CartRequest.UpdateDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
        requestDTOs.forEach(
                updateDTO -> System.out.println("요청 받은 장바구니 수정 내역 : "+updateDTO.toString())
        );

        // 가짜 저장소의 값을 변경한다.
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            for (Cart cart : fakeStore.getCartList()) {
                if(cart.getId() == updateDTO.getCartId()){
                    cart.update(updateDTO.getQuantity(), cart.getPrice() * updateDTO.getQuantity());
                }
            }
        }

        // DTO를 만들어서 응답한다.
        CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(fakeStore.getCartList());
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }


    @PostMapping("/carts-mock/clear")
    public ResponseEntity<?> clearMock(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody List<CartRequest.SaveDTO> requestDTOs,
                                         @AuthenticationPrincipal CustomUserDetails userDetails,
                                         HttpServletRequest request) {

        try {
            cartService.create(requestDTOs, userDetails);
            return ResponseEntity.ok(ApiUtils.success(null));
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }

    }

    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.FindAllDTO responseDTO = cartService.getCartLists(userDetails);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody List<CartRequest.UpdateDTO> requestDTOs,
                                    @AuthenticationPrincipal CustomUserDetails userDetails,
                                    HttpServletRequest request) {

        try{
            CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs);
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    @PostMapping("/carts/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   HttpServletRequest request) {
        try{
            cartService.clear(userDetails);
            return ResponseEntity.ok(ApiUtils.success(null));
        }catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }
}
