package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
public class CartRestController {

    @PostMapping("/carts/add")
    public ResponseEntity<?> save(@RequestBody List<CartRequest.CreateDTO> cartRequestCreateDTOList) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

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
                        .cartItems(cartItemDTOList)
                        .build()
        );

        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@Valid @RequestBody List<CartRequest.UpdateDTO> cartRequestUpdateDTOList) {
        CartRespUpdateDTO responseDTO = null;
        List<CartOptionDTO> cartsDTO = new ArrayList<>();

        for(CartRequest.UpdateDTO cart: cartRequestUpdateDTOList) {
            if (cart.getCartId() == 4) {
                cartsDTO.add(CartOptionDTO.builder()
                        .cartId(4L)
                        .optionId(1)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .quantity(cart.getQuantity())
                        .price(100000).build());

            }else if (cart.getCartId() == 5) {
                cartsDTO.add(CartOptionDTO.builder()
                        .cartId(5L)
                        .optionId(2)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .quantity(cart.getQuantity())
                        .price(109000).build());

            }else {
                ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : ", HttpStatus.BAD_REQUEST));
            }
        }

        responseDTO = CartRespUpdateDTO.builder()
                .carts(cartsDTO)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
