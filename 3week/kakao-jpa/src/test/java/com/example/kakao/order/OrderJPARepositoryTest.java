package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static com.example.kakao._core.utils.PrintUtils.getPrettyString;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @BeforeEach //test 실행 전 호출 -> 백업
    public void setup() {
        //order 새로 만들기 -> 필요한 매개변수 user
        User userSP = userJPARepository.save(newUser("chaewon"));


        //cartJPARepository.saveAll(cartsSP);

        em.clear();
        System.out.println("----TEST 시작----");
    }

    @AfterEach
    public void resetIndex() {
        cartJPARepository.deleteAll();
        userJPARepository.deleteAll();
        productJPARepository.deleteAll();
        optionJPARepository.deleteAll();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.clear();
        System.out.println("----TEST 끝----");
    }

}
