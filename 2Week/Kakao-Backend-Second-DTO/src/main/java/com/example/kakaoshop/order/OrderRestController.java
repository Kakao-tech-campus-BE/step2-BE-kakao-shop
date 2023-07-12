package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {
    @PostMapping("/orders")
    public ResponseEntity<?> saveOrders(){
        List<OrderSaveItemDTO> orderSaveItemsList = new ArrayList<>();

        OrderSaveItemDTO orderSaveItem1 = OrderSaveItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        orderSaveItemsList.add(orderSaveItem1);

        OrderSaveItemDTO orderSaveItem2 = OrderSaveItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();

        orderSaveItemsList.add(orderSaveItem2);

        List<OrderSaveProductDTO> orderSaveProductsList = new ArrayList<>();

        orderSaveProductsList.add(
                OrderSaveProductDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .orderItems(orderSaveItemsList)
                        .build()
        );

        RespSaveOrdersDTO responseDTO = new RespSaveOrdersDTO(1, orderSaveProductsList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){

        if(id == 1){
            List<OrderResultItemDTO> orderResultItemsList = new ArrayList<>();

            OrderResultItemDTO orderResultItem1 = OrderResultItemDTO.builder()
                    .id(4)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(10)
                    .price(100000)
                    .build();

            orderResultItemsList.add(orderResultItem1);

            OrderResultItemDTO orderResultItem2 = OrderResultItemDTO.builder()
                    .id(5)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(109000)
                    .build();

            orderResultItemsList.add(orderResultItem2);

            List<OrderResultProductDTO> orderResultProductsList = new ArrayList<>();

            orderResultProductsList.add(
                    OrderResultProductDTO.builder()
                            .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                            .orderItems(orderResultItemsList)
                            .build()
            );

            RespFindByIdDTO responseDTO = new RespFindByIdDTO(1, orderResultProductsList, 209000);

            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }

        return ResponseEntity.ok(ApiUtils.success("주문 항목이 없습니다."));
    }
}
