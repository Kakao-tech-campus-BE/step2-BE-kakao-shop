package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.request.CartReqAddDTO;
import com.example.kakaoshop.cart.request.CartReqUpdateDTO;
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

    @PostMapping("/carts/add")
    public ResponseEntity<?> add(@RequestBody List<CartReqAddDTO> cartReqAddDTOList) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

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

    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody List<CartReqUpdateDTO> cartReqUpdateDTOList) {
        List<CartItemUpdateDTO> cartItemUpdateDTOList = new ArrayList<>();

        CartItemUpdateDTO cartItemUpdateDTO1 = CartItemUpdateDTO.builder()
                .cartId(cartReqUpdateDTOList.get(0).getCartId())
                .optionId(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(cartReqUpdateDTOList.get(0).getQuantity())
                .price(100000)
                .build();
        cartItemUpdateDTOList.add(cartItemUpdateDTO1);

        CartItemUpdateDTO cartItemUpdateDTO2 = CartItemUpdateDTO.builder()
                .cartId(cartReqUpdateDTOList.get(1).getCartId())
                .optionId(1)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(cartReqUpdateDTOList.get(1).getQuantity())
                .price(109000)
                .build();
        cartItemUpdateDTOList.add(cartItemUpdateDTO2);

        CartRespUpdateDTO responseDTO = new CartRespUpdateDTO(cartItemUpdateDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
