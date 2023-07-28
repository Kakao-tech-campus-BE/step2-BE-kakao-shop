package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final CartService cartService;
    /**
     * [
     *     {
     *         "optionId":1,
     *         "quantity":5
     *     },
     *     {
     *         "optionId":2,
     *         "quantity":5
     *     }
     * ]
     */
    // (기능6) 장바구니 담기 POST
    // /carts/add
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        cartService.addCartList(requestDTOs, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // (기능7) 장바구니 조회 - (주문화면) GET
    // /carts
    public ResponseEntity<?> findAllv2(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.FindAllDTOv2 responseDTO = cartService.findAllv2(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));

    }


    /**
     *  [
     *      {
     *          "cartId":1,
     *          "quantity":10
     *      },
     *      {
     *          "cartId":2,
     *          "quantity":10
     *      }
     *  ]
     */
    // (기능8) 주문하기 - (주문화면에서 장바구니 수정하기)
    // /carts/update
    public void update() {
    }
}
