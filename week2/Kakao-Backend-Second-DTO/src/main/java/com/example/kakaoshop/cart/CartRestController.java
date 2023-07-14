package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartRestController {

    @PostMapping("/carts")
    public ResponseEntity<?> add() {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @GetMapping("/carts")
    public ResponseEntity<?> findAll() {
        // 여러 카트아이템을 담을 DTO 생성
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        // 카트아이템 1개씩 넣기
        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(4)
                .quantity(5)
                .price(50000)
                .build();
        // 카트아이템의 옵션 설정
        cartItemDTO1.setOption(ProductOptionDTO.builder()
                                .id(1)
                                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                                .price(10000)
                                .build());
        cartItemDTOList.add(cartItemDTO1);
        // 카트아이템 1개씩 넣기
        CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                .id(5)
                .quantity(5)
                .price(54500)
                .build();
        // 카트아이템의 옵션 설정
        cartItemDTO2.setOption(ProductOptionDTO.builder()
                                .id(1)
                                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                                .price(10900)
                                .build());
        cartItemDTOList.add(cartItemDTO2);
        // 여러 상품을 담을 DTO 생성
        List<ProductDTO> productDTOList = new ArrayList<>();
        // 상품 1개씩 넣기
        productDTOList.add(
                ProductDTO.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .cartItems(cartItemDTOList)
                        .build()
        );
        // 카트 1개를 담을 DTO 생성
        CartItemRespFindAllDTO responseDTO = new CartItemRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PatchMapping("/carts")
    public ResponseEntity<?> update() {
        // 여러 카트아이템을 담을 DTO 생성
        List<CartItemUpdateDTO> cartItemUpdateDTOList = new ArrayList<>();
        // 카트아이템 1개씩 넣기
        CartItemUpdateDTO cartItemUpdateDTO1 = CartItemUpdateDTO.builder()
                .cartId(4)
                .optionId(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        cartItemUpdateDTOList.add(cartItemUpdateDTO1);
        // 카트아이템 1개씩 넣기
        CartItemUpdateDTO cartItemUpdateDTO2 = CartItemUpdateDTO.builder()
                .cartId(5)
                .optionId(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        cartItemUpdateDTOList.add(cartItemUpdateDTO2);
        // 카트 1개를 담을 DTO 생성
        CartItemRespUpdateDTO responseDTO = new CartItemRespUpdateDTO(cartItemUpdateDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
