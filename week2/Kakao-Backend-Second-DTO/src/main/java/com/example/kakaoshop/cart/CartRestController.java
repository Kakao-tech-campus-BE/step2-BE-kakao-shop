package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.request.CartAddDTO;
import com.example.kakaoshop.cart.request.CartUpdateDTO;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartRestController {
    @PostMapping("/carts/update")
    public ResponseEntity<?> updateOption(@RequestBody List<CartUpdateDTO> updateDTOList) {
        List<UpdatedItemDTO> updatedItemDTOList = new ArrayList<>();
        updatedItemDTOList.add(
                UpdatedItemDTO.builder()
                        .cartId(4)
                        .optionId(1)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .quantity(10)
                        .price(100000)
                        .build());

        updatedItemDTOList.add(
                UpdatedItemDTO.builder()
                        .cartId(5)
                        .optionId(2)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .quantity(10)
                        .price(109000)
                        .build());

        UpdatedCartDto responseDTO = UpdatedCartDto.builder()
                .carts(updatedItemDTOList)
                .totalPrice(209000)
                .build();
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PostMapping("/carts/add")
    public ResponseEntity<?> addOption(@RequestBody List<CartAddDTO> addDTOList) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @GetMapping("/carts")
    public ResponseEntity<?> findAll() {
        // 카트 아이템 리스트 만들기
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();

        ProductOptionDTO option1 = ProductOptionDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .price(10000)
                .build();

        // 카트 아이템 리스트에 담기
        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(4)
                .quantity(5)
                .price(50000)
                .option(option1)
                .build();
        cartItemDTOList.add(cartItemDTO1);


        ProductOptionDTO option2 = ProductOptionDTO.builder()
                .id(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .price(10900)
                .build();
        CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                .id(5)
                .quantity(5)
                .price(54500)
                .option(option2)
                .build();
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

        CartRespFindAllDTO responseDTO = CartRespFindAllDTO.builder()
                .products(productDTOList)
                .totalPrice(104500)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
