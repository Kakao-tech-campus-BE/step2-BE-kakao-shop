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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    private final EntityManager em;

    private final CartJPARepository cartJPARepository;

    private final ObjectMapper om;

    private final UserJPARepository userJPARepository;

    private final OptionJPARepository optionJPARepository;

    private final ProductJPARepository productJPARepository;

    @Autowired
    public CartJPARepositoryTest (
            EntityManager em,
            CartJPARepository cartJPARepository,
            UserJPARepository userJPARepository,
            OptionJPARepository optionJPARepository,
            ObjectMapper om,
            ProductJPARepository productJPARepository) {
        this.em = em;
        this.cartJPARepository = cartJPARepository;
        this.userJPARepository = userJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.om = om;
        this.productJPARepository = productJPARepository;
    }

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        User ssar = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        cartJPARepository.saveAll(cartDummyList(ssar, optionListPS));
        em.clear();
    }

    @DisplayName("영속화 테스트")
    @Test
    public void join_test(){
        Cart cart = newCart(newUser("cos"), optionDummyList(productDummyList()).get(1), 1);
        System.out.println("영속화 되기 전 id : " + cart.getId());
        cartJPARepository.save(cart);
        System.out.println("영속화 된 후 id : " + cart.getId());
        assertEquals(3, cart.getId());
    }

    @DisplayName("데이터 삽입 테스트")
    @Test
    public void create_test() throws JsonProcessingException {
        int userId = 1;
        int optionId = 2;

        User user = userJPARepository.findById(userId).orElseThrow(RuntimeException::new);
        Option option = optionJPARepository.findById(optionId).orElseThrow(RuntimeException::new);

        Cart cart = newCart(user, option, 2);
        cartJPARepository.save(cart);
        String responseBody = om.writeValueAsString(cart);
        System.out.println(responseBody);
        assertEquals(3, cart.getId());
        assertEquals(2, cart.getQuantity());
        assertEquals(option.getPrice() * cart.getQuantity(), cart.getPrice());
        assertEquals(user.getId(), cart.getUser().getId());
        assertEquals(user.getEmail(), cart.getUser().getEmail());
        assertEquals(user.getPassword(), cart.getUser().getPassword());
        assertEquals(user.getUsername(), cart.getUser().getUsername());
        assertEquals(user.getRoles(), cart.getUser().getRoles());
        assertEquals(option.getId(), cart.getOption().getId());
        assertEquals(option.getOptionName(), cart.getOption().getOptionName());
        assertEquals(option.getPrice(), cart.getOption().getPrice());
        assertEquals(option.getProduct().getId(), cart.getOption().getProduct().getId());
        assertEquals(option.getProduct().getProductName(), cart.getOption().getProduct().getProductName());
        assertEquals(option.getProduct().getImage(), cart.getOption().getProduct().getImage());
        assertEquals(option.getProduct().getPrice(), cart.getOption().getProduct().getPrice());
    }

    @DisplayName("데이터 조회 테스트")
    @Test
    public void read_test() throws JsonProcessingException {
        int userId = 1;
        List<Cart> carts = cartJPARepository.mFindAllByUserId(userId);
        String result = om.writeValueAsString(carts);
        System.out.println(result);
    }

    @DisplayName("데이터 업데이트 테스트")
    @Test
    public void update_test(){
        int id = 1;
        int quantity = 10;

        Cart cart = cartJPARepository.findById(id).orElseThrow(RuntimeException::new);
        cart.update(quantity, cart.getPrice());

        assertEquals(quantity, cart.getQuantity());
    }
}
