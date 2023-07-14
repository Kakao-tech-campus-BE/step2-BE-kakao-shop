package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.ItemOptionDTO;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderRespFindByIdDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {
    @PostMapping("orders/save")
    public ResponseEntity<?> saveOrder(){
        OrderRespFindByIdDTO responseDTO;
        //주문내역의 상품들 리스트 - 실제 환경에서는 카트에 있던 상품들을 그대로 가져옴
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        //주문내역의 첫번째 상품의 옵션 리스트
        List<ItemOptionDTO> itemOptionDTOList1 = new ArrayList<>();

        itemOptionDTOList1.add(
                ItemOptionDTO.builder()
                        .id(4)
                        .optionName("2겹 식빵수세미 6매")
                        .quantity(3)
                        .price(26700)
                        .build()
        );

        orderItemDTOList.add(
                OrderItemDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .items(itemOptionDTOList1)
                        .build()
        );

        //주문내역의 두번째 상품의 옵션리스트
        List<ItemOptionDTO> itemOptionDTOList2 = new ArrayList<>();

        itemOptionDTOList2.add(
                ItemOptionDTO.builder()
                        .id(5)
                        .optionName("JR310BT (무선 전용) - 레드")
                        .quantity(4)
                        .price(199600)
                        .build()
        );

        itemOptionDTOList2.add(
                ItemOptionDTO.builder()
                        .id(6)
                        .optionName("JR310BT (무선 전용) - 그린")
                        .quantity(5)
                        .price(249500)
                        .build()
        );

        orderItemDTOList.add(
                OrderItemDTO.builder()
                        .productName("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!")
                        .items(itemOptionDTOList2)
                        .build()
        );
        //1번 주문id의 2개의 상품이 포함된 DTOList와 총 가격을 리턴
        responseDTO = new OrderRespFindByIdDTO(1, orderItemDTOList, 475800);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        OrderRespFindByIdDTO responseDTO = null;
        if(id == 1){
            //주문내역의 상품들 리스트
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

            //주문내역의 첫번째 상품의 옵션 리스트
            List<ItemOptionDTO> itemOptionDTOList1 = new ArrayList<>();

            itemOptionDTOList1.add(
                    ItemOptionDTO.builder()
                            .id(1)
                            .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                            .quantity(5)
                            .price(50000)
                            .build()
            );

            itemOptionDTOList1.add(
                    ItemOptionDTO.builder()
                            .id(2)
                            .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                            .quantity(5)
                            .price(54500)
                            .build()
            );

            orderItemDTOList.add(
                    OrderItemDTO.builder()
                            .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                            .items(itemOptionDTOList1)
                            .build()
            );

            //주문내역의 두번째 상품의 옵션리스트
            List<ItemOptionDTO> itemOptionDTOList2 = new ArrayList<>();

            itemOptionDTOList2.add(
                    ItemOptionDTO.builder()
                            .id(3)
                            .optionName("JR310BT (무선 전용) - 레드")
                            .quantity(5)
                            .price(249500)
                            .build()
            );

            orderItemDTOList.add(
                    OrderItemDTO.builder()
                            .productName("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!")
                            .items(itemOptionDTOList2)
                            .build()
            );
            //1번 주문id의 2개의 상품이 포함된 DTOList와 총 가격을 리턴
            responseDTO = new OrderRespFindByIdDTO(1, orderItemDTOList, 354000);
        }
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
