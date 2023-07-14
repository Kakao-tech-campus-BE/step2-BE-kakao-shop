package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.example.util.WithMockCustomUser;
import com.example.util.WithMockCustomUserSecurityContextFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@DataJpaTest
@Import(ObjectMapper.class)
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private EntityManager em;
    @Autowired
    private ObjectMapper om;

    //private MockMvc mockMvc;
    //private final WithMockCustomUserSecurityContextFactory contextFactory;

    @BeforeEach
    public void setUp(){
        User user = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        cartJPARepository.saveAll(cartDummyList(user, optionListPS));

        em.clear();
    }


    @DisplayName("사용자의 장바구니 불러오기 테스트")
    @Test
    public void findByUserId_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Cart> cartListPS = cartJPARepository.findByUserIdWithUserAndOption(id); // Lazy

        System.out.println("json 직렬화 직전========================");
        //om.setSerializationInclusion(JsonInclude.Include.NON_NULL); //lazy로 불러와 null인 user와 option 제외하기
        String responseBody = om.writeValueAsString(cartListPS); //Product는 직렬화 되는데 User는 왜 안됨? -> Null이라서
        System.out.println("테스트 : "+responseBody);

        Assertions.assertThat(cartListPS.get(0).getOption().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getOption().getProduct().getProductName())
                .isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");

        // then (상태 검사)
        Assertions.assertThat(cartListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartListPS.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(cartListPS.get(0).getQuantity()).isEqualTo(10);
        Assertions.assertThat(cartListPS.get(0).getPrice()).isEqualTo(100000);

        Assertions.assertThat(cartListPS.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(cartListPS.get(1).getOption().getId()).isEqualTo(2);
        Assertions.assertThat(cartListPS.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
        Assertions.assertThat(cartListPS.get(1).getOption().getPrice()).isEqualTo(10900);
        Assertions.assertThat(cartListPS.get(1).getQuantity()).isEqualTo(10);
        Assertions.assertThat(cartListPS.get(1).getPrice()).isEqualTo(109000);

    }

    @Test
    public void save(){}


}
