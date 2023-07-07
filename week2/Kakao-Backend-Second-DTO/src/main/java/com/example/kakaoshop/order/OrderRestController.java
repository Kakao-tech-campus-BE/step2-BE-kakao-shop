package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItemAddDTO;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderRespAddDTO;
import com.example.kakaoshop.order.response.OrderRespFindByIdDTO;
import com.example.kakaoshop.order.response.ProductDTO;
import com.example.kakaoshop.order.response.ProductAddDTO;
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
    public ResponseEntity<?> add() {
        // 여러 주문아이템을 담을 DTO 생성
        List<OrderItemAddDTO> orderItemAddDTOList = new ArrayList<>();
        // 주문아이템 1개씩 넣기
        OrderItemAddDTO orderItemAddDTO1 = OrderItemAddDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        orderItemAddDTOList.add(orderItemAddDTO1);
        // 주문아이템 1개씩 넣기
        OrderItemAddDTO orderItemAddDTO2 = OrderItemAddDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        orderItemAddDTOList.add(orderItemAddDTO2);
        // 여러 상품을 담을 DTO 생성
        List<ProductAddDTO> productAddDTOList = new ArrayList<>();
        // 상품 1개씩 넣기
        productAddDTOList.add(new ProductAddDTO("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", orderItemAddDTOList));
        // 주문 1개를 담을 DTO 생성
        OrderRespAddDTO responseDTO = new OrderRespAddDTO(1, productAddDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 1개 주문을 담을 DTO 생성
        OrderRespFindByIdDTO responseDTO = null;

        if(id == 1){
            // 여러 주문아이템을 담을 DTO 생성
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
            // 주문아이템 1개씩 넣기
            OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                    .id(4)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(10)
                    .price(100000)
                    .build();
            orderItemDTOList.add(orderItemDTO1);
            // 주문아이템 1개씩 넣기
            OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                    .id(5)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(109000)
                    .build();
            orderItemDTOList.add(orderItemDTO2);
            // 여러 상품을 담을 DTO 생성
            List<ProductDTO> productDTOList = new ArrayList<>();
            // 상품 1개씩 넣기
            productDTOList.add(new ProductDTO("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", orderItemDTOList));
            // 주문id에 따른 응답
            responseDTO = new OrderRespFindByIdDTO(1, productDTOList,209000);
        }else {
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
