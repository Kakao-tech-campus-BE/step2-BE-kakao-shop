package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderRespDTO;
import com.example.kakaoshop.order.response.ProductDTO;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.product.response.ProductOptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {

    @PostMapping("/orders/save")
    public ResponseEntity<?> saveOrder(){
        //현재 장바구니에 담겨있는제품들의 결제로직..
        return ResponseEntity.ok("결제하기 완료");
    }
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        // 주문을 담을 DTO 생성
        List<ProductDTO> orderDTOList =  new ArrayList<>();
        if(id == 1){
            List<OrderItemDTO> orderItemDTOList1 = new ArrayList<>();
            OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                    .quantity(10)
                    .price(100000)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .build();
            orderItemDTOList1.add(orderItemDTO1);

            OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                    .quantity(10)
                    .price(10900)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .build();
            orderItemDTOList1.add(orderItemDTO2);

            orderDTOList.add(
                    ProductDTO.builder()
                            .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                            .orderItems(orderItemDTOList1)
                            .build()
            );

        }
        else{
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }
        OrderRespDTO responseDTO = new OrderRespDTO(1,orderDTOList,209000);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));

    }
}
