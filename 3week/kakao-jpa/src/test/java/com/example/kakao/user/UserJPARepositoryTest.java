package com.example.kakao.user;

import com.example.kakao._core.util.DummyEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("User JPARepository로 CRUD 테스트")
@DataJpaTest
public class UserJPARepositoryTest extends DummyEntity {

    private final UserJPARepository userJPARepository;
    private final EntityManager em;
    UserJPARepositoryTest(@Autowired UserJPARepository userJPARepository,
                          @Autowired EntityManager em){
        this.userJPARepository = userJPARepository;
        this.em = em;
    }
    
    // 해당 테스트 돌리기전에 ssar이라는 유저 셋업
    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        userJPARepository.save(newUser("ssar"));
    }

    @DisplayName("유저 insert 테스트")
    @Test
    public void save(){

        // given
        String username = "donu";
        User newUser = User.builder()
                .email(username + "@nate.com")
                .password("donu1234")
                .username(username)
                .roles(username.equals("admin") ? "ROLE_ADMIN" : "ROLE_USER")
                .build();

        // when
        User savedUser = userJPARepository.save(newUser);

        // then
        assertEquals(savedUser, newUser);
        assertNotNull(savedUser.getId());
    }

    @DisplayName("유저 findByEmail 테스트")
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
        assertEquals(userPS.getId(),1);
        Assertions.assertThat(userPS.getEmail()).isEqualTo("ssar@nate.com");
        assertTrue(BCrypt.checkpw("meta1234!", userPS.getPassword()));
        Assertions.assertThat(userPS.getUsername()).isEqualTo("ssar");
        Assertions.assertThat(userPS.getRoles()).isEqualTo("ROLE_USER");
    }

    @DisplayName("유저 Update 테스트")
    @Test
    public void userUpdate_test() {
        // given
        String email = "ssar@nate.com";
        User userPS = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일을 찾을 수 없습니다."));

        // when
        userPS.userChangeName("donu");
        em.flush();
        em.clear();
        User user = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일을 찾을 수 없습니다."));

        // then
        Assertions.assertThat(user.getEmail()).isEqualTo("ssar@nate.com");
        assertTrue(BCrypt.checkpw("meta1234!", user.getPassword()));
        Assertions.assertThat(user.getUsername()).isEqualTo("donu");
        Assertions.assertThat(user.getRoles()).isEqualTo("ROLE_USER");
    }

    @DisplayName("유저 delete 테스트")
    @Test
    public void deleteUser_test() {
        // given
        String email = "ssar@nate.com";
        User userPS = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일을 찾을 수 없습니다."));
        // when
        userJPARepository.delete(userPS);
        em.flush();
        em.clear();

        // then (상태 검사)
        try {
            userJPARepository.findByEmail(email).orElseThrow(
                    () -> new RuntimeException("해당 이메일을 찾을 수 없습니다."));
        } catch (RuntimeException e) {
            assertEquals("해당 이메일을 찾을 수 없습니다.", e.getMessage());
        }
    }
}
