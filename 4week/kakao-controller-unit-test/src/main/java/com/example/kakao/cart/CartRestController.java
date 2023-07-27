package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final CartService cartService;

    // (기능8) 장바구니 담기
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> saveDTOs, Errors errors, HttpServletRequest request, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }
        try {
            cartService.addCartList(saveDTOs,user.getId());
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal User user, HttpServletRequest request) {
        try {
            return ResponseEntity.ok().body(ApiUtils.success(cartService.findAll(user.getId())));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }


    // (기능11) 주문하기 - (장바구니 업데이트)
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> updateDTOs, Errors errors, HttpServletRequest request,@AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }
        // 변경 갯수가 0일 때
        for(CartRequest.UpdateDTO u :updateDTOs) {
            if (Objects.equals(u.getQuantity(), 0)) {
                Exception400 ex = new Exception400("갯수가 0이 될 수 없습니다.");
                return new ResponseEntity<>(
                        ex.body(),
                        ex.status()
                );
            }
        }

        try {
            return ResponseEntity.ok().body(ApiUtils.success(cartService.update(updateDTOs,user.getId())));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }

    }


    @PostMapping("/carts/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal User user, HttpServletRequest request) {
        try {
            cartService.clear(user.getId());
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }
}
