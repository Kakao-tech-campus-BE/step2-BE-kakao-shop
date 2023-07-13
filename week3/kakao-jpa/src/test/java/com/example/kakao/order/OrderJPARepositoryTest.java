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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Import(ObjectMapper.class)
@DataJpaTest
class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private ItemJPARepository itemJPARepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        //상품 저장 및 장바구니 담기
        User user = userJPARepository.save(newUser("rhalstjr1999"));
        List<Product> products = productJPARepository.saveAll(productDummyList());
        List<Option> options = optionJPARepository.saveAll(optionDummyList(products));
        List<Cart> carts = Arrays.asList(
                newCart(user, options.get(0), 5),
                newCart(user, options.get(1), 5)
        );

        cartJPARepository.saveAll(carts);
        //장바구니 상품 주문
        int id = 1;

        List<Cart> searchCarts = cartJPARepository.findByUserId(id).orElseThrow(
                () -> new RuntimeException("해당 유저의 장바구니는 비어있습니다.")
        );

        User searchUser = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 유저를 찾을 수 없습니다.")
        );

        Order saveOrder = newOrder(user);
        orderJPARepository.save(saveOrder);

        Order order = orderJPARepository.findByUserId(id);

        List<Item> items = Arrays.asList(
                newItem(searchCarts.get(0), order),
                newItem(searchCarts.get(1), order)
        );

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
    void 주문서_등록_테스트() throws JsonProcessingException {
        //given
        int id = 1;

        List<Cart> carts = cartJPARepository.findByUserId(id).orElseThrow(
                () -> new RuntimeException("해당 유저의 장바구니는 비어있습니다.")
        );

        User user = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 유저를 찾을 수 없습니다.")
        );

        //when
        Order saveOrder = newOrder(user);
        orderJPARepository.save(saveOrder);

        Order order = orderJPARepository.findByUserId(id);

        List<Item> items = Arrays.asList(
                newItem(carts.get(0), order),
                newItem(carts.get(1), order)
        );

        itemJPARepository.saveAll(items);

        List<Item> search = itemJPARepository.findByOrderId(1);

        System.out.println("============JSON 직렬화============");
        String responseBody = om.writeValueAsString(search);
        System.out.println("테스트 : " + responseBody);
    }

    @Test
    void 주문서_조회_테스트() throws JsonProcessingException {
        //given
        int id = 1;

        //when
        List<Item> items = itemJPARepository.findByOrderId(id);

        System.out.println("============JSON 직렬화============");
        String responseBody = om.writeValueAsString(items);
        System.out.println("테스트 : " + responseBody);

        //then
    }
}