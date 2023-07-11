package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.request.CartItemReqDTO;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartRestController {

    @PostMapping("/carts/add")
    public ResponseEntity<?> addToCart(@RequestBody List<CartItemReqDTO> cartItemReqDTOList){
        //ReqDTOList를 DB에 저장


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

        CartFindAllDTO responseDTO = new CartFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PostMapping("/carts/update")
    public ResponseEntity<?> updateCart(@RequestBody List<CartItemReqDTO> cartItemReqDTOList) {
        //CartItemReqDTOList를 DB에 저장
        CartUpdateDTO responseDTO = null;

        List<CartItemUpdateDTO> cartItemUpdateDTOList = new ArrayList<>();

        cartItemUpdateDTOList.add(new CartItemUpdateDTO(4,1,"01. 슬라이딩 지퍼백 크리스마스에디션 4종",10,100000));
        cartItemUpdateDTOList.add(new CartItemUpdateDTO(5,2,"02. 슬라이딩 지퍼백 플라워에디션 5종",10,109000));


        responseDTO = new CartUpdateDTO(cartItemUpdateDTOList,209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));

    }


}
