package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderFindResponseDTO;
import com.example.kakaoshop.order.response.OrderSaveResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderRestController {
    @PostMapping("/save")
    public ResponseEntity<?> save() {
        OrderFindResponseDTO.Item item1 = OrderFindResponseDTO.Item.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        OrderFindResponseDTO.Item item2 = OrderFindResponseDTO.Item.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();

        List<OrderFindResponseDTO.Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);

        OrderFindResponseDTO.Product orderProduct = OrderFindResponseDTO.Product.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전")
                .items(itemList)
                .build();

        List<OrderFindResponseDTO.Product> products = new ArrayList<>();
        products.add(orderProduct);

        OrderFindResponseDTO.Response responseDTO = OrderFindResponseDTO.Response.builder()
                .id(2)
                .products(products)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    @GetMapping("/{idx}")
    public ResponseEntity<?> findById(@PathVariable("idx") int idx) {
        OrderSaveResponseDTO.Item item1 = OrderSaveResponseDTO.Item.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스 에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        OrderSaveResponseDTO.Item item2 = OrderSaveResponseDTO.Item.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 4종")
                .quantity(10)
                .price(109000)
                .build();

        List<OrderSaveResponseDTO.Item> orderItemList = new ArrayList<>();
        orderItemList.add(item1);
        orderItemList.add(item2);

        OrderSaveResponseDTO.Product orderProduct = OrderSaveResponseDTO.Product.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(orderItemList)
                .build();

        List<OrderSaveResponseDTO.Product> products = new ArrayList<>();
        products.add(orderProduct);

        OrderSaveResponseDTO.Response responseDTO = OrderSaveResponseDTO.Response.builder()
                .id(2)
                .products(products)
                .totalPrice(209000)
                .build();

        List<OrderSaveResponseDTO.Response> responseDTOs = new ArrayList<>();
        responseDTOs.add(responseDTO);
        OrderSaveResponseDTO.Response order1 = OrderSaveResponseDTO.Response.builder()
                .id(2)
                .products(products)
                .totalPrice(209000)
                .build();
        responseDTOs.add(responseDTO);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTOs.get(idx-1)));
    }
}
