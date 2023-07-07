package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.*;
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
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrders(@PathVariable int id) {

        if (id == 1) {

            List<OrderItemRespFindByIdDTO> items = new ArrayList<>();

            items.add(OrderItemRespFindByIdDTO.builder()
                    .id(4)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(10)
                    .price(10000)
                    .build());

            items.add(OrderItemRespFindByIdDTO.builder()
                    .id(5)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(109000)
                    .build());

            List<ProductRespOrderFindByIdDTO> products = new ArrayList<>();

            ProductRespOrderFindByIdDTO productRespOrderFindAllDTO = new ProductRespOrderFindByIdDTO("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", items);
            products.add(productRespOrderFindAllDTO);

            OrderRespFindByIdDTO orderRespFindByIdDTO = new OrderRespFindByIdDTO(1, products, 209000);

            return ResponseEntity.ok().body(ApiUtils.success(orderRespFindByIdDTO));
        }

        else return ResponseEntity.badRequest().body(ApiUtils.error("해당 상품을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder() {
        List<OrderItemRespAddDTO> items = new ArrayList<>();

        items.add(OrderItemRespAddDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(10000)
                .build());

        items.add(OrderItemRespAddDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build());

        List<ProductRespOrderAddDTO> products = new ArrayList<>();

        ProductRespOrderAddDTO productRespOrderAddDTO1 = new ProductRespOrderAddDTO("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", items);
        products.add(productRespOrderAddDTO1);

        OrderRespAddDTO orderRespAddDTO = new OrderRespAddDTO(2, products, 209000);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success(orderRespAddDTO));
    }
}
