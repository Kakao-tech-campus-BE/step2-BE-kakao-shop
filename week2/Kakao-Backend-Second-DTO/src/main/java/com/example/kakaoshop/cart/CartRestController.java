package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.request.CartReqAddDTO;
import com.example.kakaoshop.cart.request.CartReqUpdateDTO;
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
                        .cartItems(cartItemDTOList)
                        .build()
        );

        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PostMapping("/carts")
    public ResponseEntity<?> addCart(@RequestBody List<CartReqAddDTO> cartReqAddDTOS){
        //TODO : cart 추가 (동일한 옵션이 이미 존재한다면 수량 update)
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PutMapping("/carts")
    public ResponseEntity<?> updateCart(@RequestBody List<CartReqUpdateDTO> cartReqUpdateDTOS){
        //TODO : cart 수량 변경
        CartRespUpdateDTO response = new CartRespUpdateDTO(
                List.of(new CartDTO(4, 1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10, 100000),
                        new CartDTO(5,2,"02. 슬라이딩 지퍼백 플라워에디션 5종",10,109000)),
                209000);
        return ResponseEntity.ok().body(ApiUtils.success(response));
    }

    @DeleteMapping("/carts/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable int cartId){
        // TODO : cart 삭제
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
