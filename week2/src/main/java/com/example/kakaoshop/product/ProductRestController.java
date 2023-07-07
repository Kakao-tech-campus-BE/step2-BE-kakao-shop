package com.example.kakaoshop.product;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.product.response.ProductOptionDTO;
import com.example.kakaoshop.product.response.ProductRespFindAllDTO;
import com.example.kakaoshop.product.response.ProductRespFindByIdDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductRestController {

    @GetMapping("/products")
    public ResponseEntity<?> findAll() {
        List<ProductRespFindAllDTO> responseDTO = new ArrayList<>();

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto1 = ProductRespFindAllDTO.builder()
                .id(1)
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .description("")
                .image("/images/1.jpg")
                .price(1000)
                .build();
        //담기
        responseDTO.add(dto1);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto2 = ProductRespFindAllDTO.builder()
                .id(2)
                .productName("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율")
                .description("")
                .image("/images/2.jpg")
                .price(2000)
                .build();
        //담기
        responseDTO.add(dto2);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto3 = ProductRespFindAllDTO.builder()
                .id(3)
                .productName("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!")
                .description("")
                .image("/images/3.jpg")
                .price(30000)
                .build();
        //담기
        responseDTO.add(dto2);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto4 = ProductRespFindAllDTO.builder()
                .id(4)
                .productName("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종")
                .description("")
                .image("/images/4.jpg")
                .price(4000)
                .build();
        //담기
        responseDTO.add(dto4);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto5 = ProductRespFindAllDTO.builder()
                .id(5)
                .productName("[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주")
                .description("")
                .image("/images/5.jpg")
                .price(5000)
                .build();
        //담기
        responseDTO.add(dto5);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto6 = ProductRespFindAllDTO.builder()
                .id(6)
                .productName("굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전")
                .description("")
                .image("/images/6.jpg")
                .price(15900)
                .build();
        //담기
        responseDTO.add(dto6);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto7 = ProductRespFindAllDTO.builder()
                .id(7)
                .productName("eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제")
                .description("")
                .image("/images/7.jpg")
                .price(26800)
                .build();
        //담기
        responseDTO.add(dto7);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto8 = ProductRespFindAllDTO.builder()
                .id(8)
                .productName("제나벨 PDRN 크림 2개. 피부보습/진정 케어")
                .description("")
                .image("/images/8.jpg")
                .price(25900)
                .build();
        //담기
        responseDTO.add(dto8);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto9 = ProductRespFindAllDTO.builder()
                .id(9)
                .productName("플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감")
                .description("")
                .image("/images/9.jpg")
                .price(797000)
                .build();
        //담기
        responseDTO.add(dto9);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 상품을 담을 DTO 생성
        ProductRespFindByIdDTO responseDTO = null;

        if(id == 1) {
            List<ProductOptionDTO> optionDTOList = new ArrayList<>();
            optionDTOList.add(
                    ProductOptionDTO.builder()
                    .id(1)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .price(10000)
                    .build());

            optionDTOList.add(ProductOptionDTO.builder()
                    .id(2)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .price(10900)
                    .build());

            optionDTOList.add(ProductOptionDTO.builder()
                    .id(3)
                    .optionName("고무장갑 베이지 S(소형) 6팩")
                    .price(9900)
                    .build());

            optionDTOList.add(ProductOptionDTO.builder()
                    .id(4)
                    .optionName("뽑아쓰는 키친타올 130매 12팩")
                    .price(16900)
                    .build());

            optionDTOList.add(ProductOptionDTO.builder()
                    .id(5)
                    .optionName("2겹 식빵수세미 6매")
                    .price(8900)
                    .build());

            responseDTO = ProductRespFindByIdDTO.builder()
                    .id(1)
                    .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                    .description("")
                    .image("/images/1.jpg")
                    .price(1000)
                    .starCount(5)
                    .options(optionDTOList)
                    .build();

        }else if(id == 2){
            List<ProductOptionDTO> optionDTOList = new ArrayList<>();
            optionDTOList.add(ProductOptionDTO.builder()
                    .id(6)
                    .optionName("22년산 햇단밤 700g(한정판매)")
                    .price(9900)
                    .build());

            optionDTOList.add(ProductOptionDTO.builder()
                    .id(7)
                    .optionName("22년산 햇단밤 1kg(한정판매)")
                    .price(14500)
                    .build());

            optionDTOList.add(ProductOptionDTO.builder()
                    .id(8)
                    .optionName("밤깎기+다회용 구이판 세트")
                    .price(5500)
                    .build());

            responseDTO = ProductRespFindByIdDTO.builder()
                    .id(1)
                    .productName("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율")
                    .description("")
                    .image("/images/2.jpg")
                    .price(2000)
                    .starCount(5)
                    .options(optionDTOList)
                    .build();

        }else { //id가 1,2가 아닐 경우
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 상품을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}
