package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.item.OrderItemDTO;
import com.example.kakaoshop.order.item.OrderRespFindAllDTO;
import com.example.kakaoshop.order.item.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findByID(@PathVariable int id){
        if(id == 2){
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

            OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                    .id(4)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(5)
                    .price(50000)
                    .build();

            orderItemDTOList.add(orderItemDTO1);

            OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                    .id(5)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(109000)
                    .build();

            orderItemDTOList.add(orderItemDTO2);

            List<ProductDTO> productDTOList = new ArrayList<>();

            productDTOList.add(
                    ProductDTO.builder()
                            .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                            .orderItems(orderItemDTOList)
                            .build()
            );
            OrderRespFindAllDTO responseDTO = new OrderRespFindAllDTO(2, productDTOList, 209000);

            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }

        else{
            return ResponseEntity.badRequest().body(ApiUtils.error("주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }
    }
}
