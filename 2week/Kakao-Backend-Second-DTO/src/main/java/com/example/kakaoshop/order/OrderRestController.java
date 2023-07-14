package com.example.kakaoshop.order;


import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderProductDTO;
import com.example.kakaoshop.order.response.OrderRespFindByIdDTO;
import com.example.kakaoshop.order.response.OrderSavedDTO;
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

    //orders/save
    @PostMapping("/orders/save")
    public ResponseEntity<?> saveOrder() {

        //오더의 products 리스트
        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();

        //오더의 items 리스트 만들기
        List<OrderItemDTO> orderItemDTOList1 = new ArrayList<>();

        //오더 아이템 리스트에 담기
        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(4)
                .optionName( "01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .price(100000)
                .quantity(10)
                .build();

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(5)
                .optionName( "02. 슬라이딩 지퍼백 플라워에디션 5종")
                .price(109000)
                .quantity(10)
                .build();

        orderItemDTOList1.add(orderItemDTO1);
        orderItemDTOList1.add(orderItemDTO2);

        //order product 넣어주기
        orderProductDTOList.add(OrderProductDTO.builder()
                .productName( "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(orderItemDTOList1)
                .build());

        OrderSavedDTO responseDTO = new OrderSavedDTO(2,orderProductDTOList,209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    //orders/{id}
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {

        //order을 담을 DTO 생성
        OrderRespFindByIdDTO responseDTO = null;

        if(id == 2) {
        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();
        List<OrderItemDTO> orderItemDTOList1 = new ArrayList<>();
        orderItemDTOList1.add(
                OrderItemDTO.builder()
                        .id(4)
                        .optionName( "01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .price(100000)
                        .quantity(10)
                        .build()
        );
        orderItemDTOList1.add(
                    OrderItemDTO.builder()
                            .id(5)
                            .optionName( "02. 슬라이딩 지퍼백 플라워에디션 5종")
                            .price(109000)
                            .quantity(10)
                            .build()
        );
        orderProductDTOList.add(new OrderProductDTO("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                orderItemDTOList1));
        responseDTO = new OrderRespFindByIdDTO(2, orderProductDTOList,209000);

        }else {
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));

    }
}
