package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.request.CartRequest;
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
                        .carts(cartItemDTOList)
                        .build()
        );

        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PostMapping("/carts/add")
    public ResponseEntity<?> AddToCart(@RequestBody List<CartRequest.AddDTO> addDTOs) {
        System.out.println("POST /carts/add 로 요청 받은 값");
        for (CartRequest.AddDTO addDTO : addDTOs) {
            System.out.println("optionId(quantity): " + addDTO.getOptionId() +
                    "(" + addDTO.getQuantity() + ")");
            // TODO: addDTO를 바탕으로 cart_tb 에 INSERT 필요
        }

        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/carts/update")
    public ResponseEntity<?> UpdateCart(@RequestBody List<CartRequest.updateDTO> updateDTOs) {

        List<CartItemInfoDTO> carts = new ArrayList<>();

        carts.add(CartItemInfoDTO.builder()
                .cartId(4L)
                .optionId(1L)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build());

        carts.add(CartItemInfoDTO.builder()
                .cartId(5L)
                .optionId(2L)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build());

        System.out.println("POST /carts/update 로 요청 받은 값");

        for (CartRequest.updateDTO updateDTO : updateDTOs) {
            System.out.println("cartId(quantity): " + updateDTO.getCartId() +
                    "(" + updateDTO.getQuantity() + ")");
            // TODO: updateDTO 를 바탕으로 cart_tb 에 UPDATE 필요
        }

        CartResUpdateDTO responseDTO = new CartResUpdateDTO(carts, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
