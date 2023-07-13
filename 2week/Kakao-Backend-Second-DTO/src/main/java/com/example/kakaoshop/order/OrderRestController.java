package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderRespFindByIdDTO;
import com.example.kakaoshop.order.response.ProductDTO;
import org.aspectj.weaver.ast.Or;
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
    public ResponseEntity<?> findById(@PathVariable int id){
        OrderRespFindByIdDTO responseDTO = null;
        // 현재 API에는 주문번호 1번만 존재
        if(id == 1){
            // 먼저 카트 번호에 해당하는 옵션의 이름과 수량, 가격을 설정
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
            orderItemDTOList.add(
                    OrderItemDTO.builder()
                            .cart_id(4)
                            .option_name("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                            .quantity(10)
                            .price(100000)
                            .build()
            );
            orderItemDTOList.add(
                    OrderItemDTO.builder()
                            .cart_id(5)
                            .option_name("02. 슬라이딩 지퍼백 플라워에디션 4종")
                            .quantity(10)
                            .price(109000)
                            .build()
            );

            // 설정한 카트에 담긴 옵션들을 하나의 product로 묶는다.
            List<ProductDTO> productDTOList = new ArrayList<>();
            productDTOList.add(
                    ProductDTO.builder()
                            .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전 외 주방용품 특가전")
                            .orderItems(orderItemDTOList)
                            .build()
            );
            responseDTO = OrderRespFindByIdDTO.builder()
                    .order_id(1)
                    .products(productDTOList)
                    .totalPrice(209000)
                    .build();
        }
        else{
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
