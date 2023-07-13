package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.orderResult.OrderResultItemDTO;
import com.example.kakaoshop.order.response.orderResult.OrderResultProductDTO;
import com.example.kakaoshop.order.response.orderResult.OrderResultResDTO;
import com.example.kakaoshop.order.response.orderSave.OrderSaveItemDTO;
import com.example.kakaoshop.order.response.orderSave.OrderSaveProductDTO;
import com.example.kakaoshop.order.response.orderSave.OrderSaveResDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {

    @PostMapping("/orders/save")
    public ResponseEntity<?> orderSave(){
        List<OrderSaveItemDTO> orderItemDTOS = new ArrayList<>();
        orderItemDTOS.add(new OrderSaveItemDTO(
                4L,
                "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                10L,
                100000L
        ));
        orderItemDTOS.add(new OrderSaveItemDTO(
                5L,
                "02. 슬라이딩 지퍼백 플라워에디션 5종",
                10L,
                109000L
        ));
        OrderSaveResDTO orderSaveResDTO = new OrderSaveResDTO(
                2L,
                new OrderSaveProductDTO(
                        "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                        orderItemDTOS),
                209000L
        );

        return ResponseEntity.ok(ApiUtils.success(orderSaveResDTO));
    }

    // 주문 결과 확인
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> orderResult(@PathVariable String orderId){
        List<OrderResultItemDTO> orderResultItemDTOS = new ArrayList<>();
        orderResultItemDTOS.add(new OrderResultItemDTO(
                4L,
                "01. 슬라이딩 지퍼백 크리스마스에디션 4종.",
                10L,
                100000L
        ));
        orderResultItemDTOS.add(new OrderResultItemDTO(
                5L,
                "02. 슬라이딩 지퍼백 플라워에디션 5종",
                10L,
                109000L
        ));

        OrderResultProductDTO orderResultProductDTO = new OrderResultProductDTO(
                "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                orderResultItemDTOS
        );

        OrderResultResDTO orderResultResDTO = new OrderResultResDTO(
                2L,
                orderResultProductDTO,
                209000L
        );

        return ResponseEntity.ok(ApiUtils.success(orderResultResDTO));
    }
}
