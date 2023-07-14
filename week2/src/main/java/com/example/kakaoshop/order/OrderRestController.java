package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop._core.utils.DummyStore;
import com.example.kakaoshop.cart.CartRestController;
import com.example.kakaoshop.cart.response.CartItemDTO;
import com.example.kakaoshop.cart.response.CartRespFindAllDTO;
import com.example.kakaoshop.cart.response.ProductDTO;
import com.example.kakaoshop.cart.response.ProductOptionDTO;
import com.example.kakaoshop.order.OrderDTO.OrderRespDTO;
import com.example.kakaoshop.order.OrderDTO.ProductItemDTO;
import com.example.kakaoshop.product.response.ProductRespFindByIdDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderRestController {
    private final DummyStore dummyStore;
    //주문 결과 확인
    @GetMapping ("/{id}") // /orders/{id}
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 주문을 담을 DTO 생성
        OrderRespDTO responseDTO = null;

        if(id == 1) {
            //ItemInfo 담을 리스트 생성
            List<ProductItemDTO> productItemDTOList = dummyStore.getProductItemDTOList();

            //응답할 dto 생성
            responseDTO = OrderRespDTO.builder()
                    .id(2)
                    .products(productItemDTOList)
                    .totalPrice(209000)
                    .build();
        }
        else { //id가 1이 아닌 경우
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    //결제하기(주문 인서트)
    @PostMapping("/save") //  /orders/save
    public ResponseEntity<?> saveOrder() {

        List<ProductItemDTO> productItemDTOList = dummyStore.getProductItemDTOList();

        //응답할 dto 생성
        OrderRespDTO responseDTO = OrderRespDTO.builder()
                .id(2)
                .products(productItemDTOList)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
