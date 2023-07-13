package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.item.OrderItem;
import com.example.kakaoshop.order.item.OrderProductDTO;
import com.example.kakaoshop.order.item.OrderRespDTO;
import com.example.kakaoshop.product.response.ProductRespFindByIdDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {

//    @GetMapping("/products/{id}")
//    public ResponseEntity<?> findById(@PathVariable int id) {
//        // 상품을 담을 DTO 생성
//        ProductRespFindByIdDTO responseDTO = null;
//
    @GetMapping("orders/{orderId}")
    public ResponseEntity<?> findById(@PathVariable int orderId){

        List<OrderItem> orderItemList = new ArrayList<>();
        List<OrderProductDTO> orderProductDTOS = new ArrayList<>();

        OrderItem orderItem01 = OrderItem.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        orderItemList.add(orderItem01);

        OrderItem orderItem02 = OrderItem.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        orderItemList.add(orderItem02);


        OrderProductDTO orderProductDTO01 = OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(orderItemList)
                .build();

        orderProductDTOS.add(orderProductDTO01);

        OrderRespDTO responseDTO = OrderRespDTO.builder()
                .id(orderId)
                .products(orderProductDTOS)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));

    }

    @PostMapping("orders/save")
    public ResponseEntity<?> save(){

        List<OrderItem> orderItemList = new ArrayList<>();
        List<OrderProductDTO> orderProductDTOS = new ArrayList<>();

        OrderItem orderItem01 = OrderItem.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        orderItemList.add(orderItem01);

        OrderItem orderItem02 = OrderItem.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        orderItemList.add(orderItem02);


        OrderProductDTO orderProductDTO01 = OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(orderItemList)
                .build();

        orderProductDTOS.add(orderProductDTO01);

        OrderRespDTO responseDTO = OrderRespDTO.builder()
                .id(2)
                .products(orderProductDTOS)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }


}
