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
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("장바구니 Repository JPA 테스트")
@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity{

    private final EntityManager em;
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final ProductJPARepository productJPARepository;
    private final UserJPARepository userJPARepository;
    private final ObjectMapper om;

    @Autowired
    public CartJPARepositoryTest(EntityManager em, CartJPARepository cartJPARepository, OptionJPARepository optionJPARepository, ProductJPARepository productJPARepository, UserJPARepository userJPARepository, ObjectMapper om) {
        this.em = em;
        this.cartJPARepository = cartJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.productJPARepository = productJPARepository;
        this.userJPARepository = userJPARepository;
        this.om = om;
    }

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        User user = userJPARepository.save(newUser("ssar"));
        List<Product> products = productJPARepository.saveAll(productDummyList());
        List<Option> options = optionJPARepository.saveAll(optionDummyList(products));
        cartJPARepository.saveAll(cartDummyList(options, user));
        em.clear();
    }


    @DisplayName("장바구니_추가_성공")
    @Test
    public void cart_save_test() {
        // given
        User user = newUser("taeho");
        Product product = newProduct("테스트 프로덕트", 16, 10000);
        Option option = newOption(product, "테스트 옵션", 12000);
        userJPARepository.save(user);
        productJPARepository.save(product);
        optionJPARepository.save(option);
        Cart cart = newCart(user,option, 5);

        // when
        Cart result = cartJPARepository.save(cart);

        // then
        assertThat(result.getId()).isNotEqualTo(0);
    }

    @DisplayName("장바구니_추가_실패_옵션_회원_중복")
    @Test
    public void cart_save_fail_duplicate_test() {
        // given
        User user = userJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        Option option = optionJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 옵션이 존재하지 않습니다."));
        Cart cart = newCart(user, option, 1);

        // when then
        assertThatThrownBy(() -> cartJPARepository.save(cart))
                .isInstanceOf(DataIntegrityViolationException.class);

        // 다음과 같이 when then을 나눌 수 있음.
        // when
//        Throwable throwable = Assertions.assertThrows(DataIntegrityViolationException.class,
//                () -> cartJPARepository.save(cart));

        // then
//        assertThat(throwable.getCause()).isInstanceOf(ConstraintViolationException.class);
//        assertThat(throwable).isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("장바구니_추가_실패_옵션_없음")
    @Test
    public void cart_save_fail_no_option() {
        // given
        User user = userJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
//        Option option = optionJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 옵션이 존재하지 않습니다."));
        Cart cart = Cart.builder()
                .price(10000)
                .quantity(5)
                .user(user)
                .build();

        // when then
        assertThatThrownBy(() -> cartJPARepository.save(cart))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @DisplayName("장바구니_추가_실패_회원_없음")
    @Test
    public void cart_save_fail_no_user() {
        // given
//        User user = userJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        Option option = optionJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 옵션이 존재하지 않습니다."));
        Cart cart = Cart.builder()
                .price(10000)
                .quantity(5)
                .option(option)
                .build();

        // when then
        assertThatThrownBy(() -> cartJPARepository.save(cart))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    // 현재 트랜잭션안에서 동작하기 때문에 save를 해도 dirtyCheck를 하지않아 update가 되지 않는다.
    // 그래서 다음과 같은 방법을 사용해야한다.
    // 1. em.flush() 혹은 saveAndFlush()로 직접 flush 해주기
    // 2. 다른 JPQL을 한번 더 호출한다.
    @DisplayName("장바구니_업데이트_성공")
    @Test
    public void cart_update_test() {
        // given
        List<Integer> cartIds = List.of(1, 2);
        List<Integer> quantities = List.of(5, 10);
        List<Cart> cartList = cartJPARepository.findByIdIn(cartIds);

        // when
        for(int i=0; i<cartList.size(); i++) {
            cartList.get(i).update(quantities.get(i), quantities.get(i) * cartList.get(i).getOption().getPrice());
//            cartJPARepository.saveAndFlush(cartList.get(i)); // 직접 flush 해주기
        }

        // in절은 1차 캐시를 사용하지 않고 데이터베이스를 조회해서 사용
        List<Cart> cartResult = cartJPARepository.findByIdIn(cartIds); // 다른 JPQL 사용

        // then
        assertThat(cartList.get(0).getQuantity()).isEqualTo(5);
        assertThat(cartList.get(0).getPrice()).isEqualTo(50000);
        assertThat(cartList.get(1).getQuantity()).isEqualTo(10);
        assertThat(cartList.get(1).getPrice()).isEqualTo(109000);
    }

    @DisplayName("장바구니_findByIdIn_성공")
    @Test
    public void cart_findByIdIn_success_test() throws JsonProcessingException {
        // given
        List<Integer> cartIds = Arrays.asList(1,2);

        // when
        List<Cart> cartList = cartJPARepository.findByIdIn(cartIds);
//        String responseData = om.writeValueAsString(cartList);
//        System.out.println(responseData);

        // then
        assertThat(cartList.size()).isEqualTo(2);
        assertThat(cartList.get(0).getId()).isEqualTo(1);
        assertThat(cartList.get(1).getId()).isEqualTo(2);
    }


    @DisplayName("장바구니_findByIdUserId_성공")
    @Test
    public void cart_findByUserId_test() throws JsonProcessingException {
        // given
        int userId = 1;

        // when
        List<Cart> cartList = cartJPARepository.findByUserId(userId);
        String responseData = om.writeValueAsString(cartList);
        System.out.println(responseData);

        // then
        assertThat(cartList.get(1).getId()).isEqualTo(2);
        assertThat(cartList.get(1).getPrice()).isEqualTo(32700);
        assertThat(cartList.get(1).getQuantity()).isEqualTo(3);
        assertThat(cartList.get(1).getUser().getId()).isEqualTo(1);
        assertThat(cartList.get(1).getUser().getUsername()).isEqualTo("ssar");
        assertThat(cartList.get(1).getUser().getEmail()).isEqualTo("ssar@nate.com");
        assertThat(cartList.get(1).getOption().getId()).isEqualTo(2);
        assertThat(cartList.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
        assertThat(cartList.get(1).getOption().getPrice()).isEqualTo(10900);
        assertThat(cartList.get(1).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(cartList.get(1).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        assertThat(cartList.get(1).getOption().getProduct().getDescription()).isEqualTo("");
        assertThat(cartList.get(1).getOption().getProduct().getPrice()).isEqualTo(1000);
        assertThat(cartList.get(1).getOption().getProduct().getImage()).isEqualTo("/images/1.jpg");
    }
}
