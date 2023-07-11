package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItem;
import com.example.kakaoshop.order.response.OrderProduct;
import com.example.kakaoshop.order.response.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderRestController {
    @PostMapping("/save")
    public ResponseEntity<?> orderSave() {
        List<OrderItem.SaveDTO> itemSaveDTOs = new ArrayList<>();

        OrderItem.SaveDTO orderItemDTO1 = OrderItem.SaveDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        itemSaveDTOs.add(orderItemDTO1);

        OrderItem.SaveDTO orderItemDTO2 = OrderItem.SaveDTO.builder()
                .id(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        itemSaveDTOs.add(orderItemDTO2);

        List<OrderProduct.SaveDTO> productSaveDTOs = new ArrayList<>();

        OrderProduct.SaveDTO orderProductDTO = OrderProduct.SaveDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(itemSaveDTOs)
                .build();
        productSaveDTOs.add(orderProductDTO);

        OrderResponse.SaveDTO responseDTO = OrderResponse.SaveDTO.builder()
                .id(1)
                .products(productSaveDTOs)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> orderFindById(@PathVariable int id) {
        List<OrderItem.ConfirmDTO> itemConfirmDTOs = new ArrayList<>();

        OrderItem.ConfirmDTO orderItemDTO1 = OrderItem.ConfirmDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        itemConfirmDTOs.add(orderItemDTO1);

        OrderItem.ConfirmDTO orderItemDTO2 = OrderItem.ConfirmDTO.builder()
                .id(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        itemConfirmDTOs.add(orderItemDTO2);

        List<OrderProduct.ConfirmDTO> productDTOs = new ArrayList<>();

        OrderProduct.ConfirmDTO orderProductDTO = OrderProduct.ConfirmDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(itemConfirmDTOs)
                .build();
        productDTOs.add(orderProductDTO);

        OrderResponse.ConfirmDTO responseDTO = OrderResponse.ConfirmDTO.builder()
                .id(id)
                .products(productDTOs)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}