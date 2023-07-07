package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.*;
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
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(4)
                .quantity(5)
                .price(50000)
                .build();
        cartItemDTO1.setOption(CartProductOptionDTO.builder()
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
        cartItemDTO2.setOption(CartProductOptionDTO.builder()
                                .id(2)
                                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                                .price(10900)
                                .build());
        cartItemDTOList.add(cartItemDTO2);

        // productDTO 리스트 만들기
        List<CartProductDTO> productDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productDTOList.add(
                CartProductDTO.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .cartItems(cartItemDTOList)
                        .build()
        );

        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);



        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // 주문담기 api /carts/add
    @PostMapping("/carts")
    public ResponseEntity<?> add(){
        List<CartItemAddDTO> cartAddList = new ArrayList<>();

        cartAddList.add(CartItemAddDTO.builder()
                .optionId(1)
                .quantity(5)
                .build()
        );
        cartAddList.add(CartItemAddDTO.builder()
                .optionId(2)
                .quantity(5)
                .build()
        );

        // 요청한 내용이 잘 들어갔는지 확인하기 위함
        // 실제로는 출력 x
        return ResponseEntity.ok(ApiUtils.success(cartAddList));
    }

    // 주문정보 수정 api /carts/update
    @PutMapping("/carts")
    public ResponseEntity<?> update(){
        List<CartItemUpdateDTO> cartItemList = new ArrayList<>();

        CartItemUpdateDTO CartItemDTO1 = CartItemUpdateDTO.builder()
                .id(4)
                .quantity(10)
                .price(100000).build();
        CartItemDTO1.setOption(CartProductOptionUpdateDTO.builder()
                .id(1)
                .optionName("0  1. 슬라이딩 지퍼백 크리스마스 에디션 4종").build());

        cartItemList.add(CartItemDTO1);

        CartItemUpdateDTO CartItemDTO2 = CartItemUpdateDTO.builder()
                .id(5)
                .quantity(10)
                .price(109000).build();
        CartItemDTO2.setOption(CartProductOptionUpdateDTO.builder()
                .id(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종").build());

        cartItemList.add(CartItemDTO2);

        List<CartProductUpdateDTO> productList = new ArrayList<>();

        productList.add(CartProductUpdateDTO.builder()
                .cartItems(cartItemList).build());

        CartUpdateRespDTO responseDTO = new CartUpdateRespDTO(productList, 209000);



        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
