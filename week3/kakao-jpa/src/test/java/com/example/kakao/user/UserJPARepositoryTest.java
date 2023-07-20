package com.example.kakao.user;

import com.example.kakao._core.util.DummyEntity;
import net.bytebuddy.utility.RandomString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("유저_레포지토리_테스트")
@DataJpaTest
public class UserJPARepositoryTest extends DummyEntity {

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        userJPARepository.save(newUser("ssar"));
    }

    // 1. 눈으로 findByEmail() 쿼리 확인
    // 2. 못찾으면 exception
    // 3. setUp에 유저 한명 추가
    @DisplayName("유저_회원_찾기")
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
        Assertions.assertThat(userPS.getId()).isEqualTo(1);
        Assertions.assertThat(userPS.getEmail()).isEqualTo("ssar@nate.com");
        assertTrue(BCrypt.checkpw("meta1234!", userPS.getPassword()));
        Assertions.assertThat(userPS.getUsername()).isEqualTo("ssar");
        Assertions.assertThat(userPS.getRoles()).isEqualTo("ROLE_USER");
    }

    @DisplayName("유저_생성_성공")
    @Test
    public void join_success_test() {
        // given
        User user = newUser("ssar2");

        // when
        userJPARepository.save(user);

        // then
        Assertions.assertThat(user.getId()).isNotEqualTo(0);
    }

    // 중복 회원 등록시 예외처리
    @DisplayName("유저_생성_중복_실패")
    @Test
    public void join_fail_duplicated_email(){
        // given
        User user = newUser("ssar");

        // when and then
        Assertions.assertThatThrownBy(() -> {
            userJPARepository.save(user);
        });

    }


}
