package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItemSaveDTO;
import com.example.kakaoshop.order.response.OrderRespSaveDTO;
import com.example.kakaoshop.order.response.ProductSaveDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(){
        // 주문 아이템 리스트 만들기
        List<OrderItemSaveDTO> orderItemSaveDTOList = new ArrayList<>();

        // 주문 아이템 리스트에 담기
        OrderItemSaveDTO orderItemSaveDTO1 = OrderItemSaveDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        orderItemSaveDTOList.add(orderItemSaveDTO1);

        OrderItemSaveDTO orderItemSaveDTO2 = OrderItemSaveDTO.builder()
                .id(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        orderItemSaveDTOList.add(orderItemSaveDTO2);

        // productDTO 리스트 만들기
        List<ProductSaveDTO> productSaveDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productSaveDTOList.add(
                ProductSaveDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .items(orderItemSaveDTOList)
                        .build()
        );

        OrderRespSaveDTO responseDTO = new OrderRespSaveDTO(1, productSaveDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
