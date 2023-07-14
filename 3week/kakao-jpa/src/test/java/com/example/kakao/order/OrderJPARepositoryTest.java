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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import static com.example.kakao._core.utils.PrintUtils.getPrettyString;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @BeforeEach //test 실행 전 호출 -> 백업
    public void setup() {
        //Item 새로 만들기 -> cart와 order필요
        User userSP = userJPARepository.save(newUser("chaewon"));
        List<Product> productsSP = productJPARepository.saveAll(productDummyList());
        List<Option> optionsSP = optionJPARepository.saveAll(optionDummyList(productsSP));
        List<Cart> cartsSP = Arrays.asList(
                newCart(userSP, optionsSP.get(0), 1),
                newCart(userSP, optionsSP.get(1), 3)
        );
        cartJPARepository.saveAll(cartsSP);

        //order 새로 만들기 -> 필요한 매개변수 user
        Order orderSP = orderJPARepository.save(newOrder(userSP));
        List<Item> itemSP= Arrays.asList(
                newItem(cartsSP.get(0),orderSP),
                newItem(cartsSP.get(1),orderSP)
        );
        itemJPARepository.saveAll(itemSP);
        em.clear();
        System.out.println("----TEST 시작----");
    }

    @DisplayName("Order & ITEM 생성 테스트")
    @Test
    public void orderItem_add_test() throws JsonProcessingException {
        //given
        User user = userJPARepository.save(newUser("won"));
        List<Product> products = productJPARepository.saveAll(productDummyList());
        List<Option> options = optionJPARepository.saveAll(optionDummyList(products));
        List<Cart> carts = Arrays.asList(
                newCart(user, options.get(0), 11),
                newCart(user, options.get(1), 13)
        );
        cartJPARepository.saveAll(carts);

        //when
        System.out.println("영속화-------");
        Order order = orderJPARepository.save(newOrder(user));
        List<Item> items= Arrays.asList(
                newItem(carts.get(0),order),
                newItem(carts.get(1),order)
        );
        itemJPARepository.saveAll(items);

        int id=2;
        List<Item> findByOrderIdItems = itemJPARepository.findByOrderId(id);
        //then
        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(findByOrderIdItems);
        System.out.println("테스트 : "+getPrettyString(responseBody));
    }
    @DisplayName("Order 조회 테스트")
    @Test
    public void orderItem_read_test() throws JsonProcessingException {
        //given
        int id=1;
        //when
        List<Item> items = itemJPARepository.findByOrderId(id);
        //then
        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(items);
        System.out.println("테스트 : "+getPrettyString(responseBody));

        Assertions.assertThat(items.get(0).getQuantity()).isEqualTo(1);
        Assertions.assertThat(items.get(1).getQuantity()).isEqualTo(3);
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
        System.out.println("----TEST 끝----");
    }
}
