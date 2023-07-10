package com.example.kakaoshop.order;


import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.CartItemDTO;
import com.example.kakaoshop.cart.response.CartRespFindAllDTO;
import com.example.kakaoshop.cart.response.ProductDTO;
import com.example.kakaoshop.cart.response.ProductOptionDTO;
import com.example.kakaoshop.order.item.OrderItemDTO;
import com.example.kakaoshop.order.item.OrderProductDTO;
import com.example.kakaoshop.order.item.OrderRespFindAllDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderRestController {
    @PostMapping("")
    public ResponseEntity<?> saveOrder() {
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

        orderItemDTOs.add(OrderItemDTO.builder()
                        .id(4)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .quantity(10)
                        .price(100000)
                .build());

        orderItemDTOs.add(OrderItemDTO.builder()
                        .id(5)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .quantity(10)
                        .price(100000)
                .build());

        List<OrderProductDTO> orderProductDTOs = new ArrayList<>();
        orderProductDTOs.add(OrderProductDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .items(orderItemDTOs)
                .build());

        OrderRespFindAllDTO responseDTO = OrderRespFindAllDTO.builder()
                .id(2)
                .products(orderProductDTOs)
                .totalPrice(200000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable int id){
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

        if (id == 2) {
            orderItemDTOs.add(OrderItemDTO.builder()
                    .id(4)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(10)
                    .price(100000)
                    .build());

            orderItemDTOs.add(OrderItemDTO.builder()
                    .id(5)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(100000)
                    .build());

            List<OrderProductDTO> orderProductDTOs = new ArrayList<>();
            orderProductDTOs.add(OrderProductDTO.builder()
                    .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                    .items(orderItemDTOs)
                    .build());

            OrderRespFindAllDTO responseDTO = OrderRespFindAllDTO.builder()
                    .id(2)
                    .products(orderProductDTOs)
                    .totalPrice(200000)
                    .build();


            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }
        else return new ResponseEntity<>(ApiUtils.error("존재하지 않는 주문번호입니다.", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}
