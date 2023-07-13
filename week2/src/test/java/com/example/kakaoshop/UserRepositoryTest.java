package com.example.kakaoshop;

import com.example.kakaoshop.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void join_uk_error_test() {
        // given
        User user = User.builder().username("ssar").password("1234").email("sar@naver.com").build();

        // when
        userRepository.save(null);

        // then
    }
}
