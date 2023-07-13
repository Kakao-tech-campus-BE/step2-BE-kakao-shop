package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartRestController {

    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody List<CartRequest.CartUpdateDTO> cartUpdateDTOS){

        // 들어온 입력들 (입력과 별개로 DTO 구조로 하드코딩해서 출력해줌)
        for(CartRequest.CartUpdateDTO cart : cartUpdateDTOS){
            System.out.println("Cart Id :"+cart.getCartId()+"/ Quantity : "+cart.getQuantity());
        }

        // 카트 아이템 리스트 만들기
        List<CartUpdateDTO> cartUpdateDTOList = new ArrayList<>();
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(4)
                .quantity(10)
                .price(100000)
                .build();

        cartItemDTO1.setOption(ProductOptionDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .price(10000)
                .build());

        cartItemDTOList.add(cartItemDTO1);

        CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                .id(5)
                .quantity(10)
                .price(109000)
                .build();

        cartItemDTO2.setOption(ProductOptionDTO.builder()
                .id(1)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .price(10900)
                .build());

        cartItemDTOList.add(cartItemDTO2);

        for( CartItemDTO cart : cartItemDTOList){
            cartUpdateDTOList.add(CartUpdateDTO.fromCartItemDTO(cart));
        }

        CartRespUpdateDTO responseDTO = new CartRespUpdateDTO(cartUpdateDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));

    }


    @PostMapping("/carts/add")
    public ResponseEntity<?> add(@RequestBody List<CartRequest.CartAddDTO> cartAddDTOS){

        for(CartRequest.CartAddDTO cart : cartAddDTOS){
            System.out.println("Option Id :"+cart.getOptionId()+"/ Quantity : "+cart.getQuantity());
        }

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
                        .carts(cartItemDTOList)
                        .build()
        );

        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }


}
