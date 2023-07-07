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

    @PostMapping("/orders")
    public ResponseEntity<?> save() {
        List<OrderItemDTO> orderItemList = new ArrayList<>();
        orderItemList.add(OrderItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마에디션 4종")
                .quantity(10)
                .price(100000).build());
        orderItemList.add(OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디 5종")
                .quantity(10)
                .price(109000).build());

        List<OrderProductDTO> productDTOList = new ArrayList<>();

        // productDTO 리스트에 주문하는 아이템 담기
        productDTOList.add(OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워 에디션 외 주방용품 특가전")
                .orderItemList(orderItemList)
                .build()
        );

        // 주문정보에 productDTO 리스트 담기
        OrderDTO responseDTO = new OrderDTO(1, 209000, productDTOList);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){

        OrderedRespFindByIdDTO responseDTO = null;

        if(id == 1) {
            List<OrderedItemDTO> orderedItemList = new ArrayList<>();
            orderedItemList.add(OrderedItemDTO.builder()
                    .id(4)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스 에디션 4종")
                    .quantity(10)
                    .price(100000).build());
            orderedItemList.add(OrderedItemDTO.builder()
                    .id(5)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(109000).build());

            List<OrderedProductDTO> orderedProductList = new ArrayList<>();
            orderedProductList.add(OrderedProductDTO.builder()
                    .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                    .orderedItems(orderedItemList).build());
//
//            responseDTO = responseDTO.builder()
//                    .id(1)
//                    .orderedProducts(orderedProductList)
//                    .totalPrice(209000).build();
            responseDTO = new OrderedRespFindByIdDTO(1,orderedProductList, 209000);
        }else {
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문정보를 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
