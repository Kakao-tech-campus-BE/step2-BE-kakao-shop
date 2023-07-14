package com.example.kakao.user;

import com.example.kakao._core.util.DummyEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class UserJPARepositoryTest extends DummyEntity {

    @Autowired
    private UserJPARepository userJPARepository;

    @BeforeEach
    public void setUp(){
        userJPARepository.save(newUser("ssar"));
    }
    // 로그인 테스트도 '이메일 찾기 테스트'를 통해 해결 가능하기 때문에 따로 적지 않았습니다.
    @DisplayName("이메일 찾기")
    @Test
    public void findByEmail_test() {
        // given
        String email = "ssar@nate.com";

        // when
        User userPS = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일을 찾을 수 없습니다.")
        );
        System.out.println(userPS.getEmail());
        System.out.println(userPS.getPassword());

        // then (상태 검사)
        assertEquals(userPS, new User(1, "ssar@nate.com", "meta1234!", "ssar", "ROLE_USER"));
    }

    @Test
    public void save(){}


}
