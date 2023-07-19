package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final FakeStore fakeStore;

    // (기능8) 장바구니 담기
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody List<CartRequest.SaveDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
        requestDTOs.forEach(
                saveDTO -> System.out.println("요청 받은 장바구니 옵션 : "+saveDTO.toString())
        );
        boolean isMoreThan1 = requestDTOs.stream().allMatch(
                cart -> cart.getQuantity() >= 1
        );
        if (!isMoreThan1){
            Exception400 e = new Exception400("장바구니의 수량은 1이상이어야 합니다.");
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }

        boolean isDuplicated = requestDTOs.stream()
                .map(CartRequest.SaveDTO::getOptionId)
                .distinct()
                .count() != requestDTOs.size();
        if (isDuplicated){
            Exception400 e = new Exception400("장바구니는 중복되면 안됩니다.");
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }

        boolean isOptionOK = requestDTOs.stream().allMatch(
            saveDTO -> fakeStore.getOptionList().stream().anyMatch(option -> saveDTO.getOptionId() == option.getId())
        );
        if (!isOptionOK){
            Exception404 e = new Exception404("잘못된 장바구니 ID입니다.");
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Cart> cartList = fakeStore.getCartList();
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartList);

        int id = userDetails.getUser().getId();
        boolean isValid = cartList.stream().allMatch(
                cart -> cart.getUser().getId() == id
        );
        if (!isValid){
            Exception403 e = new Exception403("인증된 user는 해당 장바구니로 접근할 권한이 없습니다" + id);
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // (기능11) 주문하기 - (장바구니 업데이트)
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
        requestDTOs.forEach(
                updateDTO -> System.out.println("요청 받은 장바구니 수정 내역 : "+updateDTO.toString())
        );
        boolean isMoreThan1 = requestDTOs.stream().allMatch(
                cart -> cart.getQuantity() >= 1
        );
        if (!isMoreThan1){
            Exception400 e = new Exception400("장바구니의 수량은 1이상이어야 합니다.");
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }
        boolean isDuplicated = requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCartId)
                .distinct()
                .count() != requestDTOs.size();
        if (isDuplicated){
            Exception400 e = new Exception400("장바구니는 중복되면 안됩니다.");
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }
        boolean isOptionOK = requestDTOs.stream().allMatch(
                saveDTO -> fakeStore.getOptionList().stream().anyMatch(option -> saveDTO.getCartId() == option.getId())
        );
        if (!isOptionOK){
            Exception404 e = new Exception404("잘못된 장바구니 ID입니다.");
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }
        // 가짜 저장소의 값을 변경한다.
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            for (Cart cart : fakeStore.getCartList()) {
                if(cart.getId() == updateDTO.getCartId()){
                    cart.update(updateDTO.getQuantity(), cart.getPrice() * updateDTO.getQuantity());
                }
            }
        }

        // DTO를 만들어서 응답한다.
        CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(fakeStore.getCartList());
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }


    @PostMapping("/carts/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
