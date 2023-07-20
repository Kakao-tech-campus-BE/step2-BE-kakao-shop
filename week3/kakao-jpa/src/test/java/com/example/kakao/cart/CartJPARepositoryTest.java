package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DataJpaTest
class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private EntityManager em;

    final private int DEFAULT_CART_OPTION_ID_1 = 1;
    final private int DEFAULT_CART_OPTION_ID_2 = 2;
    final private int DEFAULT_CART_QUANTITY_1 = 4;
    final private int DEFAULT_CART_QUANTITY_2 = 7;



    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        User user = newUser("ssar");
        userJPARepository.save(user);

        Option option1 = em.getReference(Option.class, DEFAULT_CART_OPTION_ID_1);
        Option option2 = em.getReference(Option.class, DEFAULT_CART_OPTION_ID_2);
        cartJPARepository.save(newCart(user, option1, DEFAULT_CART_QUANTITY_1));
        cartJPARepository.save(newCart(user, option2, DEFAULT_CART_QUANTITY_2));
        em.clear();
    }


    /**
     * cart 확인 시 option의 내용이 필요함
     * LAZY FETCH 정책 상에서, option 내용 접근 시 SELECT query가 하나 더 날아감(비추천)
     */
    @DisplayName("장바구니 조회 테스트(findAllByUserId) - LAZY FETCH 로 추가 쿼리 발생")
    @Test
    public void cart_findAllByUserId_test() {
        // given
        int userId = 1;

        // when
        List<Cart> carts = cartJPARepository.findAllByUserId(userId);

        System.out.println("option1 가격 가져오기");
        int option1Price = carts.get(0).getOption().getPrice();
        System.out.println("option2 가격 가져오기");
        int option2Price = carts.get(1).getOption().getPrice();
        // then
        Assertions.assertThat(carts.size()).isEqualTo(2);
        Assertions.assertThat(carts.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(carts.get(0).getOption().getId()).isEqualTo(DEFAULT_CART_OPTION_ID_1);
        Assertions.assertThat(carts.get(0).getQuantity()).isEqualTo(DEFAULT_CART_QUANTITY_1);
        Assertions.assertThat(carts.get(0).getPrice()).isEqualTo(option1Price * DEFAULT_CART_QUANTITY_1);
        Assertions.assertThat(carts.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(carts.get(1).getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(carts.get(1).getOption().getId()).isEqualTo(DEFAULT_CART_OPTION_ID_2);
        Assertions.assertThat(carts.get(1).getQuantity()).isEqualTo(DEFAULT_CART_QUANTITY_2);
        Assertions.assertThat(carts.get(1).getPrice()).isEqualTo(option2Price * DEFAULT_CART_QUANTITY_2);

    }

    /**
     * cart 확인 시 option의 내용이 필요함
     * JOIN FETCH로 한번의 query로 가져옴(Query 한번으로 되기 때문에 더 권장)
     */
    @DisplayName("장바구니 조회 테스트(mFindAllByUserId) - JOIN FETCH 사용")
    @Test
    public void cart_mFindAllByUserId_test() {
        // given
        int userId = 1;
        // when
        List<Cart> carts = cartJPARepository.mFindAllByUserId(userId);

        System.out.println("option1 가격 가져오기");
        int option1Price = carts.get(0).getOption().getPrice();
        System.out.println("option2 가격 가져오기");
        int option2Price = carts.get(1).getOption().getPrice();
        // then
        Assertions.assertThat(carts.size()).isEqualTo(2);
        Assertions.assertThat(carts.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(carts.get(0).getOption().getId()).isEqualTo(DEFAULT_CART_OPTION_ID_1);
        Assertions.assertThat(carts.get(0).getQuantity()).isEqualTo(DEFAULT_CART_QUANTITY_1);
        Assertions.assertThat(carts.get(0).getPrice()).isEqualTo(option1Price * DEFAULT_CART_QUANTITY_1);
        Assertions.assertThat(carts.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(carts.get(1).getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(carts.get(1).getOption().getId()).isEqualTo(DEFAULT_CART_OPTION_ID_2);
        Assertions.assertThat(carts.get(1).getQuantity()).isEqualTo(DEFAULT_CART_QUANTITY_2);
        Assertions.assertThat(carts.get(1).getPrice()).isEqualTo(option2Price * DEFAULT_CART_QUANTITY_2);

    }


    @DisplayName("장바구니 담기 테스트")
    @Test
    public void cart_save_test() {
        // given
        int userId = 1;
        int optionId = 3; // setup 으로 입력한 DEFAULT_CART_OPTION_ID 와 달라야함
        int quantity = 3;
        // when
        Option option = em.getReference(Option.class, optionId);
        User user = em.getReference(User.class, userId);

        cartJPARepository.save(newCart(user, option, quantity));

        // then
        em.clear();

        List<Cart> carts = cartJPARepository.findAllByUserId(userId); // 잘 저장됐는지 다시 select 하여 확인

        Assertions.assertThat(carts.size()).isEqualTo(3);
        Assertions.assertThat(carts.get(2).getId()).isEqualTo(3);
        Assertions.assertThat(carts.get(2).getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(carts.get(2).getOption().getId()).isEqualTo(optionId);
        Assertions.assertThat(carts.get(2).getQuantity()).isEqualTo(quantity);
        Assertions.assertThat(carts.get(2).getPrice()).isEqualTo(option.getPrice() * quantity);
    }

    /**
     * update를 위해 price를 가져와야해서 option 내부에 접근하게 되고
     * select query가 update해야하는 옵션들의 개수만큼 추가로 사용된다.
     */
    @DisplayName("장바구니 수정 테스트(findAllByUserIdAndOptionIdIn) - 추가 query 발생")
    @Test
    public void cart_update_test() {
        // given
        int userId = 1;
        Map<Integer, Integer> requestOptionsQuantities = new HashMap<>();

        requestOptionsQuantities.put(DEFAULT_CART_OPTION_ID_1, 3);
        requestOptionsQuantities.put(DEFAULT_CART_OPTION_ID_2, 1);

        // when

        List<Cart> carts = cartJPARepository.findAllByUserIdAndOptionIdIn(userId, new ArrayList<>(requestOptionsQuantities.keySet()));

        carts.forEach(cart -> {
            System.out.println("업데이트");
            int updateQuantity = requestOptionsQuantities.get(cart.getOption().getId());
            cart.update(updateQuantity, updateQuantity * cart.getOption().getPrice());
        });
        em.flush();

        // then
        em.clear();
        List<Cart> updatedCarts = cartJPARepository.findAllByUserId(userId);
        Assertions.assertThat(updatedCarts.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(updatedCarts.get(0).getOption().getId()).isEqualTo(DEFAULT_CART_OPTION_ID_1);
        Assertions.assertThat(updatedCarts.get(0).getQuantity()).isEqualTo(3);
        Assertions.assertThat(updatedCarts.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(updatedCarts.get(1).getOption().getId()).isEqualTo(DEFAULT_CART_OPTION_ID_2);
        Assertions.assertThat(updatedCarts.get(1).getQuantity()).isEqualTo(1);

    }

    /**
     * 처음부터 option 내부까지 들고와 추가 select query가 발생하지 않는다.
     */
    @DisplayName("장바구니 수정 테스트(findAllByUserIdAndOptionIdIn) - JOIN FETCH 사용")
    @Test
    public void cart_updateJoinFetch_test() {
        // given
        int userId = 1;
        Map<Integer, Integer> requestOptionsQuantities = new HashMap<>();

        requestOptionsQuantities.put(DEFAULT_CART_OPTION_ID_1, 3);
        requestOptionsQuantities.put(DEFAULT_CART_OPTION_ID_2, 1);

        // when

        List<Cart> carts = cartJPARepository.mFindAllByUserIdAndOptionIdIn(userId, new ArrayList<>(requestOptionsQuantities.keySet()));

        carts.forEach(cart -> {
            System.out.println("업데이트");
            int updateQuantity = requestOptionsQuantities.get(cart.getOption().getId());
            cart.update(updateQuantity, updateQuantity * cart.getOption().getPrice());
        });
        em.flush();

        // then
        em.clear();
        List<Cart> updatedCarts = cartJPARepository.findAllByUserId(userId);
        Assertions.assertThat(updatedCarts.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(updatedCarts.get(0).getOption().getId()).isEqualTo(DEFAULT_CART_OPTION_ID_1);
        Assertions.assertThat(updatedCarts.get(0).getQuantity()).isEqualTo(3);
        Assertions.assertThat(updatedCarts.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(updatedCarts.get(1).getOption().getId()).isEqualTo(DEFAULT_CART_OPTION_ID_2);
        Assertions.assertThat(updatedCarts.get(1).getQuantity()).isEqualTo(1);

    }
}