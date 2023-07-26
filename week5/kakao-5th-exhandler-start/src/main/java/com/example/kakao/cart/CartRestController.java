package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class CartRestController {
    CartService cartService;
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
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // 1. 동일한 옵션이 들어오면 예외처리 -> 이거는 유효성 체크에 가깝다는 의견을 보고 흔들림. controller단에 추가해야 하는거 아닌가?
        // 그래서 컨트롤러단으로 모셔와서 작성하였다.
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        List<Integer> optionIdList = new ArrayList<>();
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            // 만약 이미 리스트에 존재하는 옵션id라면 중복이 발생한 것이므로 검증 실패. 존재하지 않는다면 리스트에 값을 넣어가면서 수행, 아니면 예외처리
            if (optionIdList.contains(optionId)) {
                throw new Exception400("중복된 옵션을 장바구니에 추가할 수 없습니다.");
            }
            // 아니면 리스트에 옵션Id를 추가.
            optionIdList.add(optionId);
        }
        cartService.addCartList(requestDTOs, userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    // (기능7) 장바구니 조회 - (주문화면) GET
    // /carts
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.FindAllDTO responseDTO = cartService.findAll(userDetails.getUser());
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
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리 -> 유효성 검증
        List<Integer> cartIdList = new ArrayList<>();
        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            int cartId = requestDTO.getCartId();
            // 만약 이미 cartList에 존재하는 옵션이라면 중복이 발생한 것이므로 검증 실패. 존재하지 않는다면 리스트에 값을 넣어가면서 수행, 아니면 예외처리
            if (cartIdList.contains(cartId)) {
                throw new Exception400("중복된 장바구니를 업데이트할 수 없습니다.");
            }
            // 아니면 리스트에 optionId를 추가.
            cartIdList.add(cartId);
        }

        CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs,userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }
}
