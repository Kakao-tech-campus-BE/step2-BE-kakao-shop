package com.example.kakao.order;

import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest {
    private final EntityManager em;

    private final ObjectMapper om;

    private final UserJPARepository userJPARepository;

    private final OptionJPARepository optionJPARepository;

    private final ProductJPARepository productJPARepository;

    private final OrderJPARepository orderJPARepository;

    @Autowired
    public OrderJPARepositoryTest(
            EntityManager em,
            ObjectMapper om,
            UserJPARepository userJPARepository,
            OptionJPARepository optionJPARepository,
            ProductJPARepository productJPARepository,
            OrderJPARepository orderJPARepository) {
        this.em = em;
        this.om = om;
        this.userJPARepository = userJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.productJPARepository = productJPARepository;
        this.orderJPARepository = orderJPARepository;
    }
}
