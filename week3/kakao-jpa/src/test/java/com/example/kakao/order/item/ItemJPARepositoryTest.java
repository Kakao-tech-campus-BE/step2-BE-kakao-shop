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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
class ItemJPARepositoryTest extends DummyEntity {
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final ProductJPARepository productJPARepository;
    private final UserJPARepository userJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final EntityManager em;
    private final ObjectMapper om;

    @Autowired
    public ItemJPARepositoryTest(CartJPARepository cartJPARepository, OptionJPARepository optionJPARepository, ProductJPARepository productJPARepository, UserJPARepository userJPARepository, ItemJPARepository itemJPARepository, OrderJPARepository orderJPARepository, EntityManager em, ObjectMapper om) {
        this.cartJPARepository = cartJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.productJPARepository = productJPARepository;
        this.userJPARepository = userJPARepository;
        this.itemJPARepository = itemJPARepository;
        this.orderJPARepository = orderJPARepository;
        this.em = em;
        this.om = om;
    }

    @BeforeEach
    public void setUp(){
        User user = userJPARepository.save(newUser("rhalstjr1999"));
        List<Product> products = productJPARepository.saveAll(productDummyList());
        List<Option> options = optionJPARepository.saveAll(optionDummyList(products));
        List<Cart> carts = cartDummys(user, options);

        cartJPARepository.saveAll(carts);

        Order order = orderJPARepository.save(newOrder(user));

        List<Item> items = itemDummys(carts, order);
        itemJPARepository.saveAll(items);

        em.clear();
    }

    @AfterEach
    public void resetIndex() {
        cartJPARepository.deleteAll();
        userJPARepository.deleteAll();
        productJPARepository.deleteAll();
        optionJPARepository.deleteAll();
        orderJPARepository.deleteAll();
        itemJPARepository.deleteAll();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();

        em.clear();
    }

    @Test
    void 주문_아이템_등록_테스트() {
        //given
        int id = 1;
        //User ID가 계속 1로 생성되는 기존 로직 때문에 주문과 아이템 테이블을 비우고 등록 시작
        orderJPARepository.deleteAll();
        itemJPARepository.deleteAll();

        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();

        List<Cart> carts = cartJPARepository.findByUserId(id).orElseThrow(
                () -> new RuntimeException("해당 유저의 장바구니는 비어있습니다.")
        );

        User user = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 유저를 찾을 수 없습니다.")
        );

        //when
        Order order = orderJPARepository.save(newOrder(user));

        List<Item> items = itemDummys(carts, order);
        itemJPARepository.saveAll(items);

        //then
        List<Item> searchItems = itemJPARepository.findByOrderId(user.getId());

        Assertions.assertThat(items).isEqualTo(searchItems);
    }

    @Test
    void 주문_아이템_조회_테스트() throws JsonProcessingException {
        //given
        int id = 1;

        //when
        List<Item> items = itemJPARepository.findByOrderId(id);

        System.out.println("============JSON 직렬화============");
        String responseBody = om.writeValueAsString(items);
        System.out.println("테스트 : " + responseBody);
    }
}