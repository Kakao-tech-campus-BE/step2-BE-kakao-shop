package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderProductDTO;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderSaveDTO;
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
    public ResponseEntity<?> save(){
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .price(100000)
                .quantity(10)
                .build();
        orderItemDTOList.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .price(109000)
                .quantity(10)
                .build();
        orderItemDTOList.add(orderItemDTO2);

        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();

        OrderProductDTO orderProductDTO = OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(orderItemDTOList)
                .build();

        orderProductDTOList.add(orderProductDTO);

        OrderSaveDTO orderSaveDTO = OrderSaveDTO.builder()
                .id(2)
                .products(orderProductDTOList)
                .totalPrice(209000)
                .build();
        return ResponseEntity.ok(ApiUtils.success(orderSaveDTO));
    }
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findAll(@PathVariable Integer id){
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .price(100000)
                .quantity(10)
                .build();
        orderItemDTOList.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .price(109000)
                .quantity(10)
                .build();
        orderItemDTOList.add(orderItemDTO2);

        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();

        OrderProductDTO orderProductDTO = OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(orderItemDTOList)
                .build();

        orderProductDTOList.add(orderProductDTO);

        OrderSaveDTO orderSaveDTO = OrderSaveDTO.builder()
                .id(2)
                .products(orderProductDTOList)
                .totalPrice(209000)
                .build();
        return ResponseEntity.ok(ApiUtils.success(orderSaveDTO));
    }

}
