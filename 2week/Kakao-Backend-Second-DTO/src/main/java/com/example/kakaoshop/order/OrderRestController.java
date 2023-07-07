package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {
    // 주문 조회
    @PostMapping("/orders/{id}")
    public ResponseEntity<?> findAll(@PathVariable int id){
        // 주문 아이템 리스트 만들기
        List<ProductOptionDTO> productOptionDTOList = new ArrayList<>();

        if(id == 1) {
            // 1번 주문 아이템 리스트에 담기
            ProductOptionDTO productOptionDTO1 = ProductOptionDTO.builder()
                    .id(4)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(5)
                    .price(10000)
                    .build();
            productOptionDTOList.add(productOptionDTO1);

            ProductOptionDTO productOptionDTO2 = ProductOptionDTO.builder()
                    .id(5)
                    .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                    .quantity(5)
                    .price(10900)
                    .build();
            productOptionDTOList.add(productOptionDTO2);
        } else if (id == 2) {
            // 2번 주문 아이템 리스트에 담기
            ProductOptionDTO productOptionDTO1 = ProductOptionDTO.builder()
                    .id(4)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(1)
                    .price(10000)
                    .build();
            productOptionDTOList.add(productOptionDTO1);

            ProductOptionDTO productOptionDTO2 = ProductOptionDTO.builder()
                    .id(5)
                    .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                    .quantity(3)
                    .price(10900)
                    .build();
            productOptionDTOList.add(productOptionDTO2);
        }else{
            // 주문 정보 없음
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        // 주문 상품 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();

        productDTOList.add(
                ProductDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .items(productOptionDTOList)
                        .build()
        );

        // 주문 반환 객체 만들기
        OrderRespFindAllDTO responseDTO = new OrderRespFindAllDTO(1, productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // 주문하기
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(){
        // 주문 저장 아이템 리스트 만들기
        List<OrderSaveItemDTO> orderSaveItemDTOList = new ArrayList<>();

        // 주문 저장 아이템 리스트에 담기
        OrderSaveItemDTO orderSaveItemDTO1 = OrderSaveItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(5)
                .price(10000)
                .build();
        orderSaveItemDTOList.add(orderSaveItemDTO1);

        OrderSaveItemDTO orderSaveItemDTO2 = OrderSaveItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .quantity(5)
                .price(10900)
                .build();
        orderSaveItemDTOList.add(orderSaveItemDTO2);

        // 주문 저장 상품 리스트 만들기
        List<OrderSaveProductDTO> orderSaveProductDTOList = new ArrayList<>();

        orderSaveProductDTOList.add(
                OrderSaveProductDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .items(orderSaveItemDTOList)
                        .build()
        );

        // 주문 저장 객체 만들기
        OrderSaveDTO responseDTO = new OrderSaveDTO(1, orderSaveProductDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
