package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.Product;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.example.util.WithMockCustomUser;
import com.example.util.WithMockCustomUserSecurityContextFactory;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@DataJpaTest
@RequiredArgsConstructor
public class CartJPARepositoryTest extends DummyEntity {

    private final CartJPARepository cartJPARepository;

    private MockMvc mockMvc;

    private final WithMockCustomUserSecurityContextFactory contextFactory;

    @BeforeEach
    public void setUp(){

    }

    
    @Test
    @WithMockCustomUser
    public void findByUserId_test() {
        // given
        CustomUserDetails customUserDetails =

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
    public void save(){}


}
