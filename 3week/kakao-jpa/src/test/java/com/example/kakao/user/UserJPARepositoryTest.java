package com.example.kakao.user;

import com.example.kakao._core.util.DummyEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // BeforeEach 에 Alter를 하는 어노테이션과 같다.
@DataJpaTest // 자동으로 롤백을 한다.
public class UserJPARepositoryTest extends DummyEntity {



    @Autowired
    private UserJPARepository userJPARepository;


    @BeforeEach
    public void setUp(){
        userJPARepository.save(newUser("ssar"));

    }


    @DisplayName("이메일로 사용자 찾기")
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


    @DisplayName("중복사용자 추가 에러발생")
    @Test
    public void join_error_test() {
        // given
        User duplicateUser = newUser("ssar");
        // when
        Throwable thrown = catchThrowable(() -> {
            userJPARepository.save(duplicateUser);
        });
        // then
        Assertions.assertThat(thrown).isInstanceOf(DataIntegrityViolationException.class);
    }


    @DisplayName("회원가입 테스트 (pc 적재시 id)")
    @Test
    public void join_user_test(){
        // given
        User user = newUser("cos");
        System.out.println("영속화 되기 전 user id : "+user.getId());
        // when
        User savedUser = userJPARepository.save(user);
        System.out.println("영속화 된 후 user id : "+user.getId());

        // then
        User foundUser = userJPARepository.findById(savedUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Assertions.assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
        Assertions.assertThat(foundUser.getUsername()).isEqualTo(savedUser.getUsername());
        Assertions.assertThat(foundUser.getEmail()).isEqualTo(savedUser.getEmail());
    }


    @DisplayName("회원 정보 수정 테스트")
    @Test
    //인증 정보를 들고 접근하기 때문에 가져온 id가 없는 정보는 아닐거라고 가정
    public void update_user_test(){
        //인증 정보에서 email을 가져왔다고 가정
        //given
        String email = "ssar@nate.com";

        // when
        User foundUser = userJPARepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String updateUsername = "update_ssar";
        foundUser.setUsername(updateUsername);
        userJPARepository.save(foundUser);

        //then
        User updateUser = userJPARepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Assertions.assertThat(updateUser.getUsername()).isEqualTo(updateUsername);


    }

    @DisplayName("회원정보 삭제 테스트")
    @Test
    public void delete_user_test(){
        //인증 정보에서 email을 가져왔다고 가정
        //given
        String email = "ssar@nate.com";

        // when
        User foundUser = userJPARepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userJPARepository.delete(foundUser);

        //then
        Optional<User> deletedUser = userJPARepository.findByEmail(email);
        Assertions.assertThat(deletedUser).isEmpty();

    }

    @AfterEach
    public void tearDown() {
    }



}
