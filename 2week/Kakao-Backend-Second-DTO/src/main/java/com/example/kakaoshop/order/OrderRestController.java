package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.item.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderProductDTO;
import com.example.kakaoshop.order.response.OrderRespFindByIdDTO;
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

    @PostMapping("/orders/add")
    public ResponseEntity<?> orderCart(){

        OrderRespFindByIdDTO responseDTO = null;

        List<OrderItemDTO> itemDTOList = new ArrayList<>();
        List<OrderProductDTO> productDTOList = new ArrayList<>();

        itemDTOList.add(new OrderItemDTO(4,"01. 슬라이딩 지퍼백 크리스마스에디션 4종",10,100000));
        itemDTOList.add(new OrderItemDTO(5,"02. 슬라이딩 지퍼백 플라워에디션 5종",10,109000));

        productDTOList.add(new OrderProductDTO("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", itemDTOList));

        responseDTO = new OrderRespFindByIdDTO(1, productDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){

        OrderRespFindByIdDTO responseDTO = null;

        if(id==1){
            List<OrderItemDTO> itemDTOList = new ArrayList<>();
            List<OrderProductDTO> productDTOList = new ArrayList<>();

            itemDTOList.add(new OrderItemDTO(4,"01. 슬라이딩 지퍼백 크리스마스에디션 4종",10,100000));
            itemDTOList.add(new OrderItemDTO(5,"02. 슬라이딩 지퍼백 플라워에디션 5종",10,109000));

            productDTOList.add(new OrderProductDTO("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", itemDTOList));

            responseDTO = new OrderRespFindByIdDTO(1, productDTOList, 209000);

        }else {
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문이 존재하지 않습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
