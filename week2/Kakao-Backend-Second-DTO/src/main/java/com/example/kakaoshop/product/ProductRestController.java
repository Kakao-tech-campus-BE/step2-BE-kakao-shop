package com.example.kakaoshop.product;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.product.response.ProductOptionDTO;
import com.example.kakaoshop.product.response.ProductRespFindAllDTO;
import com.example.kakaoshop.product.response.ProductRespFindByIdDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductRestController {

    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(value="page", required = false, defaultValue = "0")int id) {
        List<ProductRespFindAllDTO> responseDTO = new ArrayList<>();

        if(id == 0) {
            responseDTO.add(new ProductRespFindAllDTO(
                    1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "", "/images/3.jpg", 30000
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    4, "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종", "", "/images/4.jpg", 4000
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    5, "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주", "", "/images/5.jpg", 5000
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    6, "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전", "", "/images/6.jpg", 15900
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    7, "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제", "", "/images/7.jpg", 26800
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    8, "제나벨 PDRN 크림 2개. 피부보습/진정 케어", "", "/images/8.jpg", 25900
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    9, "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감", "", "/images/9.jpg", 797000
            ));
        } else if(id == 1) {
            responseDTO.add(new ProductRespFindAllDTO(
                    10, "통영 홍 가리비 2kg, 2세트 구매시 1kg 추가증정", "", "/images/10.jpg", 8900
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    11, "아삭한 궁채 장아찌 1kg 외 인기 반찬 모음전", "", "/images/11.jpg", 6900
                    ));
            responseDTO.add(new ProductRespFindAllDTO(
                    12, "깨끗한나라 순수소프트 30롤 2팩. 무형광, 도톰 3겹", "", "/images/12.jpg", 28900
                    ));
            responseDTO.add(new ProductRespFindAllDTO(
                    13, "생활공작소 초미세모 칫솔 12입 2개+가글 증정", "", "/images/13.jpg", 9900
                    ));
            responseDTO.add(new ProductRespFindAllDTO(
                    14, "경북 영천 샤인머스켓 가정용 1kg 2수 내외", "", "/images/14.jpg", 9900
                    ));
            responseDTO.add(new ProductRespFindAllDTO(
                    15, "[LIVE][5%쿠폰] 홈카페 Y3.3 캡슐머신 베이직 세트", "", "/images/15.jpg", 148000
                    ));
        }

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

@GetMapping("/products/{id}")
public ResponseEntity<?> findById(@PathVariable int id) {
    // 상품을 담을 DTO 생성
    ProductRespFindByIdDTO responseDTO = null;

    if(id == 1){
        List<ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(new ProductOptionDTO(1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000));
        optionDTOList.add(new ProductOptionDTO(2, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900));
        optionDTOList.add(new ProductOptionDTO(3, "고무장갑 베이지 S(소형) 6팩", 9900));
        optionDTOList.add(new ProductOptionDTO(4, "뽑아쓰는 키친타올 130매 12팩", 16900));
        optionDTOList.add(new ProductOptionDTO(4, "2겹 식빵수세미 6매", 8900));
        responseDTO = new ProductRespFindByIdDTO(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000, 5, optionDTOList);

    }else if(id == 2){
        List<ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(new ProductOptionDTO(6, "22년산 햇단밤 700g(한정판매)", 9900));
        optionDTOList.add(new ProductOptionDTO(7, "22년산 햇단밤 1kg(한정판매)", 14500));
        optionDTOList.add(new ProductOptionDTO(8, "밤깎기+다회용 구이판 세트", 5500));
        responseDTO = new ProductRespFindByIdDTO(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000, 5, optionDTOList);
    }else {
        return ResponseEntity.badRequest().body(ApiUtils.error("해당 상품을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
    }

    return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
