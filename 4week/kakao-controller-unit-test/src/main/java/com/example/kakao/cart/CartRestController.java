package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CartRestController {

//    private final FakeStore fakeStore;
    private final GlobalExceptionHandler globalExceptionHandler;
    private final CartService cartService;

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
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs, Errors errors, HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }
        try {
            cartService.addCartList(requestDTOs,userDetails.getUser().getId());
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
//
//        requestDTOs.forEach(
//                saveDTO -> System.out.println("요청 받은 장바구니 옵션 : "+saveDTO.toString())
//        );
//        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        try {
            return ResponseEntity.ok().body(ApiUtils.success(cartService.findAll(userDetails.getUser().getId())));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
//        List<Cart> cartList = fakeStore.getCartList();
//        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartList);
//        return ResponseEntity.ok(ApiUtils.success(responseDTO));
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
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, Errors errors, HttpServletRequest request,@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }



        try {
            return ResponseEntity.ok().body(ApiUtils.success(cartService.update(requestDTOs,userDetails.getUser().getId())));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }


//        requestDTOs.forEach(
//                updateDTO -> System.out.println("요청 받은 장바구니 수정 내역 : "+updateDTO.toString())
//        );

//        // 가짜 저장소의 값을 변경한다.
//        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
//            for (Cart cart : fakeStore.getCartList()) {
//                if(cart.getId() == updateDTO.getCartId()){
//                    cart.update(updateDTO.getQuantity(), cart.getPrice() * updateDTO.getQuantity());
//                }
//            }
//        }
//
//        // DTO를 만들어서 응답한다.
//        CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(fakeStore.getCartList());
//        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }


    @PostMapping("/carts/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        try {
            cartService.clear(userDetails.getUser().getId());
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }
}
