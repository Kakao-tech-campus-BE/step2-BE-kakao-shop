package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderItemRespFindByIdDTO;
import com.example.kakaoshop.order.response.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderRestController {

    @GetMapping("/{orderId}")
    public ResponseEntity<?> findById(@PathVariable Long orderId) {

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        OrderItemDTO orderItemDTODTO1 = OrderItemDTO.builder()
                .id(4L)
                .optionName("01. 슬라이딩 지퍼백 크리스맛에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        OrderItemDTO orderItemDTODTO2 = OrderItemDTO.builder()
                .id(5L)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();

        orderItemDTOList.add(orderItemDTODTO1);
        orderItemDTOList.add(orderItemDTODTO2);


        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(
                ProductDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 외 주방용품 특가전")
                        .items(orderItemDTOList)
                        .build()
        );

        OrderItemRespFindByIdDTO responseDTO = new OrderItemRespFindByIdDTO(2L, productDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }


    @PostMapping("")
    public ResponseEntity<?> saveOrder() {

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        OrderItemDTO orderItemDTODTO1 = OrderItemDTO.builder()
                .id(4L)
                .optionName("01. 슬라이딩 지퍼백 크리스맛에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        OrderItemDTO orderItemDTODTO2 = OrderItemDTO.builder()
                .id(5L)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();

        orderItemDTOList.add(orderItemDTODTO1);
        orderItemDTOList.add(orderItemDTODTO2);


        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(
                ProductDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 외 주방용품 특가전")
                        .items(orderItemDTOList)
                        .build()
        );

        OrderItemRespFindByIdDTO responseDTO = new OrderItemRespFindByIdDTO(2L, productDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));

    }
}
