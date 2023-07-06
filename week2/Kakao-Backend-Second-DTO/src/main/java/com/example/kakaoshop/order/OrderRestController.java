package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderDTO;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderRestController {
    @PostMapping("/save")
    public ResponseEntity<?> saveOrder() {
        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();


        List<OrderItemDTO> orderItemDTOList1 = new ArrayList<>();
        orderItemDTOList1.add(OrderItemDTO.builder()
                .id(4)
                .optionName("2겹 식빵수세미 6매")
                .quantity(3)
                .price(26700)
                .build());
        orderItemDTOList1.add(OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build());

        orderProductDTOList.add(OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(orderItemDTOList1)
                .build());


        List<OrderItemDTO> orderItemDTOList2 = new ArrayList<>();
        orderItemDTOList2.add(OrderItemDTO.builder()
                .id(5)
                .optionName("JR310BT (무선 전용) - 레드")
                .quantity(4)
                .price(199600)
                .build());
        orderItemDTOList2.add(OrderItemDTO.builder()
                .id(6)
                .optionName("JR310BT (무선 전용) - 그린")
                .quantity(5)
                .price(249500)
                .build());

        orderProductDTOList.add(OrderProductDTO.builder()
                .productName("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!")
                .items(orderItemDTOList2)
                .build());


        OrderDTO responseDTO = OrderDTO.builder()
                .id(1)
                .products(orderProductDTOList)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder() {
        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();


        List<OrderItemDTO> orderItemDTOList1 = new ArrayList<>();
        orderItemDTOList1.add(OrderItemDTO.builder()
                .id(4)
                .optionName("2겹 식빵수세미 6매")
                .quantity(3)
                .price(26700)
                .build());
        orderItemDTOList1.add(OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build());

        orderProductDTOList.add(OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(orderItemDTOList1)
                .build());


        List<OrderItemDTO> orderItemDTOList2 = new ArrayList<>();
        orderItemDTOList2.add(OrderItemDTO.builder()
                .id(5)
                .optionName("JR310BT (무선 전용) - 레드")
                .quantity(4)
                .price(199600)
                .build());
        orderItemDTOList2.add(OrderItemDTO.builder()
                .id(6)
                .optionName("JR310BT (무선 전용) - 그린")
                .quantity(5)
                .price(249500)
                .build());

        orderProductDTOList.add(OrderProductDTO.builder()
                .productName("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!")
                .items(orderItemDTOList2)
                .build());


        OrderDTO responseDTO = OrderDTO.builder()
                .id(1)
                .products(orderProductDTOList)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
