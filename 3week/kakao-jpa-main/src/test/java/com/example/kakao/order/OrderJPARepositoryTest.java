package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
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
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {

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
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        // pk 초기화
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User userPS = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));

        Order orderPS = orderJPARepository.save(newOrder(userPS));
        Cart cartPS1 = cartJPARepository.save(newCart(userPS, optionListPS.get(4), 2));
        Cart cartPS2 = cartJPARepository.save(newCart(userPS, optionListPS.get(12), 3));
        itemJPARepository.save(newItem(cartPS1, orderPS));
        itemJPARepository.save(newItem(cartPS2, orderPS));
        cartJPARepository.delete(cartPS1);
        cartJPARepository.delete(cartPS2);
    }

    @Test
    public void order_findAll_test() throws JsonProcessingException {
        // given

        // when
        List<Item> itemListPS = itemJPARepository.findAll();
        String responseBody = om.writeValueAsString(itemListPS);
        System.out.println("test : " + responseBody);
        em.clear();
    }

    @Test
    public void order_findById_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        orderJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found")
        );
        List<Item> itemListPS = itemJPARepository.mFindByOrderId(id);
        String responseBody = om.writeValueAsString(itemListPS);
        System.out.println("test : " + responseBody);
        em.clear();
    }

    @Test
    public void order_save_test() throws JsonProcessingException {
        // given
        int userId = 1;
        int orderId = 1;
        Cart cartPS = cartJPARepository.save(newCart(userJPARepository.getReferenceById(userId),
                optionJPARepository.getReferenceById(2), 4));

        // when
        Item itemPS = itemJPARepository.save(newItem(cartPS,
                orderJPARepository.getReferenceById(orderId)));

        String responseBody = om.writeValueAsString(itemPS);
        System.out.println("test : " + responseBody);
        em.clear();
    }
}
