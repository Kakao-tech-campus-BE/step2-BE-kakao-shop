package com.example.kakaoshop.product;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop._core.utils.DummyStore;
import com.example.kakaoshop.product.response.ProductOptionDTO;
import com.example.kakaoshop.product.response.ProductRespFindAllDTO;
import com.example.kakaoshop.product.response.ProductRespFindByIdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final DummyStore dummyStore;

    @GetMapping("/products")
    public ResponseEntity<?> findAll() {
        List<ProductRespFindAllDTO> responseDTO = dummyStore.getRespFindAllDTOList();

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 상품을 담을 DTO 생성
        ProductRespFindByIdDTO responseDTO = null;

        if(id == 1) {
            List<ProductOptionDTO> optionDTOList = dummyStore.getOptionDTOList();
            List<ProductOptionDTO> optionList=new ArrayList<>();
            for (int i=0;i<5;i++) {
                optionList.add(optionDTOList.get(i));
            }
            responseDTO = ProductRespFindByIdDTO.builder()
                    .id(1)
                    .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                    .description("")
                    .image("/images/1.jpg")
                    .price(1000)
                    .starCount(5)
                    .options(optionList)
                    .build();

        }else if(id == 2){
            List<ProductOptionDTO> optionDTOList = dummyStore.getOptionDTOList();
            List<ProductOptionDTO> optionList=new ArrayList<>();
            for (int i=5;i<optionDTOList.size();i++) {
                optionList.add(optionDTOList.get(i));
            }

            responseDTO = ProductRespFindByIdDTO.builder()
                    .id(1)
                    .productName("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율")
                    .description("")
                    .image("/images/2.jpg")
                    .price(2000)
                    .starCount(5)
                    .options(optionList)
                    .build();

        }else { //id가 1,2가 아닐 경우
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 상품을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}
