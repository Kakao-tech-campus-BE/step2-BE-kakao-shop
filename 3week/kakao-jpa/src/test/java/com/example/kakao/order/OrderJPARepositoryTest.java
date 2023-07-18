package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Optional;

@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;

    @BeforeEach
    public void setUp() {
        User user = userJPARepository.save(newUser("gijun"));

        orderJPARepository.save(newOrder(user));
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.clear();
    }

    // BDD 패턴
    // 1. given
    // 2. when
    // 3. then (상태 검사)

    @Test
    public void findByOrder_test() {
        // 1. given
        String email = "gijun@nate.com";

        // 2. when
        User user = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 user를 찾을 수 없습니다.")
        );
        Order order = orderJPARepository.findOrdersByUser(user).orElseThrow(
                () -> new RuntimeException("해당 id 값을 찾을 수 없습니다.")
        );
        System.out.println("-------------------------");
        System.out.println(order.getId());
        System.out.println(order.getUser().getUsername());
        System.out.println(order.getUser().getEmail());
        System.out.println(order.getClass());

        System.out.println("-------------------------");
        // 3. then (상태 검사)

        Assertions.assertThat(order.getId()).isEqualTo(1);
        Assertions.assertThat(order.getUser().getUsername()).isEqualTo("gijun");
        Assertions.assertThat(order.getUser().getEmail()).isEqualTo("gijun@nate.com");

    }

    @Test
    public void save(){}

}
