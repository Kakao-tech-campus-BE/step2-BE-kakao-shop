package com.example.kakao.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CartRestController {

    /**
     * [
     *     {
     *         "optionId":1,
     *         "quantity":5
     *     },
     *     {
     *         "optionId":2,
     *         "quantity":5
     *     }
     * ]
     */
    // (기능6) 장바구니 담기 POST
    // /carts/add
    public void addCartList() {

    }

    // (기능7) 장바구니 조회 - (주문화면) GET
    // /carts
    public void findAll() {

    }


    /**
     *  [
     *      {
     *          "cartId":1,
     *          "quantity":10
     *      },
     *      {
     *          "cartId":2,
     *          "quantity":10
     *      }
     *  ]
     */
    // (기능8) 주문하기 - (주문화면에서 장바구니 수정하기)
    // /carts/update
    public void update() {
    }
}
