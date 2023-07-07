package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.item.OrderItemDTO;
import com.example.kakaoshop.order.item.OrderItemRespCheckDTO;
import com.example.kakaoshop.order.item.OrderItemRespDTO;
import com.example.kakaoshop.order.item.OrderProjectDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {

    @GetMapping("/orders/1")
    public ResponseEntity<?> order()
    {
        // 카트 아이템 리스트 만들기
        List<OrderItemDTO> orderItemDTO = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(4L)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(5L)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        // 추가 ( items 생성 )
        orderItemDTO.add(orderItemDTO1);
        orderItemDTO.add(orderItemDTO2);


        // OrderProjectDTO 리스트 만들기
        List<OrderProjectDTO> orderProjectDTO = new ArrayList<>();

        // productDTO 리스트에 담기
        orderProjectDTO.add(
                OrderProjectDTO.builder()
                        .productName( "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .orderItemDTO(orderItemDTO)
                        .build()
        );

        // OrderItemRespDTO 리스트 만들기
        OrderItemRespCheckDTO orderItemRespCheckDTO = new OrderItemRespCheckDTO(2L, orderProjectDTO, 209000);
        return ResponseEntity.ok(ApiUtils.success(orderItemRespCheckDTO));
    }

    @PostMapping("/orders/save")
    public ResponseEntity<?> orderSave() {
        // 카트 아이템 리스트 만들기
        List<OrderItemDTO> orderItemDTO = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(4L)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(5L)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        // 추가 ( items 생성 )
        orderItemDTO.add(orderItemDTO1);
        orderItemDTO.add(orderItemDTO2);


        // OrderProjectDTO 리스트 만들기
        List<OrderProjectDTO> orderProjectDTO = new ArrayList<>();

        // productDTO 리스트에 담기
        orderProjectDTO.add(
                OrderProjectDTO.builder()
                        .productName( "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .orderItemDTO(orderItemDTO)
                        .build()
        );

        // OrderItemRespDTO 리스트 만들기
        OrderItemRespDTO orderItemRespDTO = new OrderItemRespDTO(2L, orderProjectDTO, 209000);

        return ResponseEntity.ok(ApiUtils.success(orderItemRespDTO));
    }
}
