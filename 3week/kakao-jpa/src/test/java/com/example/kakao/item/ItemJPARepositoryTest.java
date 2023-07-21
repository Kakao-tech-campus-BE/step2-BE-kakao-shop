package com.example.kakao.item;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.Order;
import com.example.kakao.order.OrderJPARepository;
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

@DataJpaTest
public class ItemJPARepositoryTest extends DummyEntity {

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

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @BeforeEach
    public void setUp(){

        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User user = newUser("sonny");
        userJPARepository.save(user);

        Product product = newProduct("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000);
        productJPARepository.save(product);

        Option option1 = newOption(product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = newOption(product, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        optionJPARepository.save(option1);
        optionJPARepository.save(option2);

        Cart cart1 = newCart(user, option1, 10);
        Cart cart2 = newCart(user, option2, 10);

        List<Cart> carts = List.of(cart1,cart2);
        cartJPARepository.saveAll(carts);

        Order order = newOrder(user);
        Order savedOrder = orderJPARepository.save(order);

        itemJPARepository.save(newItem(cart1,savedOrder));
        itemJPARepository.save(newItem(cart2,savedOrder));

        em.clear();// 준영속 상태로 만듬 -> 쿼리 보기 위해서

    }

    @Test
    public void item_findById_test(){
        int orderId = 1;

        // when
        List<Item> items = itemJPARepository.findItemsByOrderId(orderId);

        // then
        Assertions.assertThat(items.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(items.get(0).getPrice()).isEqualTo(10000*10);
        Assertions.assertThat(items.get(0).getQuantity()).isEqualTo(10);

        Assertions.assertThat(items.get(0).getOption().getId()).isEqualTo(1);

        Assertions.assertThat(items.get(0).getOrder().getId()).isEqualTo(1);
    }







}
