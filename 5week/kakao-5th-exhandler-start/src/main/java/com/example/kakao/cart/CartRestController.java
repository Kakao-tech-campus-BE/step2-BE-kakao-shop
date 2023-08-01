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

    private final CartService cartService; // 의존

    // (기능8) 주문하기 - (주문화면에서 장바구니 수정하기)
    // /carts/update
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs,userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    /**
     * [
     * {
     * "optionId":1,
     * "quantity":5
     * },
     * {
     * "optionId":2,
     * "quantity":5
     * }
     * ]
     */
    // (기능6) 장바구니 담기 POST
    // /carts/add
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        cartService.addCartList(requestDTOs, userDetails.getUser());
        // msave였으면 getid()만 던지면 됨
        // 근데 jpa를 쓸거면 유저 객체를 던져야 됨
        return ResponseEntity.ok(ApiUtils.success(null));
    }
    // 유효성 검사할 필요 없음 // aop로 돼있음 postmapping에 걸어둠
    // errors랑 valid 걸려있음 // notnull만 체크


    // (기능7) 장바구니 조회 - (주문화면) GET
    // /carts
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.FindAllDTO responseDTO = cartService.findAll(userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/carts/v2")
    public ResponseEntity<?> findAllv2(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.FindAllDTOv2 responseDTO = cartService.findAllv2(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
    // 테스트 하려면 2가지가 필요
    // 로그인하고 cart에 값 넣어야지 조회가 됨


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
