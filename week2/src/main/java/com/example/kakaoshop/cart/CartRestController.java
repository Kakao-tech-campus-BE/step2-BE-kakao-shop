package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop._core.utils.DummyStore;
import com.example.kakaoshop.cart.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartRestController {
    private final DummyStore dummyStore;
    //장바구니 조회
    @GetMapping() // /carts

    public ResponseEntity<?> findAll() {
        // 카트 아이템 리스트 만들기
        List<ProductDTO> productDTOList = dummyStore.getProductDTOList();

        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }


    //장바구니 담기
    @PostMapping("/add") //  /carts/add
    public ResponseEntity<?> addCart(@RequestBody List<CartReqRespDTO.CartDTO> request) { //리스트 형식으로 요청받음
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    //장바구니 수정
    @PostMapping("/update") //  /carts/update
    public ResponseEntity<?> updateCart(@RequestBody List<CartReqRespDTO.CartUpdateRequestDTO> request) {
        // 장바구니 불러오기
        List<CartReqRespDTO.CartInfoDTO> cartInfoDTOList = dummyStore.getCartInfoDTOList();

        //장바구니 수정
        for (CartReqRespDTO.CartUpdateRequestDTO requestDTO : request) {
            for (CartReqRespDTO.CartInfoDTO cart : cartInfoDTOList) {
                if (cart.getCartId() == requestDTO.getCartId()) {
                    cart.setPrice(dummyStore.getOptionDTOList().get(cart.getOptionId()-1).getPrice() * requestDTO.getQuantity());
                }
            }
        }

        //responseDTO 만들기
        CartReqRespDTO.CartUpdateResponseDTO responseDTO = CartReqRespDTO.CartUpdateResponseDTO.builder()
                .carts(cartInfoDTOList)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}
