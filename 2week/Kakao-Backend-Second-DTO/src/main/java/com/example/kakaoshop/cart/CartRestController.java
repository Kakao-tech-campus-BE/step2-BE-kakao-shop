package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartRestController {

    @GetMapping("/carts")
    public ResponseEntity<?> findAll() {
        // 카트 아이템 리스트 만들기
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(4)
                .quantity(5)
                .price(50000)
                .build();
        cartItemDTO1.setOption(ProductOptionDTO.builder()
                                .id(1)
                                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                                .price(10000)
                                .build());
        cartItemDTOList.add(cartItemDTO1);

        CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                .id(5)
                .quantity(5)
                .price(54500)
                .build();
        cartItemDTO2.setOption(ProductOptionDTO.builder()
                                .id(1)
                                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                                .price(10900)
                                .build());
        cartItemDTOList.add(cartItemDTO2);

        // productDTO 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productDTOList.add(
                ProductDTO.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .carts(cartItemDTOList)
                        .build()
        );

        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // 장바구니 담기
    @PostMapping("/carts/add")
    public ResponseEntity<?> add(@RequestBody List<CartItemReqDTO> cartItemReqDTOList){
        // 나중에 만들 로직
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 장바구니 수정
    @PutMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody List<CartItemReqDTO> cartItemReqDTOList){
        // 카트 아이템 리스트 만들기
        List<CartUpdateItemDTO> cartUpdateItemDTOList = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        CartUpdateItemDTO cartItemDTO1 = CartUpdateItemDTO.builder()
                .cartId(4)
                .optionId(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(5)
                .price(10000)
                .build();
        cartUpdateItemDTOList.add(cartItemDTO1);

        CartUpdateItemDTO cartItemDTO2 = CartUpdateItemDTO.builder()
                .cartId(5)
                .optionId(2)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .quantity(2)
                .price(10900)
                .build();
        cartUpdateItemDTOList.add(cartItemDTO2);

        // 카트 업데이트 객체 만들기
        CartUpdateDTO responseDTO = new CartUpdateDTO(cartUpdateItemDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // 장바구니 삭제
    // 기존엔 없었는데 그냥 추가
    @DeleteMapping("/carts/delete")
    public ResponseEntity<?> delete(@RequestBody List<CartItemReqDTO> cartItemReqDTOList){
        // 나중에 만들 로직
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
