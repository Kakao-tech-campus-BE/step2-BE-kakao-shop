package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.item.OrderItem;
import com.example.kakaoshop.order.response.ProductOptionDTO;
import com.example.kakaoshop.order.response.Products;
import com.example.kakaoshop.order.response.SaveProductsDTO;
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

    @PostMapping("/orders/save")
    public ResponseEntity<?> save(){
        SaveProductsDTO responseDTO = null;
        List<ProductOptionDTO> productOptionDTOList = new ArrayList<>();
        productOptionDTOList.add(new ProductOptionDTO(4,"01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10, 100000));
        productOptionDTOList.add(new ProductOptionDTO(5,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10, 109000));
        List<Products> products = new ArrayList<>();
        products.add(new Products("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",productOptionDTOList));
        responseDTO = new SaveProductsDTO(2,products,209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        OrderItem responseDTO = null;
        if(id==1) {
            List<ProductOptionDTO> optionDTOList1 = new ArrayList<>();
            optionDTOList1.add(new ProductOptionDTO(1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 3, 30000));
            optionDTOList1.add(new ProductOptionDTO(2, "02. 슬라이딩 지퍼백 플라워에디션 5종", 3, 32700));

            List<ProductOptionDTO> optionDTOList1_2 = new ArrayList<>();
            optionDTOList1_2.add(new ProductOptionDTO(3, "22년산 햇단밤 1kg(한정판매)", 2, 29000));
            optionDTOList1_2.add(new ProductOptionDTO(4, "밤깎기+다회용 구이판 세트", 2, 11000));

            List<Products> productDTOList = new ArrayList<>();
            productDTOList.add(new Products("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", optionDTOList1));
            productDTOList.add(new Products("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", optionDTOList1_2));

            responseDTO = new OrderItem(1, productDTOList, 102700);

        } else if(id == 2){
            List<ProductOptionDTO> optionDTOList2 = new ArrayList<>();
            optionDTOList2.add(new ProductOptionDTO(4, "01. 슬라이딩 지퍼백 크리스마스에디션 4종",10,100000));
            optionDTOList2.add(new ProductOptionDTO(5, "02. 슬라이딩 지퍼백 플라워에디션 5종",10,109000));

            List<Products> productDTOList = new ArrayList<>();
            productDTOList.add(new Products("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전",optionDTOList2));

            responseDTO = new OrderItem(2,productDTOList,209000);
        } else if(id==3){
            List<ProductOptionDTO> optionDTOList3 = new ArrayList<>();
            optionDTOList3.add(new ProductOptionDTO(8, "T510BT (무선 전용) - 화이트", 1, 52900));
            optionDTOList3.add(new ProductOptionDTO(9, "T510BT (무선 전용) - 블랙", 1, 52900));
            optionDTOList3.add(new ProductOptionDTO(10, "JR310BT (무선 전용) - 레드", 1, 49900));

            List<ProductOptionDTO> optionDTOList3_2 = new ArrayList<>();
            optionDTOList3_2.add(new ProductOptionDTO(11, "밤깎기+다회용 구이판 세트", 1, 5500));
            optionDTOList3_2.add(new ProductOptionDTO(12, "22년산 햇단밤 1kg(한정판매)", 1, 14500));

            List<ProductOptionDTO> optionDTOList3_3 = new ArrayList<>();
            optionDTOList3_3.add(new ProductOptionDTO(13, "플레이스테이션 VR2", 2, 1594000));

            List<Products> productDTOList = new ArrayList<>();
            productDTOList.add(new Products("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", optionDTOList3));
            productDTOList.add(new Products("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", optionDTOList3_2));
            productDTOList.add(new Products("플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감", optionDTOList3_3));

            responseDTO = new OrderItem(1, productDTOList, 1769700);

        } else {
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 상품을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}