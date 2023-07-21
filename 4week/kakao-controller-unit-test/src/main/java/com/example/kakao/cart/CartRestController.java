package com.example.kakao.cart;
import com.example.kakao._core.utils.ValidList;
import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CartRestController {
    private final GlobalExceptionHandler globalExceptionHandler;
    private final FakeStore fakeStore;
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
    public ResponseEntity<?> addCartList(@RequestBody @Valid ValidList<CartRequest.SaveDTO> requestDTOs, Errors errors,
                                         @AuthenticationPrincipal CustomUserDetails userDetails,
                                         HttpServletRequest request) {

        //1. 유효성 검사
        if (errors.hasErrors()) {
            List<String> messages=new ArrayList<>();
            errors.getFieldErrors().forEach((fieldError)->{
                        messages.add(fieldError.getDefaultMessage() + ":" + fieldError.getField());});
            Exception400 e = new Exception400(messages);
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }
        //2. 권한 검사 어차피 시큐리티 필터 선에서 검증 다해줌
        //3. 서비스 로직 실행
        try {
          requestDTOs.forEach(
                    saveDTO -> System.out.println("요청 받은 장바구니 옵션 : "+saveDTO.toString()));
            cartService.add(requestDTOs);
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }

    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails,HttpServletRequest request) {

        //1. 유효성 검사
        //2. 권한 검사 어차피 시큐리티 필터 선에서 검증 다해줌
        //3. 서비스 로직 실행
        try {
            CartResponse.FindAllDTO reponseDTO= cartService.findAll();
            return ResponseEntity.ok().body(ApiUtils.success(reponseDTO));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }

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
    public ResponseEntity<?> update(@RequestBody @Valid ValidList<CartRequest.UpdateDTO> requestDTOs, Errors errors,
                                    @AuthenticationPrincipal CustomUserDetails userDetails,HttpServletRequest request) {
        requestDTOs.forEach(
                updateDTO -> System.out.println("요청 받은 장바구니 수정 내역 : "+updateDTO.toString())
        );


        //1. 유효성 검사
        if (errors.hasErrors()) {
            List<String> messages=new ArrayList<>();
            errors.getFieldErrors().forEach((fieldError)->{
                messages.add(fieldError.getDefaultMessage() + ":" + fieldError.getField());});
            Exception400 e = new Exception400(messages);
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }
        //2. 권한 검사 어차피 시큐리티 필터 선에서 검증 다해줌
        //3. 서비스 로직 실행
        try {
            CartResponse.UpdateDTO reponseDTO= cartService.update(requestDTOs);
            return ResponseEntity.ok().body(ApiUtils.success(reponseDTO));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }

    }





    @PostMapping("/carts/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal CustomUserDetails userDetails,HttpServletRequest request) {
        //1. 유효성 검사
        //2. 권한 검사 어차피 시큐리티 필터 선에서 검증 다해줌
        //3. 서비스 로직 실행
        try {
             cartService.clear();
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }

    }
}
