package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderProductDTO;
import com.example.kakaoshop.order.response.OrderRespSaveDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderRestController {
    @PostMapping("/save")
    public ResponseEntity<?> orderSave() {
        List<OrderItemDTO> itemDTOList = new ArrayList<>();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        itemDTOList.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        itemDTOList.add(orderItemDTO2);

        List<OrderProductDTO> productDTOList = new ArrayList<>();

        OrderProductDTO orderProductDTO = OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(itemDTOList)
                .build();
        productDTOList.add(orderProductDTO);

        OrderRespSaveDTO responseDTO = OrderRespSaveDTO.builder()
                .id(1)
                .products(productDTOList)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> orderFindById(@PathVariable int id) {
        List<OrderItemDTO> itemDTOList = new ArrayList<>();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        itemDTOList.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        itemDTOList.add(orderItemDTO2);

        List<OrderProductDTO> productDTOList = new ArrayList<>();

        OrderProductDTO orderProductDTO = OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(itemDTOList)
                .build();
        productDTOList.add(orderProductDTO);

        OrderRespSaveDTO responseDTO = OrderRespSaveDTO.builder()
                .id(id)
                .products(productDTOList)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}