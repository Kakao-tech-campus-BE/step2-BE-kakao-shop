package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    CartJPARepository cartJPARepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void setUp() {
        // Given
        User user1 = newUser("user1");

        em.persist(user1);

        List<Product> productListPS = productDummyList();
        productListPS.forEach(em::persist);
        List<Option> optionListPS = optionDummyList(productListPS);
        optionListPS.forEach(em::persist);

        em.persist(newCart(user1, optionListPS.get(0), 3));
        em.persist(newCart(user1, optionListPS.get(1), 5));

        em.flush();
        em.clear();
    }

    @Test
    public void findCartsByUserId() {
        // Given
        int userId = 1;

        // When
        List<Cart> user1CartList = cartJPARepository.findByUserId(userId);

        try {
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(user1CartList);
            System.out.println("테스트: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Then
        then(user1CartList.size()).isEqualTo(2);
    }
}
