package com.example.kakao.cart;

import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

@DataJpaTest
public class CartJPARepositoryTest {
    private final EntityManager em;

    private final CartJPARepository cartJPARepository;

    private final ObjectMapper om;

    private final UserJPARepository userJPARepository;

    private final OptionJPARepository optionJPARepository;

    private final ProductJPARepository productJPARepository;

    @Autowired
    public CartJPARepositoryTest(
            EntityManager em,
            CartJPARepository cartJPARepository,
            UserJPARepository userJPARepository,
            OptionJPARepository optionJPARepository,
            ObjectMapper om,
            ProductJPARepository productJPARepository) {
        this.em = em;
        this.cartJPARepository = cartJPARepository;
        this.userJPARepository = userJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.om = om;
        this.productJPARepository = productJPARepository;
    }
}
