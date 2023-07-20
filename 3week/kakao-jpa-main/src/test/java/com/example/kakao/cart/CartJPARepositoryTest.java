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

import javax.persistence.EntityManager;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        em.clear();
        // pk 초기화
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User userPS = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        cartJPARepository.save(newCart(userPS, optionListPS.get(4), 2));
        cartJPARepository.save(newCart(userPS, optionListPS.get(12), 3));
    }

    @Test
    public void cart_findAll_test() throws JsonProcessingException {
        // given

        // when
        List<Cart> cartListPS = cartJPARepository.findAll();
        String responseBody = om.writeValueAsString(cartListPS);
        System.out.println("test : " + responseBody);
    }

    @Test
    public void cart_findByUserId_lazy_error_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        System.out.println("find user : " + userJPARepository.findById(0));
        List<Cart> cartListPS = cartJPARepository.findByUserId(id);

        String responseBody = om.writeValueAsString(cartListPS);
        System.out.println("test : " + responseBody);
    }

    @Test
    public void cart_mFindByUserId_lazy_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Cart> cartListPS = cartJPARepository.mFindByUserId(id);

        String responseBody = om.writeValueAsString(cartListPS);
        System.out.println("test : " + responseBody);
    }

    @Test
    public void cart_add_test() throws JsonProcessingException {
        // given
        int userId = 1;
        int optionId = 3;

        // when
        Cart cartPS = cartJPARepository.save(newCart(userJPARepository.getReferenceById(userId),
                optionJPARepository.getReferenceById(optionId), 2));
        String responseBody = om.writeValueAsString(cartPS);
        System.out.println("test : " + responseBody);

        // then
        Assertions.assertThat(cartPS.getUser().getId()).isEqualTo(1);
        Assertions.assertThat(cartPS.getId()).isEqualTo(3);
        Assertions.assertThat(cartPS.getQuantity()).isEqualTo(2);
        em.clear();
    }

    // 옵션 중복 오류
    @Test
    public void cart_add_duplicated_error_test(){
        // given
        int userId = 1;
        // 옵션 아이디 중복 에러
        int optionId = 5;

        // when
        try {
            // insert 오류 발생
            Cart cartPS = cartJPARepository.save(newCart(userJPARepository.getReferenceById(userId),
                    optionJPARepository.getReferenceById(optionId), 5));
            String responseBody = om.writeValueAsString(cartPS);
            System.out.println("test : " + responseBody);
            em.clear();
        }catch (Exception ex){
            System.out.println("Product is already in the cart");
        }
    }

    @Test
    public void cart_update_test() throws JsonProcessingException {
        // given
        // user id로 찾는 것이 아닌 cart id로 찾는 것이 낫나요?
        int id = 1;

        // when
        Cart cartPS = cartJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cart not found")
        );

        cartPS.update(5, cartPS.getPrice());
        Cart cartSavePS = cartJPARepository.save(cartPS);
        String responseBody = om.writeValueAsString(cartSavePS);
        System.out.println(responseBody);

        // then
        Assertions.assertThat(cartJPARepository.getReferenceById(id).getQuantity()).isEqualTo(5);
        em.clear();
    }
}
