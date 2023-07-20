package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
public class CartRestController {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final CartService cartService;

    // (기능8) 장바구니 담기
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs,
                                         Errors errors,
                                         @AuthenticationPrincipal CustomUserDetails userDetails,
                                         HttpServletRequest request) {
//        if (errors.hasErrors()) {
//            List<FieldError> fieldErrors = errors.getFieldErrors();
//            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
//            return new ResponseEntity<>(
//                    ex.body(),
//                    ex.status()
//            );
//        }

        try{
            cartService.add(requestDTOs, userDetails);
            requestDTOs.forEach(
                    saveDTO -> System.out.println("요청 받은 장바구니 옵션 : " + saveDTO.toString())
            );
            return ResponseEntity.ok(ApiUtils.success(null));
        }catch(ConstraintViolationException e){
            return globalExceptionHandler.handleConstraintViolationException(e, request);
        }catch(RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        try{
            CartResponse.FindAllDTO responseDTO = cartService.findAll();
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }catch(RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
    }

    // (기능11) 주문하기 - (장바구니 업데이트)
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs,
                                    Errors errors,
                                    @AuthenticationPrincipal CustomUserDetails userDetails,
                                    HttpServletRequest request) {

//        if (errors.hasErrors()) {
//            List<FieldError> fieldErrors = errors.getFieldErrors();
//            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
//            return new ResponseEntity<>(
//                    ex.body(),
//                    ex.status()
//            );
//        }

        try{
            CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs);
            requestDTOs.forEach(
                    updateDTO -> System.out.println("요청 받은 장바구니 수정 내역 : " + updateDTO.toString())
            );
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }catch(RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
    }


    @PostMapping("/carts/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
