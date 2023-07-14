package com.example.kakao.user;

import com.example.kakao._core.util.DummyEntity;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@Transactional
public class UserJPARepositoryTest extends DummyEntity {

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private EntityManager em;

    // 더미 데이터 추가해두기
    @BeforeEach
    public void setUp(){
        em.clear();
    }

    // 1. 눈으로 findByEmail() 쿼리 확인
    // 2. 못찾으면 exception
    // 3. setUp에 유저 한명 추가
    @Test
    public void findByEmail_test() {
        // given
        userJPARepository.save(newUser("ssar"));
        String email = "ssar@nate.com";

        // when
        User userPS = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일을 찾을 수 없습니다.")
        );
        System.out.println(userPS.getEmail());
        System.out.println(userPS.getPassword());

        // then (상태 검사)
        Assertions.assertThat(userPS.getId()).isEqualTo(2);
        Assertions.assertThat(userPS.getEmail()).isEqualTo("ssar@nate.com");
        assertTrue(BCrypt.checkpw("meta1234!", userPS.getPassword()));
        Assertions.assertThat(userPS.getUsername()).isEqualTo("ssar");
        Assertions.assertThat(userPS.getRoles()).isEqualTo("ROLE_USER");
    }

    @Test
    public void join_test() {
        // given
        String email = "jason@nate.com";

        // when
        // jason 회원가입 -> newUser를 활용한 새로운 user 객체 생성
        userJPARepository.save(newUser("jason"));
        // jason의 이메일을 사용한 user 탐색
        User userPS = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일을 찾을 수 없습니다.")
        );
        // jason의 email, password 출력
        System.out.println("Jason's email : " + userPS.getEmail());
        System.out.println("Jason's password : " + userPS.getPassword());

        // then (상태 검사)
        Assertions.assertThat(userPS.getId()).isEqualTo(1);
        Assertions.assertThat(userPS.getEmail()).isEqualTo("jason@nate.com");
        assertTrue(BCrypt.checkpw("meta1234!", userPS.getPassword()));
        Assertions.assertThat(userPS.getUsername()).isEqualTo("jason");
        Assertions.assertThat(userPS.getRoles()).isEqualTo("ROLE_USER");
    }
}
