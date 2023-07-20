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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User user = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));

        // 카트에 0, 1, 5번째 option 저장
        cartJPARepository.save(newCart(user, optionListPS.get(0), 5));
        cartJPARepository.save(newCart(user, optionListPS.get(1), 5));
        cartJPARepository.save(newCart(user, optionListPS.get(5), 5));

        em.clear();
    }

    // user id로 카트 전체 조회 test
    @Test
    public void cart_findByUserId_test() throws JsonProcessingException {

        // given
        int id = 1;

        // when
        List<Cart> cartList = cartJPARepository.findByUserId(id);
        //String responseBody = om.writeValueAsString(cartList);
        //System.out.println("테스트 : "+responseBody);

        // then
        // 모든 요소를 검사해야 할지 핵심적인 것들만 검사하면 될지 궁금합니다
        // 만약 핵심 요소들만 검사한다면, 어떤 것을 주로 비교하는지 궁금합니다
        assertThat(cartList.size()).isEqualTo(3);
        assertThat(cartList.get(0).getUser().getId()).isEqualTo(id);
        assertThat(cartList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(cartList.get(1).getOption().getId()).isEqualTo(2);
        assertThat(cartList.get(2).getOption().getId()).isEqualTo(6);

    }

    // 카트 id로 조회하는 test
    @Test
    public void cart_findById_test() throws JsonProcessingException {

        // given
        int id = 1;

        // when
        Cart findCartItem = cartJPARepository.findById(id);

        // then
        assertThat(findCartItem.getId()).isEqualTo(id);
        assertThat(findCartItem.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    }

    // 카트에 추가하는 test
    @Test
    public void cart_save_test() throws JsonProcessingException {

        // given
        User user = userJPARepository.findById(1).orElseThrow();
        Option option = optionJPARepository.findById(8).orElseThrow();
        Cart cartItem = newCart(user, option, 10);

        // when
        Cart saveCartItem = cartJPARepository.save(cartItem);

        // then
        assertThat(saveCartItem.getId()).isEqualTo(4);
        assertThat(saveCartItem.getOption().getId()).isEqualTo(8);
        assertThat(saveCartItem.getQuantity()).isEqualTo(10);
    }

    // 카트 아이템 수정하는 test
    @Test
    public void cart_update_test() throws JsonProcessingException {

        // given
        Cart cartItem = cartJPARepository.findById(3);
        int quantity = 10;
        int price = quantity*(cartItem.getOption().getPrice());

        // when
        cartItem.update(quantity, price);
        Cart updateCartItem = cartJPARepository.findById(3);
        //String responseBody = om.writeValueAsString(updateCartItem);
        //System.out.println("테스트 : "+responseBody);

        // then
        assertThat(updateCartItem.getQuantity()).isEqualTo(quantity);
        assertThat(updateCartItem.getPrice()).isEqualTo(price);
    }

    // 카트 id로 카트에서 삭제하는 test
    @Test
    public void cart_delete_test() throws JsonProcessingException {

        // given
        int id = 3;

        // when
        cartJPARepository.deleteById(id);

        // then
        assertThat(cartJPARepository.count()).isEqualTo(2);
    }

}
