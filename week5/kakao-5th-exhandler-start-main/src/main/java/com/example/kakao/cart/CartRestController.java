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
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs,userDetails.getUser());

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}

/*
@Transactional
public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
    List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
    Set<Integer> checkCartId = new HashSet<>();

    for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
        int cartId = updateDTO.getCartId();

        // cartId가 중복되어 requestDTOs에 두 번 이상 들어오면 예외 처리
        if (checkCartId.contains(cartId)) {
            throw new Exception400("잘못된 요청입니다. - 장바구니 아이디 중첩");
        }

        checkCartId.add(cartId);

        boolean cartExists = false;
        for (Cart cart : cartList) {
            if (cart.getId() == cartId) {
                cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                cartExists = true;
                break;
            }
        }

        // 유저 장바구니에 없는 cartId가 requestDTOs에 들어오면 예외처리
        if (!cartExists) {
            throw new RuntimeException("유저의 장바구니에 존재하지 않는 아이디입니다: " + cartId);
        }
    }

    return new CartResponse.UpdateDTO(cartList);
}

 */