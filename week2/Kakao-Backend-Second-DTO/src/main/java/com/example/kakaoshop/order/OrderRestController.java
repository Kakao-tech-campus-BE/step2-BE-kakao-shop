package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderRespFindByIdDTO;
import com.example.kakaoshop.order.response.OrderRespSaveDTO;
import com.example.kakaoshop.order.response.ProductOrderItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderRestController {

    @PostMapping("/save")
    public ResponseEntity<?> orderSave() {

        OrderRespSaveDTO responseDTO = null;

        List<ProductOrderItemDTO> products = new ArrayList<>();
        List<OrderItemDTO> items = new ArrayList<>();

        items.add(OrderItemDTO.builder()
                .id(4L)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build());

        items.add(OrderItemDTO.builder()
                .id(5L)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 4종")
                .quantity(10)
                .price(109000)
                .build());

        products.add(ProductOrderItemDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전")
                .items(items)
                .build());

        responseDTO = OrderRespSaveDTO.builder()
                .id(1L)
                .products(products)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        OrderRespFindByIdDTO responseDTO = null;

        if(id == 1) {
            List<ProductOrderItemDTO> products = new ArrayList<>();
            List<OrderItemDTO> items = new ArrayList<>();

            items.add(OrderItemDTO.builder()
                    .id(4L)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(10)
                    .price(100000)
                    .build());

            items.add(OrderItemDTO.builder()
                    .id(5L)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 4종")
                    .quantity(10)
                    .price(109000)
                    .build());

            products.add(ProductOrderItemDTO.builder()
                    .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전")
                    .items(items)
                    .build());

            responseDTO = OrderRespFindByIdDTO.builder()
                    .id(1L)
                    .products(products)
                    .totalPrice(209000)
                    .build();

            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }
        return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
    }
}
