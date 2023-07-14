package com.example.kakaoboardjpa.user;

import com.example.kakaoboardjpa.DummyEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest extends DummyEntity {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        userRepository.save(newUser("ssar"));
    }


    @Test
    public void join_uk_error_test() {
        // given
        User user = newUser("ssar");

        // when then
        Assertions.assertThatThrownBy(() -> {
            userRepository.save(user);
        });
    }

    @Test
    public void join_test() {
        // given
        User user = newUser("cos");
        System.out.println("영속화 되기 전 user id : " + user.getId());

        // when
        userRepository.save(user);
        System.out.println("영속화 된 후 user id : " + user.getId());

        // then
        Assertions.assertThat(user.getId()).isEqualTo(2);
        Assertions.assertThat(user.getUsername()).isEqualTo("cos");
        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
        Assertions.assertThat(user.getEmail()).isEqualTo("cos@nate.com");
    }

    // 테이블명 틀려보고, 메서드이름도 틀려보자.
// 눈으로 쿼리 결과를 먼저 한번 보자.
    @Test
    public void login_named_query_test() {
        // given
        String username = "ssar";
        String password = "1234";

        // when
        Optional<User> userOP = userRepository.findByUsernameAndPassword(username, password);

        // then
        Assertions.assertThat(userOP.get().getId()).isEqualTo(1);
        Assertions.assertThat(userOP.get().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(userOP.get().getPassword()).isEqualTo("1234");
        Assertions.assertThat(userOP.get().getEmail()).isEqualTo("ssar@nate.com");
    }

    @Test
    public void login_jpql_query_test() {
        // given
        String username = "ssar";
        String password = "1234";

        // when
        Optional<User> userOP = userRepository.jpqlFindByUsernameAndPassword(username, password);

        // then
        Assertions.assertThat(userOP.get().getId()).isEqualTo(1);
        Assertions.assertThat(userOP.get().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(userOP.get().getPassword()).isEqualTo("1234");
        Assertions.assertThat(userOP.get().getEmail()).isEqualTo("ssar@nate.com");
    }

    @Test
    public void login_native_query_test() {
        // given
        String username = "ssar";
        String password = "1234";

        // when
        Optional<User> userOP = userRepository.findByUsernameAndPassword(username, password);

        // then
        Assertions.assertThat(userOP.get().getId()).isEqualTo(1);
        Assertions.assertThat(userOP.get().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(userOP.get().getPassword()).isEqualTo("1234");
        Assertions.assertThat(userOP.get().getEmail()).isEqualTo("ssar@nate.com");
    }
}