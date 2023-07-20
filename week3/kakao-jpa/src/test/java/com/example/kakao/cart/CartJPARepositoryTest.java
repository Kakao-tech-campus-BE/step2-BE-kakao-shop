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
import java.util.stream.Collectors;

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
                        newUser("ssal"),
                        newUser("no")
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
    public void cart_add_test() throws JsonProcessingException {
        // given
        int userId = 2;
        List<Integer> optionIds = Arrays.asList(100, 101);
        int q = 5;

        // when
        System.out.println("========================================테스트 시작=========================================");
        // 사용자 유효성 검사
        User userPS = userJPARepository.findById(userId).orElseThrow(
                () -> new RuntimeException("장바구니아이템 추가 테스트 실패 : 사용자를 찾을 수 없습니다")
        );
        // 옵션 유효성 검사
        optionIds.forEach(x-> {
            if (optionJPARepository.findById(x) == null) {
                throw new RuntimeException("장바구니아이템 추가 테스트 실패 : 옵션을 찾을 수 없습니다");
            }
        });
        // 장바구니아이템 유효성 검사
        List<Cart> cartListPS = cartJPARepository.mFindByUserId(userId);
        cartListPS.forEach(x-> {
            optionIds.forEach(y-> {
                if (x.getOption().getId() == y) {
                    throw new RuntimeException("장바구니아이템 추가 테스트 실패 : 이미 장바구니에 담았습니다");
                }
            });
        });
        // 장바구니아이템 추가
//        cartJPARepository.saveAll(optionListPS.stream()
//                        .map(x->newCart(userPS, x, q))
//                        .collect(Collectors.toList())
//        );

        em.flush(); // PC 지우지 않고 DB에 반영

        // then
        System.out.println("========================================테스트 결과=========================================");
        Cart cartPS = cartJPARepository.findById(6).orElseThrow();

        Assertions.assertThat(cartPS.getOption().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(cartPS.getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(cartPS.getId()).isEqualTo(6);
        Assertions.assertThat(cartPS.getOption().getId()).isEqualTo(3);
        Assertions.assertThat(cartPS.getOption().getOptionName()).isEqualTo("고무장갑 베이지 S(소형) 6팩");
        Assertions.assertThat(cartPS.getOption().getPrice()).isEqualTo(9900);
        Assertions.assertThat(cartPS.getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartPS.getPrice()).isEqualTo(49500);
    }

    @DisplayName("전체 장바구니아이템 조회 테스트")
    @Test
    public void cart_mFindByUserId_test() throws JsonProcessingException {
        // given
        int userId = 2;

        // when
        System.out.println("========================================테스트 시작=========================================");
        // 사용자 유효성 검사
        userJPARepository.findById(userId).orElseThrow(
                () -> new RuntimeException("전체 장바구니아이템 조회 테스트 실패 : 사용자를 찾을 수 없습니다")
        );
        // 장바구니아이템 유효성 조회
        List<Cart> cartListPS = cartJPARepository.mFindByUserId(userId);
        // 장바구니아이템 유효성 검사
        if (cartListPS.isEmpty()) {
             throw new RuntimeException("전체 장바구니아이템 조회 테스트 실패 : 장바구니가 비어있습니다");
        }

        // then
        System.out.println("========================================테스트 결과=========================================");
        String responseBody = om.writeValueAsString(cartListPS);
        System.out.println(getPrettyString(responseBody));

        Assertions.assertThat(cartListPS.get(0).getOption().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(cartListPS.get(0).getId()).isEqualTo(4);
        Assertions.assertThat(cartListPS.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartListPS.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(cartListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartListPS.get(0).getPrice()).isEqualTo(50000);
    }

    @DisplayName("장바구니아이템 수량 수정 테스트")
    @Test
    public void cart_update_test() {
        // given
        int userId = 2;
        int id = 4;
        int q = 10;

        // when
        System.out.println("========================================테스트 시작=========================================");
        // 사용자 유효성 검사
        userJPARepository.findById(userId).orElseThrow(
                () -> new RuntimeException("장바구니아이템 수량 수정 테스트 실패 : 사용자를 찾을 수 없습니다")
        );
        // 장바구니아이템 유효성 검사
        Cart cartPS = cartJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("장바구니아이템 수량 수정 테스트 실패 : 장바구니아이템을 찾을 수 없습니다")
        );
        // 장바구니아이템 수량 수정
        cartPS.update(q, cartPS.getOption().getPrice()*q);

        em.flush(); // PC 지우지 않고 DB에 반영

        // then
        System.out.println("========================================테스트 결과=========================================");
        Cart updatedCart = cartJPARepository.findById(4).orElseThrow();

        Assertions.assertThat(updatedCart.getId()).isEqualTo(4);
        Assertions.assertThat(updatedCart.getOption().getId()).isEqualTo(1);
        Assertions.assertThat(updatedCart.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(updatedCart.getQuantity()).isEqualTo(10);
        Assertions.assertThat(updatedCart.getPrice()).isEqualTo(100000);
    }

}