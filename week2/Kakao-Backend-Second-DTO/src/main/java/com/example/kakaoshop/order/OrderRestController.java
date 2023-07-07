package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.item.OrderItem;
import com.example.kakaoshop.order.response.ProductDTO;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderRespFindAllDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {
    @PostMapping("/orders/save")
    public ResponseEntity<?> save() {
        // 결제 로직 구현

        return ResponseEntity.ok("save");
    }

    @GetMapping("orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        List<OrderItemDTO> OrderItemDTOList = new ArrayList<>();

        OrderItemDTO option1 = OrderItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스 에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        OrderItemDTOList.add(option1);

        OrderItemDTO option2 = OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        OrderItemDTOList.add(option2);

        ProductDTO product = ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 외 주방용품 특가전")
                .items(OrderItemDTOList)
                .build();

        OrderRespFindAllDTO orderRespFindAllDTO = new OrderRespFindAllDTO(1, product, 209000);

        return ResponseEntity.ok(ApiUtils.success(orderRespFindAllDTO));
    }
}