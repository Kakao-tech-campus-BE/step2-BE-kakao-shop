package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.CartItemDTO;
import com.example.kakaoshop.cart.response.CartRespFindAllDTO;
import com.example.kakaoshop.cart.response.CartProductOptionDTO;
import com.example.kakaoshop.cart.response.CartProductDTO;
import com.example.kakaoshop.order.response.OrderCartDTO;
import com.example.kakaoshop.order.response.OrderItemDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id)
    {
        /*
        카트에 담긴 내용물들 Mock data
         */
        OrderCartDTO responseDTO = null;

        List<CartItemDTO> cartItemDTOList = new ArrayList<>();

        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(4)
                .quantity(5)
                .price(50000)
                .build();
        cartItemDTO1.setOption(CartProductOptionDTO.builder()
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
        cartItemDTO2.setOption(CartProductOptionDTO.builder()
                .id(1)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .price(10900)
                .build());
        cartItemDTOList.add(cartItemDTO2);

        List<CartProductDTO> cartProductDTOList = new ArrayList<>();

        cartProductDTOList.add(
                CartProductDTO.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .cartItems(cartItemDTOList)
                        .build()
        );

        int totalPrice = 104500;
        CartRespFindAllDTO cartRespFindAllDTODTO = new CartRespFindAllDTO(cartProductDTOList, totalPrice);
        OrderItemDTO orderItemDTO = new OrderItemDTO(1,totalPrice, cartRespFindAllDTODTO.getProducts());

        if (id == 1) {
            responseDTO = new OrderCartDTO(1, totalPrice, cartRespFindAllDTODTO);
        }
        else {
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 카트를 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
