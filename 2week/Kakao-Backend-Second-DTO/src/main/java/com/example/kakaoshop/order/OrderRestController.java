package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.CartRespItemDTO;
import com.example.kakaoshop.cart.response.CartRespProductDTO;
import com.example.kakaoshop.order.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class OrderRestController {

    //todo 결제하기 - 장바구니 모두 주문
    @PostMapping("/orders")
    public ResponseEntity<?> save() {

        List<CartRespItemDTO> cartRespItemDTOS = new ArrayList<>();
        cartRespItemDTOS.add(CartRespItemDTO.builder()
                        .id(4)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .quantity(10)
                        .price(100000)
                        .build());
        cartRespItemDTOS.add(CartRespItemDTO.builder()
                        .id(5)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .quantity(10)
                        .price(109000)
                        .build());


        List<CartRespProductDTO> cartRespProductDTOS = new ArrayList<>();
        cartRespProductDTOS.add(CartRespProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(cartRespItemDTOS)
                .build());

        OrderRespSaveDTO orderRespSaveDTO= OrderRespSaveDTO.builder()
                .id(2)
                .products(cartRespProductDTOS)
                .totalPrice(209000)
                .build();
        return ResponseEntity.ok(ApiUtils.success(orderRespSaveDTO));
    }

    //todo 주문결과 조회
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> find(@PathVariable int id) {
        List<OrderRespItemDTO> orderRespItemDTOS= new ArrayList<>();
        orderRespItemDTOS.add(OrderRespItemDTO.builder()
                        .id(4)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .quantity(10)
                        .price(100000)
                .build());
        orderRespItemDTOS.add(OrderRespItemDTO.builder()
                        .id(5)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .quantity(10)
                        .price(109000)
                        .build());

        List<OrderRespProductDTO> orderRespProductDTOS = new ArrayList<>();
        orderRespProductDTOS.add(OrderRespProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워 에디션 외 주방용품 특가전")
                .items(orderRespItemDTOS)
                .build()
        );
        OrderRespFindDTO orderRespFindDTO = OrderRespFindDTO.builder()
                .id(2)
                .products(orderRespProductDTOS)
                .totalPrice(209000)
                .build();
        return ResponseEntity.ok(ApiUtils.success(orderRespFindDTO));
    }
}
