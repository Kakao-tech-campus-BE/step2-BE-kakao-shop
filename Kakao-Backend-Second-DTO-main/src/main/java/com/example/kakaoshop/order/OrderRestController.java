package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.item.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderProductDTO;
import com.example.kakaoshop.order.response.OrderResponseFindByIdDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findOrderById(@PathVariable int id){
        // 주문 번호는 모두 1번이라 가정 (가짜 데이터기 때문)
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        // 주문 아이템 리스트에 담기
        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .price(10000)
                .build();
        orderItemDTOList.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(1)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .price(9900)
                .build();
        orderItemDTOList.add(orderItemDTO2);

        List<OrderProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(
                OrderProductDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .orderItems(orderItemDTOList)
                        .build()
        );
        OrderResponseFindByIdDTO responseDTO = new OrderResponseFindByIdDTO(id, productDTOList, 19900);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
