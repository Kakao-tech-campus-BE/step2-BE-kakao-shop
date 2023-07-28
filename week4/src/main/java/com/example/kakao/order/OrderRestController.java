package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.ProductResponse;
import com.example.kakao.product.ProductService;
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

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final OrderService orderService;
    private final UserService userService;

    // (기능12) 결제
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        try {
            //jwt가 존재하는지 확인
            if(userDetails.getUser()!= null){
                //유저가 존재하는지 확인
                UserResponse.FindById byId = userService.findById(userDetails.getUser().getId());

                if(byId.getId() != 0){ //유저가 존재할 경우 order 저장
                    OrderResponse.FindByIdDTO dto = orderService.save(userDetails.getUser());
                    return ResponseEntity.ok().body(ApiUtils.success(dto));
                }
            }
            return ResponseEntity.ok().body(ApiUtils.success(null));
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }

    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable String id, HttpServletRequest request) {
        try {
            int orderId = Integer.parseInt(id); //문자열->Integer
            OrderResponse.FindByIdDTO dto = orderService.findById(orderId);
            return ResponseEntity.ok().body(ApiUtils.success(dto));
        }
        catch(NumberFormatException e){
            return ResponseEntity.badRequest().body(ApiUtils.error("id는 숫자만 가능합니다", HttpStatus.BAD_REQUEST));
        }
        catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }
}
