package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderDTO;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.ProductDTO;
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
    public ResponseEntity<?> orderSave() {

        // 오더 아이템 리스트 만들기
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        //오더 아이템 리스트에 담기
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
        
        // 상품 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();
        
        // 상품 리스트에 담기
        ProductDTO productDTO1 = ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션의 주방용품 특가전")
                .items(orderItemDTOList)
                .build();

        productDTOList.add(productDTO1);
        
        // 주문 만들기
        OrderDTO orderDTO1 = OrderDTO.builder()
                .id(2)
                .products(productDTOList)
                .totalPrice(20900)
                .build();
        return ResponseEntity.ok(ApiUtils.success(orderDTO1));
    }
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 오더 아이템 리스트 만들기
        List<OrderItemDTO> orderItemDTOList2 = new ArrayList<>();

        //오더 아이템 리스트에 담기
        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        orderItemDTOList2.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        orderItemDTOList2.add(orderItemDTO2);

        // 상품 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();

        // 상품 리스트에 담기
        ProductDTO productDTO1 = ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션의 주방용품 특가전")
                .items(orderItemDTOList2)
                .build();

        productDTOList.add(productDTO1);

        // 주문 만들기
        OrderDTO orderDTO1 = OrderDTO.builder()
                .id(2)
                .products(productDTOList)
                .totalPrice(20900)
                .build();
        return ResponseEntity.ok(ApiUtils.success(orderDTO1));
    }
}
