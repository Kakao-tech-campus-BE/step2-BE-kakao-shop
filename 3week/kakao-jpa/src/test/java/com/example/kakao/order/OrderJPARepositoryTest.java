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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private ItemJPARepository itemJPARepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        User user = userJPARepository.save(newUser("ssar"));

        Cart dummyCart1 = newCart(user,optionListPS.get(0),5);
        Cart dummyCart2 = newCart(user,optionListPS.get(1),5);

        Order orderPS = orderJPARepository.save(newOrder(user));

        Item dummyItem1 = newItem(dummyCart1,orderPS);
        Item dummyItem2 = newItem(dummyCart2,orderPS);

        itemJPARepository.save(dummyItem1);
        itemJPARepository.save(dummyItem2);
    }

    @Test
    public void order_save_test(){

        // given

        User user = newUser("cos");
        userJPARepository.save(user);
        Cart cartA = newCart(user,optionJPARepository.getReferenceById(4),10);
        Cart cartB = newCart(user,optionJPARepository.getReferenceById(5),10);

        Order order = newOrder(user);
        orderJPARepository.save(order);
        orderJPARepository.findAll().forEach(System.out::println);
        Item itemA = newItem(cartA,order);
        Item itemB = newItem(cartB,order);

        itemJPARepository.save(itemA);
        itemJPARepository.save(itemB);

        // when
        List<Order> orders = orderJPARepository.findAll();

        // then
        Assertions.assertThat(order.getUser().getUsername()).isEqualTo("cos");
        Assertions.assertThat(orders.size()).isEqualTo(1);
    }


    @Test
    public void order_test(){
        // given
        int userId = 1;

        // when
        Optional<Order> order = orderJPARepository.findById(userId);

        // then
        Assertions.assertThat(order.get().getId()).isEqualTo(1);
    }

}
