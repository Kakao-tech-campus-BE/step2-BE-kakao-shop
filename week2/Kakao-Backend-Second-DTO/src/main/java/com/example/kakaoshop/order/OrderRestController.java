package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.item.OrderItem;
import com.example.kakaoshop.order.item.OrderProducts;
import com.example.kakaoshop.order.item.OrderResponseDTO;
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
    /*
        추가되어야 할 API:
        - 결제
            /orders/save
        - 주문 결과 확인
            /orders/{id}
    */
    @PostMapping("/orders/save")
    public ResponseEntity<?> makeOrder() {
        List<OrderItem> items = new ArrayList<>();
        OrderItem orderItem01 = OrderItem.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        OrderItem orderItem02 = OrderItem.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        items.add(orderItem01);
        items.add(orderItem02);

        List<OrderProducts> products = new ArrayList<>();
        OrderProducts orderProducts01 = OrderProducts.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션")
                .items(items)
                .build();
        products.add(orderProducts01);

        OrderResponseDTO response = new OrderResponseDTO(2, products, 209000);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        OrderResponseDTO response = null;

        if(id == 2){
            List<OrderItem> items = new ArrayList<>();
            OrderItem orderItem01 = OrderItem.builder()
                    .id(4)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(10)
                    .price(100000)
                    .build();
            OrderItem orderItem02 = OrderItem.builder()
                    .id(5)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(109000)
                    .build();
            items.add(orderItem01);
            items.add(orderItem02);

            List<OrderProducts> products = new ArrayList<>();
            OrderProducts orderProducts01 = OrderProducts.builder()
                    .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션")
                    .items(items)
                    .build();
            products.add(orderProducts01);

            response = new OrderResponseDTO(2, products, 209000);
        }else {
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
