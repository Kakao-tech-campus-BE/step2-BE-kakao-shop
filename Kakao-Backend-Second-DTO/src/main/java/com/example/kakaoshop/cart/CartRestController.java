package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.CartItemResponse;
import com.example.kakaoshop.cart.response.CartFindAllResponse;
import com.example.kakaoshop.cart.response.ProductOptionResponse;
import com.example.kakaoshop.cart.response.ProductCartItemReponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartRestController {

    @GetMapping("/carts")
    public ResponseEntity<ApiUtils.ApiResult<CartFindAllResponse>> findAll() {
        // 카트 아이템 리스트 만들기
        List<CartItemResponse> cartItemDTOList = new ArrayList<>();

        ProductOptionResponse productOptionResponse = ProductOptionResponse.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .price(10000)
                .build();

        // 카트 아이템 리스트에 담기
        CartItemResponse cartItemDTO1 = CartItemResponse.builder()
                .id(4)
                .option(productOptionResponse)
                .quantity(5)
                .price(50000)
                .build();

        cartItemDTOList.add(cartItemDTO1);

        ProductOptionResponse productOptionResponse1 = ProductOptionResponse.builder()
                .id(1)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .price(10900)
                .build();

        CartItemResponse cartItemDTO2 = CartItemResponse.builder()
                .id(5)
                .option(productOptionResponse1)
                .quantity(5)
                .price(54500)
                .build();

        cartItemDTOList.add(cartItemDTO2);

        // productDTO 리스트 만들기
        List<ProductCartItemReponse> productDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productDTOList.add(
                ProductCartItemReponse.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .cartItems(cartItemDTOList)
                        .build()
        );

        CartFindAllResponse responseDTO = new CartFindAllResponse(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
