package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.CartRestController;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private CartRestController optionJPARepository;


    @BeforeEach
    public void setUp() {
        User user = newUser("ssar");
        userJPARepository.save(user);

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionDummyList(productListPS);

        System.out.println("setUp");
    }

    @Test // 주문 조회 -> user id
    public void order_List() {
        // given
        int id = 1;

        // when
        List<Order> orderList = orderJPARepository.findById(id);

        // then
        assertThat(orderList.get(0).getUser().getUsername()).isEqualTo("ssar");
    }

    // 주문 내역 상세 조회 test 추가
}