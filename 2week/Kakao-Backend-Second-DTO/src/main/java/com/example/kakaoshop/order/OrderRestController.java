package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;

import com.example.kakaoshop.order.item.OrderItem;
import com.example.kakaoshop.order.item.OrderRespFindAllDTO;
import com.example.kakaoshop.order.item.ProductDTO;
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
    public ResponseEntity<?> findAll(@PathVariable int id) {
        // 카트 아이템 리스트 만들기
        List<OrderItem> orderItemList = new ArrayList<>();

        // productDTO 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        if (id == 1) {
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

            // productDTO 리스트에 담기
            productDTOList.add(
                    ProductDTO.builder()
                            .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                            .items(orderItemList)
                            .build()
            );

        } else {
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 상품을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        OrderRespFindAllDTO responseDTO = new OrderRespFindAllDTO(2, productDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // 주문 생성하기 (결제하기)
    @PostMapping ("/orders")
    public ResponseEntity<?> makeOrder(){
        // 카트 아이템 리스트 만들기
        List<OrderItem> orderItemList = new ArrayList<>();

        // productDTO 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();

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

        // productDTO 리스트에 담기
        productDTOList.add(
                ProductDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .items(orderItemList)
                        .build()
        );

        OrderRespFindAllDTO responseDTO = new OrderRespFindAllDTO(2, productDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
