package com.example.kakao.user;

import com.example.kakao._core.util.DummyEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class UserJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @BeforeEach
    public void setUp(){
        userJPARepository.save(newUser("ssar"));
        em.clear();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
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
    } // 매번 Transactional 끝날 때마다 rollback을 진행합니다. 데이터가 rollback이 되었는데 auto-increment는 초기화가 되지 않을 수도 있습니다.
        // 그리고 추가적으로 save() finalByEmail_test() 둘 중에 어느 것이 먼저 실행될지 모르기 때문에, save() 먼저 호출 된 다음,
        // auto increment에 의해서 id값이 1이 되고 그 다음 findByEmail_test()가 실행될 때 auto increment에 의해서 id값이 2가 되는 것인 것 같습니다. .

    @Test
    public void save(){}


}
