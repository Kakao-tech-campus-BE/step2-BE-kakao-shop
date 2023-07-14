package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ObjectMapper om;

    // 조회 및 업데이트 테스트를 위해 2개의 option 데이터를 가진 1개의 cart 데이터 생성
    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));

        List<Integer> optionIds = Arrays.asList(4, 5);
        List<Option> optionListPS = optionJPARepository.findAllById(optionIds);

        User userPS = userJPARepository.save(newUser("ssar"));
        for (Option option : optionListPS) {
            cartJPARepository.save(newCart(userPS, option, 5));
        }

        em.clear();
    }

    // 이미 SetUp에서 데이터 생성이 성공적으로 이루어짐을 보였음
    // 때문에 같은 user, option 객체를 가진 데이터를 중복 저장 시 발생하는 에러를 테스트
    @Test
    public void cart_save_error_test() {
        // given
        int id = 1;

        List<Integer> optionIds = Arrays.asList(4, 5);
        List<Option> optionListPS = optionJPARepository.findAllById(optionIds);

        User userPS = userJPARepository.findById(id).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        // when & then
        for (Option option : optionListPS) {
            Assertions.assertThatThrownBy(() -> cartJPARepository.save(newCart(userPS, option, 5)))
                    .isInstanceOf(DataIntegrityViolationException.class);
        }
    }

    // user id를 통한 장바구니 조회
    // fetch전략을 eager로 지정 시 네임드 쿼리인 findByUserId를 사용해도 cart와 매핑 관계를 가진 객체 정보를 모두 함꼐 가져옴
    @Test
    public void cart_findByUserId_eager_test() throws JsonProcessingException {
        // given
        int id = 1;

        Product productPS = productJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("상품을 찾을 수 없습니다")
        ); // option의 product 컬럼이 lazy fetch인 경우 해당 코드를 실행해야함

        // when
        List<Cart> cartListPS = cartJPARepository.findByUserId(id);

        String responseBody = om.writeValueAsString(cartListPS);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(cartListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(cartListPS.get(0).getOption().getId()).isEqualTo(4);
        Assertions.assertThat(cartListPS.get(0).getOption().getOptionName()).isEqualTo("뽑아쓰는 키친타올 130매 12팩");
        Assertions.assertThat(cartListPS.get(0).getOption().getPrice()).isEqualTo(16900);
        Assertions.assertThat(cartListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartListPS.get(0).getPrice()).isEqualTo(84500);
    }

    // user id를 통한 장바구니 조회
    // fetch전략을 lazy로 지정 시 네임드 쿼리인 findByUserId를 사용하면 target 없는 option과 user의 프록시 객체만을 가지고 있게 됨
    // 이후 json 직렬화를 실행하게 되면 user와 option 프록시 객체를 초기화하는 속도보다 빨라 JsonProcessingException이 발생하게 된다.
    @Test
    public void cart_findByUserId_lazy_error_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when & then
        List<Cart> cartListPS = cartJPARepository.findByUserId(id);

        Assertions.assertThatThrownBy(() -> om.writeValueAsString(cartListPS))
                .isInstanceOf(JsonProcessingException.class);
    }

    // user id를 통한 장바구니 조회
    // fetch전략을 lazy로 지정 시 네임드 쿼리인 findByUserId를 사용하면 target 없는 option과 user의 프록시 객체만을 가지고 있게 됨
    // 그러므로 필요한 option과 user 객체를 미리 영속성 컨텍스트에 저장한 후 findByUserId를 사용
    @Test
    public void cart_findByUserId_lazy_test() throws JsonProcessingException {
        // given
        int id = 1;

        List<Integer> optionIds = Arrays.asList(4, 5);
        List<Option> optionListPS = optionJPARepository.mFindAllById(optionIds);
        // option의 product 컬럼이 지연로딩일 때를 고려하여 jpql로 fetch join을 하는 mFindAllById를 작성하여 사용

        User UserPS = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("사용자를 찾을 수 없습니다")
        );

        // when
        List<Cart> cartListPS = cartJPARepository.findByUserId(id);

        String responseBody = om.writeValueAsString(cartListPS);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(cartListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(cartListPS.get(0).getOption().getId()).isEqualTo(4);
        Assertions.assertThat(cartListPS.get(0).getOption().getOptionName()).isEqualTo("뽑아쓰는 키친타올 130매 12팩");
        Assertions.assertThat(cartListPS.get(0).getOption().getPrice()).isEqualTo(16900);
        Assertions.assertThat(cartListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartListPS.get(0).getPrice()).isEqualTo(84500);
    }

    // user id를 통한 장바구니 조회
    // fetch전략을 lazy로 지정했기 때문에 jqpl을 사용하여 option과 user 객체에 대하여 fetch join을 하는 mFindByUserId 작성하여 사용
    @Test
    public void cart_mFindByUserId_lazy_test() throws JsonProcessingException {
        // given
        int id = 1;

        Product productPS = productJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("상품을 찾을 수 없습니다")
        ); // option의 product 컬럼이 지연로딩일 때를 고려하여 product 객체를 영속성 컨텍스트에 미리 저장

        // when
        List<Cart> cartListPS = cartJPARepository.mFindByUserId(id);

        String responseBody = om.writeValueAsString(cartListPS);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(cartListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(cartListPS.get(0).getOption().getId()).isEqualTo(4);
        Assertions.assertThat(cartListPS.get(0).getOption().getOptionName()).isEqualTo("뽑아쓰는 키친타올 130매 12팩");
        Assertions.assertThat(cartListPS.get(0).getOption().getPrice()).isEqualTo(16900);
        Assertions.assertThat(cartListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartListPS.get(0).getPrice()).isEqualTo(84500);
    }

    // 장바구니 업데이트
    // cart의 업데이트 요소는 가격과 개수 뿐이기 때문에 네임드 쿼리를 사용
    @Test
    public void cart_update_test() throws JsonProcessingException {
        // given
        int id = 1;
        List<Cart> cartListPS = cartJPARepository.findByUserId(id);

        // when
        for (Cart cart : cartListPS) {
            cart.update(10, cart.getPrice() / cart.getQuantity()*10);
        }
        cartJPARepository.saveAllAndFlush(cartListPS);

        // then
        cartListPS = cartJPARepository.findByUserId(id);

        for (Cart cart : cartListPS) {
            Assertions.assertThat(cart.getQuantity()).isEqualTo(10);
        }
    }
}
