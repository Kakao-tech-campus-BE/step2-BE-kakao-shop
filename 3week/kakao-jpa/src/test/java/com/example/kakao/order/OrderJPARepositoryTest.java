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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ObjectMapper om;

    // 조회 테스트를 위해 2개의 item 데이터와 1개의 order 데이터 생성
    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));

        List<Integer> optionIds = Arrays.asList(4, 5);
        List<Option> optionListPS = optionJPARepository.findAllById(optionIds);

        User userPS = userJPARepository.save(newUser("ssar"));
        Order orderPS = orderJPARepository.save(newOrder(userPS));

        for (Option option : optionListPS) {
            Cart cartPS = cartJPARepository.save(newCart(userPS, option, 5));
            itemJPARepository.save(newItem(cartPS, orderPS));
        }


        em.clear();
    }

    // 주문 아이템 조회
    // fetch전략을 eager로 지정 시 네임드 쿼리인 findByOrderId를 사용해도 item과 매핑 관계를 가진 객체 정보를 모두 함꼐 가져옴
    @Test
    public void item_findByOrderId_eager_test() throws JsonProcessingException {
        // given
        int id = 1;

        Product productPS = productJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("상품을 찾을 수 없습니다")
        ); // option의 product 컬럼이 lazy fetch인 경우 해당 코드를 실행해야함

        User userPS = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("사용자를 찾을 수 없습니다")
        ); // order의 user 컬럼이 lazy fetch인 경우 해당 코드를 실행해야함

        // when
        List<Item> itemListPS = itemJPARepository.findByOrderId(id);

        String responseBody = om.writeValueAsString(itemListPS);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(itemListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOption().getId()).isEqualTo(4);
        Assertions.assertThat(itemListPS.get(0).getOption().getOptionName()).isEqualTo("뽑아쓰는 키친타올 130매 12팩");
        Assertions.assertThat(itemListPS.get(0).getOption().getPrice()).isEqualTo(16900);
        Assertions.assertThat(itemListPS.get(0).getOrder().getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOrder().getUser().getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOrder().getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(itemListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(itemListPS.get(0).getPrice()).isEqualTo(84500);
    }

    // 주문 아이템 조회
    // fetch전략을 lazy로 지정 시 네임드 쿼리인 findByOrderId를 사용하면 target 없는 option과 order의 프록시 객체만을 가지고 있게 됨
    // 이후 json 직렬화를 실행하게 되면 order와 option 프록시 객체를 초기화하는 속도보다 빨라 JsonProcessingException이 발생하게 된다.
    @Test
    public void item_findByOrderId_lazy_error_test() {
        // given
        int id = 1;

        // when & then
        List<Item> itemListPS = itemJPARepository.findByOrderId(id);

        Assertions.assertThatThrownBy(() -> om.writeValueAsString(itemListPS))
                .isInstanceOf(JsonProcessingException.class);
    }

    // 주문 아이템 조회
    // fetch전략을 lazy로 지정 시 네임드 쿼리인 findByOrderId를 사용하면 target 없는 option과 order의 프록시 객체만을 가지고 있게 됨
    // 그러므로 필요한 option과 order 객체를 미리 영속성 컨텍스트에 저장한 후 findByOrderId를 사용
    @Test
    public void item_findByOrderId_lazy_test() throws JsonProcessingException {
        // given
        int id = 1;

        List<Integer> optionIds = Arrays.asList(4, 5);
        List<Option> optionListPS = optionJPARepository.mFindAllById(optionIds);
        // option의 product 컬럼이 지연로딩일 때를 고려하여 jpql로 fetch join을 하는 mFindAllById를 작성하여 사용
        List<Order> orderListPS = orderJPARepository.mFindByUserId(id);
        // order의 user 컬럼이 지연로딩일 때를 고려하여 jpql로 fetch join을 하는 mFindAllByUserId를 작성하여 사용

        // when
        List<Item> itemListPS = itemJPARepository.findByOrderId(id);

        String responseBody = om.writeValueAsString(itemListPS);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(itemListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOption().getId()).isEqualTo(4);
        Assertions.assertThat(itemListPS.get(0).getOption().getOptionName()).isEqualTo("뽑아쓰는 키친타올 130매 12팩");
        Assertions.assertThat(itemListPS.get(0).getOption().getPrice()).isEqualTo(16900);
        Assertions.assertThat(itemListPS.get(0).getOrder().getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOrder().getUser().getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOrder().getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(itemListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(itemListPS.get(0).getPrice()).isEqualTo(84500);
    }

    // 주문 아이템 조회
    // fetch전략을 lazy로 지정했기 때문에 jqpl을 사용하여 option과 order 객체에 대하여 fetch join을 하는 mFindByOrderId를 작성하여 사용
    @Test
    public void item_mFindByOrderId_lazy_test() throws JsonProcessingException {
        // given
        int id = 1;

        Product productPS = productJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("상품을 찾을 수 없습니다")
        ); // option의 product 컬럼이 지연로딩일 때를 고려하여 product 객체를 영속성 컨텍스트에 미리 저장

        User UserPS = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("사용자를 찾을 수 없습니다")
        ); // order의 user 컬럼이 지연로딩일 때를 고려하여 user 객체를 영속성 컨텍스트에 미리 저장

        // when
        List<Item> itemListPS = itemJPARepository.mFindByOrderId(id);

        String responseBody = om.writeValueAsString(itemListPS);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(itemListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOption().getId()).isEqualTo(4);
        Assertions.assertThat(itemListPS.get(0).getOption().getOptionName()).isEqualTo("뽑아쓰는 키친타올 130매 12팩");
        Assertions.assertThat(itemListPS.get(0).getOption().getPrice()).isEqualTo(16900);
        Assertions.assertThat(itemListPS.get(0).getOrder().getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOrder().getUser().getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOrder().getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(itemListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(itemListPS.get(0).getPrice()).isEqualTo(84500);
    }

    // 주문 조회
    // fetch전략을 eager로 지정 시 네임드 쿼리인 findByUserId를 사용해도 order와 매핑 관계를 가진 객체 정보를 모두 함꼐 가져옴
    @Test
    public void order_findByUserId_eager_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Order> orderListPS = orderJPARepository.findByUserId(id);

        String responseBody = om.writeValueAsString(orderListPS);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(orderListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(orderListPS.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(orderListPS.get(0).getUser().getUsername()).isEqualTo("ssar");
    }

    // 주문 조회
    // fetch전략을 lazy로 지정 시 네임드 쿼리인 findByUserId를 사용하면 target 없는 user의 프록시 객체만을 가지고 있게 됨
    // 이후 json 직렬화를 실행하게 되면 user 프록시 객체를 초기화하는 속도보다 빨라 JsonProcessingException이 발생하게 된다.
    @Test
    public void order_findByOrderId_lazy_error_test() {
        // given
        int id = 1;

        // when & then
        List<Order> orderListPS = orderJPARepository.findByUserId(id);

        Assertions.assertThatThrownBy(() -> om.writeValueAsString(orderListPS))
                .isInstanceOf(JsonProcessingException.class);
    }

    // 주문 조회
    // fetch전략을 lazy로 지정했기 때문에 jqpl을 사용하여 user 객체에 대하여 fetch join을 하는 mFindByUserId 작성하여 사용
    @Test
    public void order_mFindByOrderId_lazy_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Order> orderListPS = orderJPARepository.mFindByUserId(id);

        String responseBody = om.writeValueAsString(orderListPS);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(orderListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(orderListPS.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(orderListPS.get(0).getUser().getUsername()).isEqualTo("ssar");
    }

    // 주문 생성 - user의 장바구니 들어있던 모든 상품을 주문하고 장바구니를 비움
    @Test
    public void order_save_test() throws JsonProcessingException {
        // given
        int id = 1;
        User userPS = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("사용자를 찾을 수 없습니다")
        );
        List<Cart> cartListPS = cartJPARepository.findByUserId(id);
        Order orderPS = orderJPARepository.save(newOrder(userPS));
        List<Item> itemListPS = new ArrayList<>();

        // when
        for (Cart cart : cartListPS) {
            itemListPS.add(newItem(cart,orderPS));
        }
        itemJPARepository.saveAll(itemListPS);
        cartJPARepository.deleteAllInBatch(cartListPS);
        em.flush();

        // then
        itemListPS = itemJPARepository.findByOrderId(2);

        Assertions.assertThat(itemListPS.get(0).getId()).isEqualTo(3);
        Assertions.assertThat(itemListPS.get(0).getOption().getId()).isEqualTo(4);
        Assertions.assertThat(itemListPS.get(0).getOrder().getId()).isEqualTo(2);
        Assertions.assertThat(itemListPS.get(0).getOrder().getUser().getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(itemListPS.get(0).getPrice()).isEqualTo(84500);
    }
}
