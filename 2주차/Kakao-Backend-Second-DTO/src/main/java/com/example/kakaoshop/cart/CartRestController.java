package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
                .id(1)
                .quantity(1)
                .price(10000)
                .build();
        cartItemDTO1.setOption(ProductOptionDTO.builder()
                                .id(1)
                                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                                .price(10000)
                                .build());
        cartItemDTOList.add(cartItemDTO1);

        CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                .id(2)
                .quantity(1)
                .price(9900)
                .build();
        cartItemDTO2.setOption(ProductOptionDTO.builder()
                                .id(1)
                                .optionName("고무장갑 베이지 S(소형) 6팩")
                                .price(9900)
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

    @PostMapping("/carts/add")
    public ResponseEntity<?> add(@RequestBody CartRequest.add add){
        //장바구니에 물건 추가 로직이 들어가야한다.
        //요청데이터로부터 option_id와 수량을 받아 그것을 현재 carts 테이블에 반영해주면 될 듯하다.

        return ResponseEntity.ok("장바구니 담기 완료");
    }

    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody CartRequest.update update){
        //장바구니에 물건 업데이트 로직이 들어가야한다.
        //요청데이터로부터 option_id와 수량을 받아 그것을 현재 carts 테이블에 반영해주면 될 듯하다.

        return ResponseEntity.ok("장바구니 수정 완료");
    }
}
