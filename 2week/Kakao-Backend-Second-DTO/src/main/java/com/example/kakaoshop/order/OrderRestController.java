package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderRespFindByIdDTO;
import com.example.kakaoshop.order.response.ProductDTO;
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

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 상품을 담을 DTO 생성
        OrderRespFindByIdDTO responseDTO = null;

        if(id == 1){
            //id가 1일 경우

            //첫번쨰 OrderItemDTO 리스트 생성
            List<OrderItemDTO> orderItemDTOList1= new ArrayList<>();
            orderItemDTOList1.add(OrderItemDTO.builder()
                    .id(1)
                    .optionName("밤깎기+다회용 구이판 세트")
                    .quantity(1)
                    .price(5500).build());
            orderItemDTOList1.add(OrderItemDTO.builder()
                    .id(2)
                    .optionName("22년산 햇단밤 1kg(한정판매)")
                    .quantity(1)
                    .price(14500).build());
            orderItemDTOList1.add(OrderItemDTO.builder()
                    .id(3)
                    .optionName("22년산 햇단밤 700g(한정판매)")
                    .quantity(1)
                    .price(9900).build());

            //두번째 OrderItemDTO 리스트 생성
            List<OrderItemDTO> orderItemDTOList2= new ArrayList<>();
            orderItemDTOList2.add(OrderItemDTO.builder()
                    .id(4)
                    .optionName("선택02_바른곡물효소누룽지맛 6박스")
                    .quantity(1)
                    .price(50000).build());
            orderItemDTOList2.add(OrderItemDTO.builder()
                    .id(5)
                    .optionName("선택03_바른곡물효소3박스+유산균효소3박스")
                    .quantity(1)
                    .price(50000).build());
            orderItemDTOList2.add(OrderItemDTO.builder()
                    .id(6)
                    .optionName("선택04_바른곡물효소3박스+19종유산균3박스")
                    .quantity(1)
                    .price(50000).build());

            //ProductDTO 리스트 생성
            List<ProductDTO> productDTOList=new ArrayList<>();
            productDTOList.add(ProductDTO.builder()
                    .id(1)
                    .productName("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율")
                    .orderItems(orderItemDTOList1).build());
            productDTOList.add(ProductDTO.builder()
                    .id(2)
                    .productName("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종")
                    .orderItems(orderItemDTOList2).build());

            //OrderRespFindByIdDTO 생성
             responseDTO= OrderRespFindByIdDTO.builder()
                     .id(1)
                     .products(productDTOList)
                     .totalPrice(179900).build();
        }else {
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }


    @PostMapping("/orders/save")
    public ResponseEntity<?> findByIdd() {
        // 상품을 담을 DTO 생성
        OrderRespFindByIdDTO responseDTO = null;

            //첫번쨰 OrderItemDTO 리스트 생성
            List<OrderItemDTO> orderItemDTOList1= new ArrayList<>();
            orderItemDTOList1.add(OrderItemDTO.builder()
                    .id(1)
                    .optionName("밤깎기+다회용 구이판 세트")
                    .quantity(1)
                    .price(5500).build());
            orderItemDTOList1.add(OrderItemDTO.builder()
                    .id(2)
                    .optionName("22년산 햇단밤 1kg(한정판매)")
                    .quantity(1)
                    .price(14500).build());
            orderItemDTOList1.add(OrderItemDTO.builder()
                    .id(3)
                    .optionName("22년산 햇단밤 700g(한정판매)")
                    .quantity(1)
                    .price(9900).build());

            //두번째 OrderItemDTO 리스트 생성
            List<OrderItemDTO> orderItemDTOList2= new ArrayList<>();
            orderItemDTOList2.add(OrderItemDTO.builder()
                    .id(4)
                    .optionName("선택02_바른곡물효소누룽지맛 6박스")
                    .quantity(1)
                    .price(50000).build());
            orderItemDTOList2.add(OrderItemDTO.builder()
                    .id(5)
                    .optionName("선택03_바른곡물효소3박스+유산균효소3박스")
                    .quantity(1)
                    .price(50000).build());
            orderItemDTOList2.add(OrderItemDTO.builder()
                    .id(6)
                    .optionName("선택04_바른곡물효소3박스+19종유산균3박스")
                    .quantity(1)
                    .price(50000).build());

            //ProductDTO 리스트 생성
            List<ProductDTO> productDTOList=new ArrayList<>();
            productDTOList.add(ProductDTO.builder()
                    .id(1)
                    .productName("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율")
                    .orderItems(orderItemDTOList1).build());
            productDTOList.add(ProductDTO.builder()
                    .id(2)
                    .productName("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종")
                    .orderItems(orderItemDTOList2).build());

            //OrderRespFindByIdDTO 생성
            responseDTO= OrderRespFindByIdDTO.builder()
                    .id(1)
                    .products(productDTOList)
                    .totalPrice(179900).build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
