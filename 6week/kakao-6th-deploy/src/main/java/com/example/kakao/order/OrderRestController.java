package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.CartService;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        //for(Cart cart : cartList){
            orderService.saveOrder(userDetails.getUser());
        //}
        OrderResponse.FindAllDTO findAllDTO = orderService.findOrder(cartList.get(cartList.size()-1).getId(),userDetails.getUser().getId());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(findAllDTO);
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