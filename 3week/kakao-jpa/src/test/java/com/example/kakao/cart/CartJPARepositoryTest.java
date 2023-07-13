package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        // Cart에 필요한 Option 객체들과 Option 객체들이 필요한 product 객체들을 사전에 저장
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        // user 사전 저장
        userJPARepository.save(newUser("kevin"));
        userJPARepository.save(newUser("master"));
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.clear();
    }

    // (기능8) 장바구니 담기 -> 각각을 select로 가져옴, 총 2개
    @Test
    public void save_cart_select_test() {
        // given
        // 현재 select가 2번 일어난다. 더 줄일 수는 없을까? -> fetch 조인을 한번 써보자.
        Optional<User> user = userJPARepository.findById(1);
        Optional<Option> option = optionJPARepository.findById(1);

        Cart cart = newCart(user.get(), option.get(), 2);

        // when
        Cart cartOP = cartJPARepository.save(cart);

        // then
        Assertions.assertThat(cartOP.getId()).isEqualTo(1);
        Assertions.assertThat(cartOP.getUser()).isEqualTo(user.get());
        Assertions.assertThat(cartOP.getQuantity()).isEqualTo(2);
    }

    // (기능8) 장바구니 담기 -> User와 fetch를 하나의 select로 가져올 수는 없을까?
    @Test
    public void save_cart_fetch_test() {
        // given
        // 현재 select가 2번 일어난다. 더 줄일 수는 없을까?
        Optional<User> user = userJPARepository.findById(1);
        Optional<Option> option = optionJPARepository.findById(1);

        Cart cart = newCart(user.get(), option.get(), 2);

        // when
        Cart cartOP = cartJPARepository.save(cart);

        // then
        Assertions.assertThat(cartOP.getId()).isEqualTo(1);
        Assertions.assertThat(cartOP.getUser()).isEqualTo(user.get());
        Assertions.assertThat(cartOP.getQuantity()).isEqualTo(2);
    }

    // (기능11) 주문하기 - (장바구니 업데이트)
    @Test
    public void update_cart_test() {
        // given
        Optional<User> user1 = userJPARepository.findById(1);
        Optional<Option> option = optionJPARepository.findById(1);

        Cart cart1 = newCart(user1.get(), option.get(), 2);
        Cart cartOP = cartJPARepository.save(cart1);

        // user를 다른 User 엔티티로 변경했을 때 성공적으로 더티 체킹 되는지
        cartOP.updateQuantity(7);
        em.flush();

        // when
        // 더티 체킹을 통한 값 변경
        Optional<Cart> cartChOP = cartJPARepository.findById(1);

        // then
        Assertions.assertThat(cartChOP.get().getId()).isEqualTo(1);
        Assertions.assertThat(cartChOP.get().getUser()).isEqualTo(user1.get());
        Assertions.assertThat(cartChOP.get().getQuantity()).isEqualTo(7);
    }

    // 장바구니 삭제
    @Test
    public void delete_cart_test() {
        // given
        Optional<User> user1 = userJPARepository.findById(1);
        Optional<Option> option = optionJPARepository.findById(1);

        Cart cart1 = newCart(user1.get(), option.get(), 2);
        cartJPARepository.save(cart1);
        em.flush();

        // when
        cartJPARepository.deleteById(1);

        // then
        Assertions.assertThat(cartJPARepository.findById(1)).isEmpty();
    }
}
