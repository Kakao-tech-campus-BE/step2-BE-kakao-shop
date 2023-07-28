package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.cart.*;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final CartJPARepository cartJPARepository;
    private final OrderService orderService;
    private final CartService cartService;


    // (기능9) 결재하기 - (주문 인서트) POST
    // /orders/save
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        //CartResponse.FindAllDTOv2 findAllDTOv2 = cartService.findAllv2(userDetails.getUser());
        //List<CartResponse.FindAllDTOv2.ProductDTO> productDTOList = findAllDTOv2.getProducts();
        List<Cart> cartList = cartJPARepository.findAllByUserId(userDetails.getUser().getId());
        for(Cart cart : cartList){
            orderService.saveOrder(cart, userDetails.getUser());
        }
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    // (기능10) 주문 결과 확인 GET
    // /orders/{id}
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.FindAllDTO findAllDTO = orderService.findOrder(id,userDetails.getUser().getId());
        // orderService.findOrder를 잘 살펴보자...
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(findAllDTO);
        return ResponseEntity.ok(apiResult);
    }

}
