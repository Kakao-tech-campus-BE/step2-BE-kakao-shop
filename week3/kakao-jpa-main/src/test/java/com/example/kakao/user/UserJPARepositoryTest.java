package com.example.kakao.user;

import com.example.kakao._core.util.DummyEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserJPARepositoryTest extends DummyEntity {

    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private EntityManager em;

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

    @DisplayName("회원가입 실패 테스트 - 이미 존재하는 유저(이메일 중복)")
    @Test
    public void join_uk_error_test() {
        // given
        User user = newUser("ssar");

        // when
        Throwable thrown = catchThrowable(() -> {
            userJPARepository.save(user);
        });

        // then
        Assertions.assertThat(thrown).isInstanceOf(DataIntegrityViolationException.class);
    }
    @DisplayName("회원가입 성공 테스트")
    @Test
    public void join_test() {
        // given
        User user = newUser("cos");
        System.out.println("영속화 되기 전 user id : " + user.getId());

        // when
        userJPARepository.save(user);
        System.out.println("영속화 된 후 user id : " + user.getId());

        // then
        assertEquals(user, new User(2, "cos@nate.com", "meta1234!", "cos", "ROLE_USER"));
    }
}
