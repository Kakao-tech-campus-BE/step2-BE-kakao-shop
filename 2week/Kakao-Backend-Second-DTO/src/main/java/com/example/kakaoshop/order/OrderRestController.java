package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderRespFindAllDTO;
import com.example.kakaoshop.order.response.ProductDTO;
import com.example.kakaoshop.order.response.ProductOptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderRestController {
    @PostMapping("orders/orderSheets")
    public ResponseEntity<?> placeOrderSheets() {
        // 카트 아이템 리스트 만들기

        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(4)
                .quantity(5)
                .price(50000)
                .build();
        orderItemDTO1.setOption(ProductOptionDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .price(10000)
                .build());
        orderItemDTOS.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(5)
                .quantity(5)
                .price(54500)
                .build();
        orderItemDTO2.setOption(ProductOptionDTO.builder()
                .id(1)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .price(10900)
                .build());
        orderItemDTOS.add(orderItemDTO2);

        // productDTO 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productDTOList.add(
                ProductDTO.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .orderItems(orderItemDTOS)
                        .build()
        );

        OrderRespFindAllDTO responseDTO = new OrderRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        if (id == 1) {
            // 카트 아이템 리스트 만들기

            List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

            OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                    .id(4)
                    .quantity(5)
                    .price(50000)
                    .build();
            orderItemDTO1.setOption(ProductOptionDTO.builder()
                    .id(1)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .price(10000)
                    .build());
            orderItemDTOS.add(orderItemDTO1);

            OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                    .id(5)
                    .quantity(5)
                    .price(54500)
                    .build();
            orderItemDTO2.setOption(ProductOptionDTO.builder()
                    .id(1)
                    .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                    .price(10900)
                    .build());
            orderItemDTOS.add(orderItemDTO2);

            // productDTO 리스트 만들기
            List<ProductDTO> productDTOList = new ArrayList<>();

            // productDTO 리스트에 담기
            productDTOList.add(
                    ProductDTO.builder()
                            .id(1)
                            .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                            .orderItems(orderItemDTOS)
                            .build()
            );

            OrderRespFindAllDTO responseDTO = new OrderRespFindAllDTO(productDTOList, 104500);

            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }
        return ResponseEntity.badRequest().body(ApiUtils.error("해당 상품을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
    }
}
