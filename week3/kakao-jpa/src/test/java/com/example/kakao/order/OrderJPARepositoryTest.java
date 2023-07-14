package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.example.kakao.cart.Cart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.kakao._core.utils.PrintUtils.getPrettyString;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        System.out.println("========================================setUp 시작=========================================");
        // user 더미데이터 생성
        List<User> userPS = userJPARepository.saveAll(Arrays.asList(
                        newUser("eunjin"),
                        newUser("ssal")
                )
        );
        // product 더미데이터 생성
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        // option 더미데이터 생성
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        // cart 더미데이터 생성
        List<Cart> cartListPS = cartJPARepository.saveAll(Arrays.asList(
                        newCart(userPS.get(0), optionListPS.get(0), 5),
                        newCart(userPS.get(0), optionListPS.get(1), 5),
                        newCart(userPS.get(0), optionListPS.get(2), 5),
                        newCart(userPS.get(1), optionListPS.get(0), 10),
                        newCart(userPS.get(1), optionListPS.get(1), 10)
                )
        );
        // order 더미데이터 생성
        Order orderPS = orderJPARepository.save(newOrder(userPS.get(1)));
        // item 더미데이터 생성
        itemJPARepository.saveAll(Arrays.asList(
                        newItem(cartListPS.get(3), orderPS),
                        newItem(cartListPS.get(4), orderPS)
                )
        );
        // cart 삭제?

        em.clear(); // PC 지우고 DB에 반영
    }

    @AfterEach
    public void resetIndex() {
        System.out.println("========================================resetIndex 시작=========================================");
        userJPARepository.deleteAll();
        productJPARepository.deleteAll();
        optionJPARepository.deleteAll();
        cartJPARepository.deleteAll();
        orderJPARepository.deleteAll();
        itemJPARepository.deleteAll();

        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.clear(); // PC 지우고 DB에 반영
        System.out.println("========================================테스트 종료=========================================");
    }

    @DisplayName("주문 추가 테스트")
    @Test
    public void order_add_test() {
        // given
        int userId = 1;

        // when
        System.out.println("========================================테스트 시작=========================================");
        // 사용자 유효성 검증
        User userPS = userJPARepository.findById(userId).orElseThrow(
                () -> new RuntimeException("주문 추가 테스트 실패 : 사용자를 찾을 수 없습니다")
        );
        // 장바구니아이템 유효성 검증
        List<Cart> cartListPS = cartJPARepository.mfindByUserId(userId);
        if (cartListPS.isEmpty()) {
            throw new RuntimeException("주문 추가 테스트 실패 : 장바구니아이템을 찾을 수 없습니다");
        }
        // 주문 추가
        Order orderPS = orderJPARepository.save(newOrder(userPS));
        // 주문아이템 추가
        itemJPARepository.saveAll(Arrays.asList(
                        newItem(cartListPS.get(0), orderPS),
                        newItem(cartListPS.get(1), orderPS),
                        newItem(cartListPS.get(2), orderPS)
                )
        );

        // 카트 비우기 진행?

        em.flush(); // PC 지우지 않고 DB에 반영

        // then
    }

    @DisplayName("특정 주문 조회 테스트")
    @Test
    public void item_mFindByOrderId_test() throws JsonProcessingException {
        // given
        int userId = 2;
        int orderId = 1;

        // when
        System.out.println("========================================테스트 시작=========================================");
        // 사용자 유효성 검사
        User userPS = userJPARepository.findById(userId).orElseThrow(
                () -> new RuntimeException("특정 주문 조회 테스트 실패 : 사용자를 찾을 수 없습니다")
        );
        // 주문 유효성 검사
        Order orderPS = orderJPARepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("특정 주문 조회 테스트 실패 : 주문을 찾을 수 없습니다")
        );
        // 주문아이템 조회
        List<Item> itemListPS = itemJPARepository.mfindByOrderId(orderId);

        System.out.println("========================================테스트 결과=========================================");
        String responseBody = om.writeValueAsString(itemListPS);
        System.out.println(getPrettyString(responseBody));

        // then
    }
}