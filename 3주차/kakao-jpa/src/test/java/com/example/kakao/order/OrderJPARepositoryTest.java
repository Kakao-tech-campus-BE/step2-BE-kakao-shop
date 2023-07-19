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
import com.example.kakao.user.UserJPARepositoryTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private ItemJPARepository itemJPARepository;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        User user = newUser("ssar");
        userJPARepository.save(user);

        Cart dummyCart1 = newCart(user,optionListPS.get(0),5);
        Cart dummyCart2 = newCart(user,optionListPS.get(1),5);

        Order orderPS = newOrder(user);
        orderJPARepository.save(orderPS);
        Item dummyItem1 = newItem(dummyCart1,orderPS);
        Item dummyItem2 = newItem(dummyCart2,orderPS);


        itemJPARepository.save(dummyItem1);
        itemJPARepository.save(dummyItem2);

        em.clear();
        System.out.println("-------------------------------------");


    }
    @Test
    public void order_save_test(){

        // given

        User user = newUser("test");
        userJPARepository.save(user);
        Cart cartA = newCart(user,optionJPARepository.getReferenceById(3),10);
        Cart cartB = newCart(user,optionJPARepository.getReferenceById(4),10);
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
        Assertions.assertThat(order.getUser().getUsername()).isEqualTo("test");
        Assertions.assertThat(orders.size()).isEqualTo(2);
    }

    @Test
    public void find_order_by_id_test(){
        // given
        int userId = 1;

        // when
        Optional<Order> order = orderJPARepository.findById(userId);
        List<Item> itemList = itemJPARepository.findByOrder_Id(order.get().getId());
        itemList.forEach(System.out::println);

        // then
        Assertions.assertThat(order.get().getId()).isEqualTo(1);
    }

}