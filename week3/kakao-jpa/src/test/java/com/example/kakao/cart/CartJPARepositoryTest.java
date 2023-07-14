package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
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
public class CartJPARepositoryTest extends DummyEntity {

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
        cartJPARepository.saveAll(Arrays.asList(
                newCart(userPS.get(0), optionListPS.get(0), 5),
                newCart(userPS.get(0), optionListPS.get(1), 5),
                newCart(userPS.get(0), optionListPS.get(2), 5),
                newCart(userPS.get(1), optionListPS.get(0), 5),
                newCart(userPS.get(1), optionListPS.get(1), 5)
                )
        );

        em.clear(); // PC 지우고 DB에 반영
    }

    @AfterEach
    public void resetIndex() {
        System.out.println("========================================resetIndex 시작=========================================");
        userJPARepository.deleteAll();
        productJPARepository.deleteAll();
        optionJPARepository.deleteAll();
        cartJPARepository.deleteAll();

        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.clear(); // PC 지우고 DB에 반영
        System.out.println("========================================테스트 종료=========================================");
    }

    @DisplayName("장바구니아이템 추가 테스트")
    @Test
    public void cart_add_test() {
        // given
        int userId = 2;
        int optionId = 3;

        // when
        System.out.println("========================================테스트 시작=========================================");
        // 사용자 유효성 검사
        User userPS = userJPARepository.findById(userId).orElseThrow(
                () -> new RuntimeException("장바구니아이템 추가 테스트 실패 : 사용자를 찾을 수 없습니다")
        );
        // 옵션 유효성 검사
        Option optionPS = optionJPARepository.findById(optionId).orElseThrow(
                () -> new RuntimeException("장바구니아이템 추가 테스트 실패 : 옵션을 찾을 수 없습니다")
        );
        // 장바구니아이템 추가
        Cart cartPS = cartJPARepository.save(newCart(userPS, optionPS, 5));

        em.flush(); // PC 지우지 않고 DB에 반영

        // then
    }

    @DisplayName("전체 장바구니아이템 조회 테스트")
    @Test
    public void cart_mFindByUserId_test() throws JsonProcessingException {
        // given
        int userId = 2;

        // when
        System.out.println("========================================테스트 시작=========================================");
        // 사용자 유효성 검사
        User userPS = userJPARepository.findById(userId).orElseThrow(
                () -> new RuntimeException("전체 장바구니아이템 조회 테스트 실패 : 사용자를 찾을 수 없습니다")
        );
        // 장바구니아이템 조회 : empty일 수도 있음
        List<Cart> cartListPS = cartJPARepository.mfindByUserId(userId);

        System.out.println("========================================테스트 결과=========================================");
        String responseBody = om.writeValueAsString(cartListPS);
        System.out.println(getPrettyString(responseBody));

        // then
    }

    @DisplayName("장바구니아이템 수량 수정 테스트")
    @Test
    public void cart_update_test() {
        // given
        int userId = 2;
        int id = 4;

        // when
        System.out.println("========================================테스트 시작=========================================");
        // 사용자 유효성 검사
        User userPS = userJPARepository.findById(userId).orElseThrow(
                () -> new RuntimeException("장바구니아이템 수량 수정 테스트 실패 : 사용자를 찾을 수 없습니다")
        );
        // 장바구니아이템 유효성 검사
        Cart cartPS = cartJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("장바구니아이템 수량 수정 테스트 실패 : 장바구니아이템을 찾을 수 없습니다")
        );
        // 장바구니아이템 수정
        cartPS.update(10, 10*cartPS.getPrice());

        em.flush(); // PC 지우지 않고 DB에 반영

        // then
    }

}