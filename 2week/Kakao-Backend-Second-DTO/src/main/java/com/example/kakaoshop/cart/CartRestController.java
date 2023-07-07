package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.CartItemDTO;
import com.example.kakaoshop.cart.response.CartRespFindAllDTO;
import com.example.kakaoshop.cart.response.ProductOptionDTO;
import com.example.kakaoshop.cart.response.ProductDTO;
import com.example.kakaoshop.cart.request.AddCartItemDTO;
import com.example.kakaoshop.cart.request.UpdateCartItemDTO;
import com.example.kakaoshop.cart.response.UpdatedItemDTO;
import com.example.kakaoshop.cart.response.CartRespUpdateDTO;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> addToCart(@RequestBody List<AddCartItemDTO> cartItemAddDTOList) {
        return ResponseEntity.ok(ApiUtils.success(true)); // 기본적인 응답 결과 값(성공 여부)
    }

    // Database 연결 후 수정 가능해지면 id 값 받아서 수정 가능하게 만들기
    @PutMapping("/carts")
    public ResponseEntity<?> updateCart(@RequestBody List<UpdateCartItemDTO> updateItems) {
        List<UpdatedItemDTO> updatedItemDTOList = new ArrayList<>();

        UpdatedItemDTO updatedItemDTO1 = UpdatedItemDTO.builder()
                .cartId(4)
                .optionId(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        updatedItemDTOList.add(updatedItemDTO1);

        UpdatedItemDTO updatedItemDTO2 = UpdatedItemDTO.builder()
                .cartId(5)
                .optionId(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        updatedItemDTOList.add(updatedItemDTO2);

        CartRespUpdateDTO responseDTO = new CartRespUpdateDTO(updatedItemDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}
