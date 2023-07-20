package com.example.kakao.user;

import com.example.kakao._core.util.DummyEntity;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@DataJpaTest
class UserJPARepositoryTest extends DummyEntity {

    @Autowired
    private UserJPARepository userJPARepository;

    @BeforeEach
    public void setUp(){
        userJPARepository.save(newUser("ssar"));
    }

    @Test
    @DisplayName("유저 이메일로 찾기")
    void findByEmail_test() {
        //given
        String email = "ssar@nate.com";

        //when
        User user = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일로 유저를 찾을 수 없습니다.")
        );
        log.info(user.getEmail());
        log.info(user.getPassword());

        //then
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getEmail()).isEqualTo("ssar@nate.com");
        assertTrue(BCrypt.checkpw("meta1234!", user.getPassword()));
        assertThat(user.getUsername()).isEqualTo("ssar");
        assertThat(user.getRoles()).isEqualTo("ROLE_USER");
    }

    @Test
    @DisplayName("유저 아이디로 찾기")
    void findById_test(){
        //given
        int id = 1;

        //when
        User user = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 아이디로 유저를 찾을 수 없습니다.")
        );
        log.info(user.getEmail());

        //then
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getEmail()).isEqualTo("ssar@nate.com");
        assertTrue(BCrypt.checkpw("meta1234!", user.getPassword()));
        assertThat(user.getUsername()).isEqualTo("ssar");
        assertThat(user.getRoles()).isEqualTo("ROLE_USER");
    }

    @Test
    void save(){}

}
