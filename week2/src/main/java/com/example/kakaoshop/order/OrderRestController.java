package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderRespSaveDTO;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderRespFindByIdDTO;
import com.example.kakaoshop.order.response.OrderProductDTO;
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

    // 주문하기 (주문 저장)
    // OrderRespSaveDTO <- OrderProductDTO <- OrderItemDTO
    @PostMapping("/orders/save")
    public ResponseEntity<?> save() {

        // 주문 아이템 리스트 만들기
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        // 리스트에 주문한 아이템 담기
        orderItemDTOList.add(OrderItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build());

        orderItemDTOList.add(OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build());

        // 주문 상품 리스트 만들기
        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();

        // 리스트에 주문한 상품(orderItemDTOList를 포함한) 담기
        orderProductDTOList.add(
                OrderProductDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전")
                        .items(orderItemDTOList)
                        .build()
        );

        // 결제 후 저장된 주문 응답 데이터 생성 (orderProductDTOList 포함)
        OrderRespSaveDTO responseDTO = new OrderRespSaveDTO(2, orderProductDTOList, 20900);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));

    }

    // 특정 주문 결과 조회
    // 현재는 주문 번호가 1인 주문만 조회 가능
    // OrderRespFindByIdDTO <- OrderProductDTO <- OrderItemDTO
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {

        // 주문 정보를 담을 응답 데이터 생성
        OrderRespFindByIdDTO responseDTO = null;

        if (id==1) {
            // 주문 아이템 리스트 만들기
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

            // 리스트에 주문된 아이템 담기
            orderItemDTOList.add(OrderItemDTO.builder()
                                    .id(4)
                                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                                    .quantity(10)
                                    .price(100000)
                                    .build());

            orderItemDTOList.add(OrderItemDTO.builder()
                                    .id(5)
                                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                                    .quantity(10)
                                    .price(109000)
                                    .build());

            // 주문 상품 리스트 만들기
            List<OrderProductDTO> orderProductDTOList = new ArrayList<>();

            // 리스트에 주문된 상품(orderItemDTOList를 포함한) 담기
            orderProductDTOList.add(
                    OrderProductDTO.builder()
                            .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전")
                            .items(orderItemDTOList)
                            .build()
            );

            // 주문 조회했을 때 응답 데이터 생성 (orderProductDTOList 포함)
            responseDTO = new OrderRespFindByIdDTO(id, orderProductDTOList, 209000);

            return ResponseEntity.ok(ApiUtils.success(responseDTO));

        } else {
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

    }
}
