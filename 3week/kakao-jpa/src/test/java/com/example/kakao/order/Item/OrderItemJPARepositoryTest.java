package com.example.kakao.order.Item;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.OrderJPARepository;
import com.example.kakao.order.OrderJPARepositoryTest;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class OrderItemJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em; //테스트 쿼리 작성하기 위해서 추가하였음


    //어벤저스 어셈블,,, 실제로는
    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<User> userListPS = userJPARepository.saveAll(userDummyList());


        cartJPARepository.saveAll(cartDummyList(userListPS, optionListPS));


        em.clear();

        System.out.println("----------추가 완료---------");
    }





}
