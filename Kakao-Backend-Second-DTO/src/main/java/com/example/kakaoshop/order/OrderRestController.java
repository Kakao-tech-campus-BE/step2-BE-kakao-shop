package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderResFindByIdDTO;
import com.example.kakaoshop.order.response.OrderSaveResDTO;
import com.example.kakaoshop.order.response.ProductOptionItemDTO;
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

    @PostMapping("/orders/save")
    public ResponseEntity<?> saveOrder() {
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        orderItemDTOList.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        orderItemDTOList.add(orderItemDTO2);

        List<ProductOptionItemDTO> productOptionItemDTOList = new ArrayList<>();
        ProductOptionItemDTO productOptionItemDTO1 = ProductOptionItemDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(orderItemDTOList)
                .build();
        productOptionItemDTOList.add(productOptionItemDTO1);

        OrderSaveResDTO responseDTO = OrderSaveResDTO.builder()
                .id(2)
                .products(productOptionItemDTOList)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById (@PathVariable int id) {

        OrderResFindByIdDTO responseDTO = null;

        if(id == 2){
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

            OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                    .id(4)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(10)
                    .price(100000)
                    .build();
            orderItemDTOList.add(orderItemDTO1);

            OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                    .id(5)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(109000)
                    .build();
            orderItemDTOList.add(orderItemDTO2);

            List<ProductOptionItemDTO> productOptionItemDTOList = new ArrayList<>();
            ProductOptionItemDTO productOptionItemDTO1 = ProductOptionItemDTO.builder()
                    .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                    .items(orderItemDTOList)
                    .build();
            productOptionItemDTOList.add(productOptionItemDTO1);

            responseDTO = OrderResFindByIdDTO.builder()
                    .id(2)
                    .products(productOptionItemDTOList)
                    .totalPrice(209000)
                    .build();

            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }
        else return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다." + id, HttpStatus.BAD_REQUEST));

    }

}
