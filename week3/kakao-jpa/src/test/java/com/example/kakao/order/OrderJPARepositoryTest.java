package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    OrderJPARepository orderJPARepository;

    @Autowired
    ItemJPARepository itemJPARepository;

    @Autowired
    ProductJPARepository productJPARepository;

    @Autowired
    OptionJPARepository optionJPARepository;

    @Autowired
    UserJPARepository userJPARepository;


    @Autowired
    EntityManager em;


    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));

        User user = userJPARepository.save(newUser("ssar"));
        Order order = orderJPARepository.save(newOrder(user));
        itemJPARepository.save(newItem(newCart(user, optionListPS.get(0), 3), order));
        itemJPARepository.save(newItem(newCart(user, optionListPS.get(1), 2), order));
        em.clear();
    }

    /**
     * 유저 아이디 기반 order 조회 -> order item 조회
     * orderId들을 찾고 해당하는 order item 들을 찾는다.
     */
    @DisplayName("주문 내역들 가져오기 - order repository 사용")
    @Test
    public void order_findAll_test() {
        // given
        int userId = 1;

        // when
        List<Order> orders = orderJPARepository.findAllByUserId(userId);

        List<Item> items = new ArrayList<>();
        orders.forEach(order -> {
            items.addAll(itemJPARepository.findAllByOrderId(order.getId()));
        });

        items.forEach(item -> {
            // 옵션마다 다시 query를 보내게 된다.
            System.out.println("옵션이름: " + item.getOption().getOptionName());
            // 동일 상품의 욥션인 경우 첫번째에서 영속화되어 상품 select query는 다시 발생하지 않는다.
            System.out.println("상품이름: " + item.getOption().getProduct().getProductName());
        });

        // then
        Assertions.assertThat(orders.size()).isEqualTo(1);
        Assertions.assertThat(orders.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(orders.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(items.size()).isEqualTo(2);
        Assertions.assertThat(items.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(items.get(0).getQuantity()).isEqualTo(3);
        Assertions.assertThat(items.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(items.get(1).getQuantity()).isEqualTo(2);
    }

    /**
     * Item과 order를 join하여 userid로 item들을 바로 조회
     * item과 option도 fetch join하여 option까지 한번에 들고온다.
     * 각 item의 option의 product 정보의 경우 따로 추가 쿼리가 필요하다.
     */
    @DisplayName("주문 내역들 가져오기 - item repository 사용(fetch join)")
    @Test
    public void item_findAll_test() {
        // given
        int userId = 1;

        // when
        List<Item> items = itemJPARepository.mFindAllByUserId(userId);

        items.forEach(item -> {
            System.out.println("옵션이름: " + item.getOption().getOptionName());
            System.out.println("상품이름: " + item.getOption().getProduct().getProductName());
        });

        // then
        Assertions.assertThat(items.size()).isEqualTo(2);
        Assertions.assertThat(items.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(items.get(0).getQuantity()).isEqualTo(3);
        Assertions.assertThat(items.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(items.get(1).getQuantity()).isEqualTo(2);
    }

    @DisplayName("주문 내역 가져오기 - order repository 사용")
    @Test
    public void item_findByOrderId_test() {
        // given
        int orderId = 1;

        // when
        List<Item> items = itemJPARepository.findAllByOrderId(orderId);

        items.forEach(item -> {
            // 옵션의 경우 item find query에서 fetch join 되어 가져와 있다.
            System.out.println("옵션이름: " + item.getOption().getOptionName());
            // item -> option -> product 로 product의 정보를 얻기 위해서는 query를 보내게 된다.
            System.out.println("상품이름: " + item.getOption().getProduct().getProductName());
        });

        // then
        Assertions.assertThat(items.size()).isEqualTo(2);
        Assertions.assertThat(items.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(items.get(0).getQuantity()).isEqualTo(3);
        Assertions.assertThat(items.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(items.get(1).getQuantity()).isEqualTo(2);
    }

    @DisplayName("주문 내역 가져오기 - item repository 사용(fetch join)")
    @Test
    public void item_mFindByOrderId_test() {
        // given
        int orderId = 1;

        // when
        List<Item> items = itemJPARepository.mFindAllByOrderId(orderId);

        items.forEach(item -> {
            // 옵션의 경우 item find query에서 fetch join 되어 가져와 있다.
            System.out.println("옵션이름: " + item.getOption().getOptionName());
            System.out.println("상품이름: " + item.getOption().getProduct().getProductName());
        });

        // then
        Assertions.assertThat(items.size()).isEqualTo(2);
        Assertions.assertThat(items.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(items.get(0).getQuantity()).isEqualTo(3);
        Assertions.assertThat(items.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(items.get(1).getQuantity()).isEqualTo(2);
    }

    @DisplayName("주문 내역 저장 테스트")
    @Test
    public void order_save_test() {
        // given
        // 옵션 id, quantity
        List<Integer> cartItems = List.of(4, 7);
        List<Integer> cartItemsQuantities = List.of(7, 1);

        int userId = 1;
        User user = em.getReference(User.class, userId);

        // when
        Order order = orderJPARepository.save(newOrder(user));

        for (int i = 0; i < cartItems.size(); i++) {
            Option option = em.getReference(Option.class, cartItems.get(i));
            itemJPARepository.save(newItem(
                    newCart(user, option, cartItemsQuantities.get(i)),
                    order));
        }

        // then
        em.clear();
        List<Item> items = itemJPARepository.mFindAllByOrderId(2);
        Assertions.assertThat(items.size()).isEqualTo(2);
        Assertions.assertThat(items.get(0).getOption().getId()).isEqualTo(cartItems.get(0));
        Assertions.assertThat(items.get(0).getQuantity()).isEqualTo(cartItemsQuantities.get(0));
        Assertions.assertThat(items.get(1).getOption().getId()).isEqualTo(cartItems.get(1));
        Assertions.assertThat(items.get(1).getQuantity()).isEqualTo(cartItemsQuantities.get(1));
    }

}