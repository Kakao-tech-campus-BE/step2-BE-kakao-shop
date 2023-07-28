package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.ValidList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
public class CartRestController {

    private final CartService cartService;

    // (기능6) 장바구니 담기 POST

    /**
     * List를 @Valid하면 정상적으로 검증체크가 되지 않는다. 이는 List<>객체 자체를 검증하려고 하기때문이다.
     * 해결방법은 다음과 같다.
     *   1. List<CareRequest.SaveDTO>대신 CareRequest.SaveDTOs와 같은 형식으로 RequestBody받기 -> API 명세가 달라진다.
     *   2. CustomValidator 만들기 -> 컨트롤러 단에서 검증을 해 중복코드가 발생한다.
     *   3. ValidList 구현 -> 현재 채택한 방법, 기존 코드의 변경없이 사용할 수 있다.
     *   4. @Validated 사용 -> 간단하지만, 기존 코드(GlobalValidationHandler)를 지우고, ExceptionHandler에 예외처리를 추가해줘야 한다.
     */
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody @Valid ValidList<CartRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        cartService.addCartList(requestDTOs, userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/carts/add/v2")
    public ResponseEntity<?> addCartListV2(@RequestBody @Valid ValidList<CartRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        cartService.addCartListV2(requestDTOs, userDetails.getUser());
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

    @GetMapping("/carts/v2")
    public ResponseEntity<?> findAllv2(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.FindAllDTOv2 responseDTO = cartService.findAllv2(userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }


    // (기능8) 주문하기 - (주문화면에서 장바구니 수정하기)
    // /carts/update
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid ValidList<CartRequest.UpdateDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs,userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/carts/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal CustomUserDetails userDetails) {
        cartService.clearAll(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
