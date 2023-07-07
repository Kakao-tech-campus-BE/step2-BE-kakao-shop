package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderProductDTO;
import com.example.kakaoshop.order.response.OrderRespDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderRestController {
    @PostMapping("/save")
    public ResponseEntity<?> orderSave() {
        List<OrderItemDTO.SaveDTO> itemSaveDTOs = new ArrayList<>();

        OrderItemDTO.SaveDTO orderItemDTO1 = OrderItemDTO.SaveDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        itemSaveDTOs.add(orderItemDTO1);

        OrderItemDTO.SaveDTO orderItemDTO2 = OrderItemDTO.SaveDTO.builder()
                .id(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        itemSaveDTOs.add(orderItemDTO2);

        List<OrderProductDTO.SaveDTO> productSaveDTOs = new ArrayList<>();

        OrderProductDTO.SaveDTO orderProductDTO = OrderProductDTO.SaveDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(itemSaveDTOs)
                .build();
        productSaveDTOs.add(orderProductDTO);

        OrderRespDTO.SaveDTO responseDTO = OrderRespDTO.SaveDTO.builder()
                .id(1)
                .products(productSaveDTOs)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> orderFindById(@PathVariable int id) {
        List<OrderItemDTO.ConfirmDTO> itemConfirmDTOs = new ArrayList<>();

        OrderItemDTO.ConfirmDTO orderItemDTO1 = OrderItemDTO.ConfirmDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        itemConfirmDTOs.add(orderItemDTO1);

        OrderItemDTO.ConfirmDTO orderItemDTO2 = OrderItemDTO.ConfirmDTO.builder()
                .id(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        itemConfirmDTOs.add(orderItemDTO2);

        List<OrderProductDTO.ConfirmDTO> productDTOs = new ArrayList<>();

        OrderProductDTO.ConfirmDTO orderProductDTO = OrderProductDTO.ConfirmDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(itemConfirmDTOs)
                .build();
        productDTOs.add(orderProductDTO);

        OrderRespDTO.ConfirmDTO responseDTO = OrderRespDTO.ConfirmDTO.builder()
                .id(id)
                .products(productDTOs)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}