package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
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
        List<CartItemDTO> cartItemDTOList1 = new ArrayList<>();
        // productDTO 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();
        // 카트 아이템 리스트에 담기
        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(1)
                .quantity(3)
                .price(26700)
                .build();
        cartItemDTO1.setOption(ProductOptionDTO.builder()
                                .id(5)
                                .optionName("2겹 식빵수세미 6매")
                                .price(8900)
                                .build());
        cartItemDTOList1.add(cartItemDTO1);

        // productDTO 리스트에 담기
        productDTOList.add(
                ProductDTO.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .carts(cartItemDTOList1)
                        .build()
        );


        // 카트 아이템 리스트 만들기
        List<CartItemDTO> cartItemDTOList2 = new ArrayList<>();
        // 카트 아이템 리스트에 담기
        CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                .id(2)
                .quantity(4)
                .price(199600)
                .build();
        cartItemDTO2.setOption(ProductOptionDTO.builder()
                .id(10)
                .optionName("JR310BT (무선 전용) - 레드")
                .price(49900)
                .build());
        cartItemDTOList2.add(cartItemDTO2);
        // 카트 아이템 리스트에 담기
        CartItemDTO cartItemDTO3 = CartItemDTO.builder()
                .id(3)
                .quantity(5)
                .price(249500)
                .build();
        cartItemDTO3.setOption(ProductOptionDTO.builder()
                .id(11)
                .optionName("JR310BT (무선 전용) - 그린")
                .price(49900)
                .build());
        cartItemDTOList2.add(cartItemDTO3);

        // productDTO 리스트에 담기
        productDTOList.add(
                ProductDTO.builder()
                        .id(3)
                        .productName("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!")
                        .carts(cartItemDTOList2)
                        .build()
        );

        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 475800);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
    @PostMapping("/carts/add")
    public ResponseEntity<?> addItem(@RequestBody List<CartRequest.AddDTO> addDTOList){
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        // 추가할 카트 아이템들을 리스트에 담기
        for (CartRequest.AddDTO data:addDTOList) {
            cartItemDTOList.add(CartItemDTO.builder()
                    .id(data.getOptionId())
                    .quantity(data.getQuantity())
                    .price(50000)
                    .build()
            );
        }
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PutMapping("carts/update")
    public ResponseEntity<?> updateItem(@RequestBody List<CartRequest.UpdateDTO> updateDTOList){
        //update한 후의 option들을 리스트로 하여 return
        List<UpdateItemDTO> updateItemDTOList = new ArrayList<>();
        UpdateItemDTO updateItemDTO1 = UpdateItemDTO.builder()
                .cartId(1)
                .optionId(5)
                .optionName("2겹 식빵수세미 6매")
                .quantity(3)
                .price(26700)
                .build();
        UpdateItemDTO updateItemDTO2 = UpdateItemDTO.builder()
                .cartId(2)
                .optionId(10)
                .optionName("JR310BT (무선 전용) - 레드")
                .quantity(5)
                .price(249500)
                .build();

        UpdateItemDTO updateItemDTO3 = UpdateItemDTO.builder()
                .cartId(3)
                .optionId(11)
                .optionName("JR310BT (무선 전용) - 그린")
                .quantity(5)
                .price(249500)
                .build();

        updateItemDTOList.add(updateItemDTO1);
        updateItemDTOList.add(updateItemDTO2);
        updateItemDTOList.add(updateItemDTO3);


        CartUpdateDTO responseDTO = new CartUpdateDTO(updateItemDTOList, 525700);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}
