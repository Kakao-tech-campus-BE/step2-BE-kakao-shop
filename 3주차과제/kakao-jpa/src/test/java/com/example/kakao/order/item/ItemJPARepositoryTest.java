package com.example.kakao.order.item;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.Order;
import com.example.kakao.order.OrderJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
public class ItemJPARepositoryTest extends DummyEntity {

    @Autowired
    private ItemJPARepository itemJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private EntityManager em;



    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User user = userJPARepository.save(newUser("user"));
        Order order = orderJPARepository.save(newOrder(user));
        Product product = productJPARepository.save(newProduct("product1", 1, 10000));
        Option option = optionJPARepository.save(newOption(product,"optionName",1000));
        Cart cart = cartJPARepository.save(newCart(user, option, 5));
        Item item = itemJPARepository.save(newItem(cart, order));

        em.clear();
    }

    @Test
    public void item_findByOrderId_test(){
        // given
        int orderId = 1;

        // when
        List<Item> items = itemJPARepository.findByOrderId(orderId);

        // then
        Assertions.assertThat(items.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(items.get(0).getPrice()).isEqualTo(1000*5);
        Assertions.assertThat(items.get(0).getQuantity()).isEqualTo(5);

        Assertions.assertThat(items.get(0).getOption().getId()).isEqualTo(1);

        Assertions.assertThat(items.get(0).getOrder().getId()).isEqualTo(1);
    }


}
