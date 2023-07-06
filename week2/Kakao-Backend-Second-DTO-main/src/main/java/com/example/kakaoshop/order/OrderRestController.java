package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderProductItemDTO;
import com.example.kakaoshop.order.response.OrderRespFindResultDTO;
import com.example.kakaoshop.order.response.OrderRespInsertDTO;
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
    public ResponseEntity<?> insertOrder(){

        List<OrderItemDTO> orderItemList = new ArrayList<>();
        orderItemList.add(
                OrderItemDTO.builder()
                        .id(4)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .quantity(10)
                        .price(100000)
                        .build()
        );
        orderItemList.add(
                OrderItemDTO.builder()
                        .id(5)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .quantity(10)
                        .price(109000)
                        .build()
        );

        List<OrderProductItemDTO> productItemList = new ArrayList<>();
        productItemList.add(
                OrderProductItemDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .items(orderItemList)
                        .build()
        );

        OrderRespInsertDTO responseDTO = new OrderRespInsertDTO(1, productItemList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findOrderResult(@PathVariable int id){

        List<OrderItemDTO> orderItemList = new ArrayList<>();
        orderItemList.add(
                OrderItemDTO.builder()
                        .id(4)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .quantity(10)
                        .price(100000)
                        .build()
        );
        orderItemList.add(
                OrderItemDTO.builder()
                        .id(5)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .quantity(10)
                        .price(109000)
                        .build()
        );

        List<OrderProductItemDTO> productItemList = new ArrayList<>();
        productItemList.add(
                OrderProductItemDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .items(orderItemList)
                        .build()
        );

        OrderRespFindResultDTO responseDTO = new OrderRespFindResultDTO(1, productItemList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
