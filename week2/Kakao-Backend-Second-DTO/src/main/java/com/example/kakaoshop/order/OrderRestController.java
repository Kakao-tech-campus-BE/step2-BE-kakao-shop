package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderRespFindByIdDTO;
import com.example.kakaoshop.order.response.OrderRespSaveDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderRestController {
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable int orderId){
        OrderRespFindByIdDTO response = new OrderRespFindByIdDTO(
                2,
                List.of(new OrderRespFindByIdDTO.ProductDTO("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                        List.of(new OrderRespFindByIdDTO.OrderItemDTO(4, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10, 100000),
                                new OrderRespFindByIdDTO.OrderItemDTO(5, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10, 109000)))),
                209000);
        return ResponseEntity.ok().body(ApiUtils.success(response));
    }

    @PostMapping("/orders")
    public ResponseEntity<?> saveOrder(){
        OrderRespSaveDTO response = new OrderRespSaveDTO(
                2,
                List.of(new OrderRespSaveDTO.ProductDTO("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                        List.of(new OrderRespSaveDTO.OrderItemDTO(4, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10, 100000),
                                new OrderRespSaveDTO.OrderItemDTO(5, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10, 109000)))),
                209000);
        return ResponseEntity.ok().body(ApiUtils.success(response));
    }
}
