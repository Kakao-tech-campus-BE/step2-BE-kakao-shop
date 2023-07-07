package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderResFindByIdDTO;
import com.example.kakaoshop.order.response.OrderResSaveDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        List<OrderResFindByIdDTO.ProductDTO> productDTOs = new ArrayList<>();

        List<OrderResFindByIdDTO.OrderItemDTO> orderItemDTOs = new ArrayList<>();
        orderItemDTOs.add(OrderResFindByIdDTO.OrderItemDTO.builder()
                .id(4L)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션")
                .quantity(10)
                .price(100000)
                .build());
        orderItemDTOs.add(OrderResFindByIdDTO.OrderItemDTO.builder()
                .id(5L)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build());

        productDTOs.add(OrderResFindByIdDTO.ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션")
                .items(orderItemDTOs)
                .build());

        OrderResFindByIdDTO responseDTO = OrderResFindByIdDTO.builder()
                .id(id)
                .products(productDTOs)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PostMapping("/orders/save")
    ResponseEntity<?> save() {
        // TODO: cart_tb의 내용을 order_tb에 저장

        // 성공 했다고 가정

        List<OrderResSaveDTO.ProductDTO> productDTOs = new ArrayList<>();

        List<OrderResSaveDTO.OrderItemDTO> orderItemDTOs = new ArrayList<>();
        orderItemDTOs.add(OrderResSaveDTO.OrderItemDTO.builder()
                .id(4L)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션")
                .quantity(10)
                .price(100000)
                .build());
        orderItemDTOs.add(OrderResSaveDTO.OrderItemDTO.builder()
                .id(5L)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build());

        productDTOs.add(OrderResSaveDTO.ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션")
                .items(orderItemDTOs)
                .build());

        OrderResSaveDTO responseDTO = OrderResSaveDTO.builder()
                .id(2L)
                .products(productDTOs)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}
