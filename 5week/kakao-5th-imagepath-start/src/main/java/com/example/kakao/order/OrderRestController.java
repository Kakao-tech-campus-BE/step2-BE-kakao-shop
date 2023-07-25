package com.example.kakao.order;

import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {


    // (기능9) 결재하기 - (주문 인서트) POST
    // /orders/save
    public void save() {

    }

    // (기능10) 주문 결과 확인 GET
    // /orders/{id}
    public void findById() {

    }

}
