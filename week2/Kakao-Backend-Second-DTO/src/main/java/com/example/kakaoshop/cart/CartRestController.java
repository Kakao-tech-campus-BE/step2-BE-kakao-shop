package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/carts")
@RestController
public class CartRestController {

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        // 카트 아이템 리스트 만들기
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(4L)
                .quantity(5)
                .price(50000)
                .build();
        cartItemDTO1.setOption(ProductOptionDTO.builder()
                                .id(1L)
                                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                                .price(10000)
                                .build());
        cartItemDTOList.add(cartItemDTO1);

        CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                .id(5L)
                .quantity(5)
                .price(54500)
                .build();
        cartItemDTO2.setOption(ProductOptionDTO.builder()
                                .id(1L)
                                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                                .price(10900)
                                .build());
        cartItemDTOList.add(cartItemDTO2);

        // productDTO 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productDTOList.add(
                ProductDTO.builder()
                        .id(1L)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .cartItems(cartItemDTOList)
                        .build()
        );

        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }


    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody List<CartRequest.CartAddDTO> cartAddDTOList) {

        return ResponseEntity.ok(ApiUtils.success(null));
    }


    @PostMapping("/update") // PutMapping이 가능하다면, PutMapping("")을 사용했을 것입니다.
    public ResponseEntity<?> update(@RequestBody List<CartRequest.CartUpdateDTO> cartUpdateRequestDTOList) {
        List<CartUpdateDTO> cartUpdateDTOList = new ArrayList<>();
        CartUpdateDTO cartUpdateItemDTO1 = CartUpdateDTO.builder()
                .cartId(4L)
                .optionId(1L)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        CartUpdateDTO cartUpdateItemDTO2 = CartUpdateDTO.builder()
                .cartId(5L)
                .optionId(2L)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .price(10)
                .quantity(109000)
                .build();

        cartUpdateDTOList.add(cartUpdateItemDTO1);
        cartUpdateDTOList.add(cartUpdateItemDTO2);

        CartRespUpdateDTO responseDTO = new CartRespUpdateDTO(cartUpdateDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
