package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderDTO;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderRestController {
    @PostMapping("/save")
    public ResponseEntity<?> save() {
        OrderItemDTO item1 = OrderItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        OrderItemDTO item2 = OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();

        List<OrderItemDTO> orderItemList = new ArrayList<>();
        orderItemList.add(item1);
        orderItemList.add(item2);

        OrderProductDTO orderProduct = OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전")
                .items(orderItemList)
                .build();

        List<OrderProductDTO> products = new ArrayList<>();
        products.add(orderProduct);

        OrderDTO order = OrderDTO.builder()
                .id(2)
                .products(products)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok().body(ApiUtils.success(order));
    }

    @GetMapping("/{idx}")
    public ResponseEntity<?> findById(@PathVariable("idx") int idx) {
        OrderItemDTO item1 = OrderItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스 에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        OrderItemDTO item2 = OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 4종")
                .quantity(10)
                .price(109000)
                .build();

        List<OrderItemDTO> orderItemList = new ArrayList<>();
        orderItemList.add(item1);
        orderItemList.add(item2);

        OrderProductDTO orderProduct = OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(orderItemList)
                .build();

        List<OrderProductDTO> products = new ArrayList<>();
        products.add(orderProduct);

        OrderDTO order = OrderDTO.builder()
                .id(2)
                .products(products)
                .totalPrice(209000)
                .build();

        List<OrderDTO> orders = new ArrayList<>();
        orders.add(order);
        OrderDTO order1 = OrderDTO.builder()
                .id(2)
                .products(products)
                .totalPrice(209000)
                .build();
        orders.add(order);

        return ResponseEntity.ok().body(ApiUtils.success(orders.get(idx-1)));
    }
}
