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

    // 1. 눈으로 findByEmail() 쿼리 확인
    // 2. 못찾으면 exception
    // 3. setUp에 유저 한명 추가
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

    @Test
    public void join_test() {
        User user = newUser("cos");

        userJPARepository.save(user);

        Assertions.assertThat(user.getId()).isEqualTo(2);
        Assertions.assertThat(user.getEmail()).isEqualTo("cos@nate.com");
        assertTrue(BCrypt.checkpw("meta1234!", user.getPassword()));
        Assertions.assertThat(user.getUsername()).isEqualTo("cos");
        Assertions.assertThat(user.getRoles()).isEqualTo("ROLE_USER");
    }

//    로그인 테스트는 비밀번호 암호화로 저장되어 있기 때문에 오류 발생
//    @Test
//    public void login_test() {
//        String email = "ssar@nate.com";
//        String password = "meta1234!";
//
//        Optional<User> user = userJPARepository.findByEmailAndPassword(email, password);
//
//        Assertions.assertThat(user.get().getId()).isEqualTo(1);
//        Assertions.assertThat(user.get().getUsername()).isEqualTo("ssar");
//        assertTrue(BCrypt.checkpw("meta1234!", user.get().getPassword()));
//        Assertions.assertThat(user.get().getEmail()).isEqualTo("ssar@nate.com");
//    }

    @Test
    public void save(){}


}
