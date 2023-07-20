package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CartRestController {
    private final CartService cartService;
    private final OptionService optionService;

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
    public ResponseEntity<?> addCartList(@RequestBody List<CartRequest.SaveDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
        insertCarts(requestDTOs, userDetails);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    private void insertCarts(List<CartRequest.SaveDTO> requestDTOs, CustomUserDetails userDetails) {
        requestDTOs
                .forEach(request -> {
                    Option option = optionService.findById(request.getOptionId());
                    int price = option.getPrice();
                    int quantity = request.getQuantity();
                    Cart cart = Cart.builder()
                            .user(userDetails.getUser())
                            .option(option)
                            .quantity(quantity)
                            .price(price * quantity)
                            .build();
                    cartService.save(userDetails.getUser(), cart);
                });
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.FindAllDTO responseDTO = cartService.findAllByUser(userDetails.getUser());
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
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.UpdateDTO responseDTO = cartService.update(userDetails.getUser(), requestDTOs);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }


    @PostMapping("/carts/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal CustomUserDetails userDetails) {
        cartService.deleteAllByUser(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
