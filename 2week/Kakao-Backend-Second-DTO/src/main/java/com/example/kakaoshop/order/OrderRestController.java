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
    public ResponseEntity<?> findAll() {
        // 주문 아이템 리스트 만들기
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        // 주문 아이템 리스트에 담기
        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(4)
                .quantity(10)
                .price(100000)
                .option(ProductOptionDTO.builder()
                        .id(1)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .price(10000)
                        .build())
                .build();
        orderItemDTOList.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(5)
                .quantity(10)
                .price(109000)
                .option(ProductOptionDTO.builder()
                        .id(1)
                        .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                        .price(10900)
                        .build())
                .build();
        orderItemDTOList.add(orderItemDTO2);

        // productDTO 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productDTOList.add(
                ProductDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .orderItems(orderItemDTOList)
                        .build()
        );

        OrderRespFindAllDTO responseDTO = new OrderRespFindAllDTO(2, productDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 주문 상품을 담을 DTO 생성
        OrderRespFindByIdDTO responseDTO = null;

        if(id == 1) {
            // 주문 아이템 리스트 만들기
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

            // 주문 아이템 리스트에 담기
            OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                    .id(4)
                    .quantity(10)
                    .price(100000)
                    .option(ProductOptionDTO.builder()
                            .id(1)
                            .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                            .price(10000)
                            .build())
                    .build();
            orderItemDTOList.add(orderItemDTO1);

            OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                    .id(5)
                    .quantity(10)
                    .price(109000)
                    .option(ProductOptionDTO.builder()
                            .id(1)
                            .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                            .price(10900)
                            .build())
                    .build();
            orderItemDTOList.add(orderItemDTO2);

            // productDTO 리스트 만들기
            List<ProductDTO> productDTOList = new ArrayList<>();

            // productDTO 리스트에 담기
            productDTOList.add(
                    ProductDTO.builder()
                            .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                            .orderItems(orderItemDTOList)
                            .build()
            );

            responseDTO = new OrderRespFindByIdDTO(id, productDTOList, 209000);

        }else {
            return ResponseEntity.badRequest().body(ApiUtils.error("주문 내역을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
