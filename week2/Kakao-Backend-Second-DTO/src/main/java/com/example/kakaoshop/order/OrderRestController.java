package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class OrderRestController {

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        List<OrderItemDTO> orderItemDtoList = new ArrayList<>();
        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(4)
                .quantity(5)
                .price(50000)
                .build();
        orderItemDTO1.setOption(ProductOptionDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .price(10000)
                .build());
        orderItemDtoList.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(5)
                .quantity(5)
                .price(54500)
                .build();
        orderItemDTO2.setOption(ProductOptionDTO.builder()
                .id(1)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .price(10900)
                .build());
        orderItemDtoList.add(orderItemDTO2);

        List<ProductDTO> productDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productDTOList.add(
                ProductDTO.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .orderItems(orderItemDtoList)
                        .build()
        );


        OrderRespFindByIdDTO responseDTO = new OrderRespFindByIdDTO(id, productDTOList, 104500);


        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
