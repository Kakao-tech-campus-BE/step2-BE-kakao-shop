package com.example.kakao.cart;

import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final CartService cartService;

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
    public ResponseEntity<?> addCartList(@RequestBody @Valid List< CartRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal User user) {
        cartService.addCartList(requestDTOs, user.getId());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    // (기능7) 장바구니 조회 - (주문화면) GET
    // /carts
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal User user) {
        CartResponse.FindAllDTO responseDTO = cartService.findAll(user.getId());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/carts/v2")
    public ResponseEntity<?> findAllv2(@AuthenticationPrincipal User user) {
        CartResponse.FindAllDTOv2 responseDTO = cartService.findAllv2(user.getId());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }


    /**
     * [
     * {
     * "cartId":1,
     * "quantity":10
     * },
     * {
     * "cartId":2,
     * "quantity":10
     * }
     * ]
     */
    // (기능8) 주문하기 - (주문화면에서 장바구니 수정하기)
    // /carts/update
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody  @Valid List<CartRequest.UpdateDTO> requestDTOs, Errors errors, @AuthenticationPrincipal User user) {
        CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs,user.getId());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
}
    @PostMapping("/carts/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal User user, HttpServletRequest request) {

        cartService.clear(user.getId());
        return ResponseEntity.ok().body(ApiUtils.success(null));


    }
}
