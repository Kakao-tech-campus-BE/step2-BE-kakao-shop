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
public class OrderJPARepositoryTest extends DummyEntity {
    // 엔티티 관리
    @Autowired
    private EntityManager entityManager;
    // user JPA
    @Autowired
    private UserJPARepository userJPARepository;
    // option JPA
    @Autowired
    private OptionJPARepository optionJPARepository;
    // product JPA
    @Autowired
    private ProductJPARepository productJPARepository;
    // cart JPA
    @Autowired
    private CartJPARepository cartJPARepository;
    // order JPA
    @Autowired
    private OrderJPARepository orderJPARepository;
    // item JPA
    @Autowired
    private ItemJPARepository itemJPARepository;
    // 파싱
    @Autowired
    private ObjectMapper objectMapper;

    // setup
    @BeforeEach
    public void setUp(){
        // id 값 리셋
        entityManager.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        // 아이디
        User user = newUser("ssar");
        userJPARepository.save(user);

        // 물품
        List<Product> products = productDummyList();
        productJPARepository.saveAll(products);

        // 옵션
        List<Option> options = optionDummyList(products);
        optionJPARepository.saveAll(options);

        // 카트
        List<Cart> carts = cartDummyList(user, options);
        cartJPARepository.saveAll(carts);

        // order
        Order order = newOrder(user);
        orderJPARepository.save(order);

        // item
        List<Item> items = itemDummyList(carts, order);
        itemJPARepository.saveAll(items);

        // 영속성 컨텍스트 비우기
        entityManager.clear();
    }

    // 테스트 - 결재하기, 주문 목록 확인
    // 결재하기
    @Test
    void order_save_test() {
        // given
        int id = 1;

        // when
        List<Item> itemListPS = itemJPARepository.findByOrderId(id);

        System.out.println(itemListPS.get(0).getOption().getProduct().getProductName());
        // then ( 상태 검사 )

        // productName check
        Assertions.assertThat(itemListPS.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");

        // items - optionName check
        Assertions.assertThat(itemListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");

        // userName check
        Assertions.assertThat(itemListPS.get(0).getOrder().getUser().getUsername()).isEqualTo("ssar");
    }

}
