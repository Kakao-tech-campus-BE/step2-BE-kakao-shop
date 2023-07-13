package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {

    @PostMapping("/orders/save")
    public ResponseEntity<?> save() {

        List<CartItemDTO> cartItemDTOList = new ArrayList<>();

        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        cartItemDTOList.add(cartItemDTO1);
        CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        cartItemDTOList.add(cartItemDTO2);

        List<ProductDTO> productDTOList = new ArrayList<>();
        ProductDTO productDTO = ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(cartItemDTOList)
                .build();
        productDTOList.add(productDTO);

        OrderRespSaveDTO responseDTO = new OrderRespSaveDTO(1, productDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));

    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {

        OrderRespFindByIdDTO responseDTO = null;

        if (id == 1) {

            List<CartItemDTO> cartItemDTOList = new ArrayList<>();
            CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                    .id(4)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(10)
                    .price(100000)
                    .build();
            cartItemDTOList.add(cartItemDTO1);
            CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                    .id(5)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(109000)
                    .build();
            cartItemDTOList.add(cartItemDTO2);

            List<ProductDTO> productDTOList = new ArrayList<>();
            ProductDTO productDTO = ProductDTO.builder()
                    .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                    .items(cartItemDTOList)
                    .build();
            productDTOList.add(productDTO);

            responseDTO = new OrderRespFindByIdDTO(1, productDTOList, 209000);
        } else {
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));

    }
}
