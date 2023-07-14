package com.example.kakao.item;

import com.example.kakao.order.OrderJPARepository;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

@Import(ObjectMapper.class)
@DataJpaTest
public class ItemJPARepositoryTest {
    private final OrderJPARepository orderJPARepository;
    private final ProductJPARepository productJPARepository;
    private final UserJPARepository userJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final EntityManager em;
    private final ObjectMapper om;

    @Autowired
    public ItemJPARepositoryTest(OrderJPARepository orderJPARepository, ProductJPARepository productJPARepository, UserJPARepository userJPARepository, OptionJPARepository optionJPARepository, EntityManager em, ObjectMapper om) {
        this.orderJPARepository = orderJPARepository;
        this.productJPARepository = productJPARepository;
        this.userJPARepository = userJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.em = em;
        this.om = om;
    }

//    @BeforeEach
//    public void setUp(){
//        User user = userJPARepository.save()
//    }
}
