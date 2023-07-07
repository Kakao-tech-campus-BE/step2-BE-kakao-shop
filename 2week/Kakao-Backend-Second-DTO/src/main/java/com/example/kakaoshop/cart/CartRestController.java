package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.request.CartReqAddDTO;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartRestController {

    @GetMapping("/carts")
    public ResponseEntity<?> findAll() {
        // 카트 아이템 리스트 만들기
        List<CartItemRespFindAllDTO> cartItemRespFindAllDTOList = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        CartItemRespFindAllDTO cartItemRespFindAllDTO1 = CartItemRespFindAllDTO.builder()
                .id(4)
                .quantity(5)
                .price(50000)
                .build();
        cartItemRespFindAllDTO1.setOption(ProductOptionRespFindAllDTO.builder()
                                .id(1)
                                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                                .price(10000)
                                .build());
        cartItemRespFindAllDTOList.add(cartItemRespFindAllDTO1);

        CartItemRespFindAllDTO cartItemRespFindAllDTO2 = CartItemRespFindAllDTO.builder()
                .id(5)
                .quantity(5)
                .price(54500)
                .build();
        cartItemRespFindAllDTO2.setOption(ProductOptionRespFindAllDTO.builder()
                                .id(1)
                                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                                .price(10900)
                                .build());
        cartItemRespFindAllDTOList.add(cartItemRespFindAllDTO2);

        // productDTO 리스트 만들기
        List<ProductRespFindAllDTO> productRespFindAllDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productRespFindAllDTOList.add(
                ProductRespFindAllDTO.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .carts(cartItemRespFindAllDTOList)
                        .build()
        );

        CartRespFindAllDTO cartRespFindAllDTO = new CartRespFindAllDTO(productRespFindAllDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(cartRespFindAllDTO));
    }

    // 장바구니 담기
    @PostMapping("/carts")
    public static ResponseEntity<?> createCart() {
        List<CartReqAddDTO> cartAddDTOList = new ArrayList<>();

        cartAddDTOList.add(
                CartReqAddDTO.builder()
                        .optionId(1)
                        .quantity(5)
                        .build());

        cartAddDTOList.add(
                CartReqAddDTO.builder()
                        .optionId(2)
                        .quantity(5)
                        .build());

        return ResponseEntity.status(HttpStatus.OK).body(ApiUtils.success(cartAddDTOList));
    }

    // 장바구니 수량 수정
    @PutMapping("/carts")
    public static ResponseEntity<?> updateCart() {
// 카트 아이템 리스트 만들기
        List<CartItemResUpdateDTO> cartItemResUpdateDTOList = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        CartItemResUpdateDTO cartUpdateItemDTO1 = CartItemResUpdateDTO.builder()
                .cartId(4)
                .optionId(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        cartItemResUpdateDTOList.add(cartUpdateItemDTO1);

        CartItemResUpdateDTO cartUpdateItemDTO2 = CartItemResUpdateDTO.builder()
                .cartId(5)
                .optionId(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();

        cartItemResUpdateDTOList.add(cartUpdateItemDTO2);

        CartRespUpdateDTO cartRespUpdateDto = new CartRespUpdateDTO(cartItemResUpdateDTOList, 209000);

        return ResponseEntity.status(HttpStatus.OK).body(ApiUtils.success(cartRespUpdateDto));
    }
}
