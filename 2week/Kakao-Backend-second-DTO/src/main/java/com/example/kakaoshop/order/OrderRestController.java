package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.CartOptionDTO;
import com.example.kakaoshop.order.response.OrderDTO;
import com.example.kakaoshop.order.response.OrderRespFindAllDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {

        List<CartOptionDTO> CartOptionDTOList = new ArrayList<>();

        CartOptionDTO option1 = CartOptionDTO.builder()
                .optionId(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        CartOptionDTO option2 = CartOptionDTO.builder()
                .optionId(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();

        CartOptionDTOList.add(option1);
        CartOptionDTOList.add(option2);

        OrderDTO product1 = OrderDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(CartOptionDTOList)
                .build();

        OrderRespFindAllDTO orderRespFindAllDTO = new OrderRespFindAllDTO(1, product1, 209000);

        return ResponseEntity.ok(ApiUtils.success(orderRespFindAllDTO));
    }

}