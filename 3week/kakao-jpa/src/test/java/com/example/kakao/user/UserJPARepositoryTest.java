package com.example.kakao.user;

import com.example.kakao._core.util.DummyEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(ObjectMapper.class)
@DataJpaTest
public class UserJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @BeforeEach
    public void setUp(){userJPARepository.save(newUser("ssar"));}

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
    public void user_save_test() {
        // given
        String username = "cha";
        User user = newUser(username);

        // when
        User userPS = userJPARepository.save(user);

        // then
        Assertions.assertThat(userPS).isEqualTo(user);
    }

    @Test
    public void user_update_test() {
        // given
        String email = "ssar@nate.com";

        // when
        User userPS = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일을 찾을 수 없습니다.")
        );

        userPS.update("abc","2490");
        userJPARepository.save(userPS);
        em.flush();
        em.detach(userPS);

        userPS = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일을 찾을 수 없습니다.")
        );

        // then
        Assertions.assertThat(userPS.getUsername()).isEqualTo("abc");
        Assertions.assertThat(userPS.getPassword()).isEqualTo("2490");

    }

    @Test
    public void user_mUpdate_test() {
        // given
        String email = "ssar@nate.com";
        String newUsername = "abc";
        String newPassword = "2490";

        // when
        User user = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일을 찾을 수 없습니다.")
        );

        userJPARepository.updateUserByEmail(email, newUsername, newPassword);

        em.detach(user);
        user = userJPARepository.findByEmail(email).get();


        // then
        Assertions.assertThat(user.getUsername()).isEqualTo("abc");
        Assertions.assertThat(user.getPassword()).isEqualTo("2490");

    }

    @Test
    public void user_delete_test() {
        // given
        String email = "ssar@nate.com";

        // when
        User userPS = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일을 찾을 수 없습니다.")
        );

        userJPARepository.delete(userPS);

        // then
        Optional<User> deletedUser = userJPARepository.findByEmail(email);
        Assertions.assertThat(deletedUser).isEmpty();


    }


}
