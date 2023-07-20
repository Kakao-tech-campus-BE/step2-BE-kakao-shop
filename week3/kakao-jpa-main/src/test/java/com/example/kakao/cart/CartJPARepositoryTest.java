package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Import(ObjectMapper.class)
@DataJpaTest
class CartJPARepositoryTest extends DummyEntity {

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
    void setUp(){
        User userA = newUser("userA");
        User userB = newUser("userB");
        userJPARepository.saveAll(Arrays.asList(userA, userB));

        List<Product> products = productDummyList();
        productJPARepository.saveAll(products);

        List<Option> options = optionDummyList(products);
        optionJPARepository.saveAll(options);

        List<Cart> cartsA = cartDummyList(userA, options, Arrays.asList(1, 2));
        List<Cart> cartsB = cartDummyList(userB, options, Arrays.asList(6, 7));
        cartJPARepository.saveAll(cartsA);
        cartJPARepository.saveAll(cartsB);

        em.clear();
    }

    @AfterEach
    void clear(){
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    @Test
    @DisplayName("장바구니 전체 조회")
    void cart_findAll_test() {
        //given
        log.info(">> cart_findAll_test");

        //when
        List<Cart> findAllList = cartJPARepository.findAll();

        //then
        assertThat(findAllList).hasSize(4);
        assertThat(findAllList.get(0).getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getPrice()).isEqualTo(10000);
        assertThat(findAllList.get(0).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getUser().getId()).isEqualTo(1); //userA
        assertThat(findAllList.get(3).getId()).isEqualTo(4);
        assertThat(findAllList.get(3).getPrice()).isEqualTo(14500);
        assertThat(findAllList.get(3).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(3).getOption().getId()).isEqualTo(7);
        assertThat(findAllList.get(3).getUser().getId()).isEqualTo(2); //userB
        assertThat(findAllList.get(3).getOption().getProduct().getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 전체 조회 - fetch join")
    void cart_findAll_joinFetch_test() {
        //given
        log.info(">> cart_findAll_joinFetch_test");

        //when
        List<Cart> findAllList = cartJPARepository.findAllJoinFetchAll();
        //List<Cart> findAllList = cartJPARepository.findAllJoinFetchUser();
        //List<Cart> findAllList = cartJPARepository.findAllJoinFetchUserOption();

        //then
        assertThat(findAllList).hasSize(4);
        assertThat(findAllList.get(0).getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getPrice()).isEqualTo(10000);
        assertThat(findAllList.get(0).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getUser().getId()).isEqualTo(1); //userA
        assertThat(findAllList.get(3).getId()).isEqualTo(4);
        assertThat(findAllList.get(3).getPrice()).isEqualTo(14500);
        assertThat(findAllList.get(3).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(3).getOption().getId()).isEqualTo(7);
        assertThat(findAllList.get(3).getUser().getId()).isEqualTo(2); //userB
        assertThat(findAllList.get(3).getOption().getProduct().getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 전체 조회 - 메시지 컨버터 실패")
    void cart_findAll_messageConverter_fail_test() {
        //given
        log.info(">> cart_findAll_messageConverter_fail_test");

        //when
        List<Cart> findAllList = cartJPARepository.findAll();
        log.info("========== JSON 직렬화 ==========");
        InvalidDefinitionException exception = assertThrows(InvalidDefinitionException.class, () -> om.writeValueAsString(findAllList));

        //then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).contains("No serializer found");
    }

    @Test
    @DisplayName("장바구니 전체 조회 - 메시지 컨버터 dto")
    void cart_findAll_messageConverter_dto_test() throws JsonProcessingException {
        //given
        log.info(">> cart_findAll_messageConverter_dto_test");

        //when
        List<Cart> findAllList = cartJPARepository.findAll();
        log.info("========== JSON 직렬화 ==========");
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(findAllList);
        String responseBody = om.writeValueAsString(responseDTO);
        log.info("responseBody=" + responseBody);

        //then
        assertThat(findAllList).hasSize(4);
        assertThat(findAllList.get(0).getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getPrice()).isEqualTo(10000);
        assertThat(findAllList.get(0).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getUser().getId()).isEqualTo(1); //userA
        assertThat(findAllList.get(3).getId()).isEqualTo(4);
        assertThat(findAllList.get(3).getPrice()).isEqualTo(14500);
        assertThat(findAllList.get(3).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(3).getOption().getId()).isEqualTo(7);
        assertThat(findAllList.get(3).getUser().getId()).isEqualTo(2); //userB
        assertThat(findAllList.get(3).getOption().getProduct().getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 전체 조회 - 메시지 컨버터 fetch join")
    void cart_findAll_messageConverter_joinFetch_test() throws JsonProcessingException {
        //given
        log.info(">> cart_findAll_messageConverter_joinFetch_test");

        //when
        List<Cart> findAllList = cartJPARepository.findAllJoinFetchAll();
        log.info("========== JSON 직렬화 ==========");
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(findAllList);
        String responseBody = om.writeValueAsString(responseDTO);
        log.info("responseBody=" + responseBody);

        //then
        assertThat(findAllList).hasSize(4);
        assertThat(findAllList.get(0).getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getPrice()).isEqualTo(10000);
        assertThat(findAllList.get(0).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getUser().getId()).isEqualTo(1); //userA
        assertThat(findAllList.get(3).getId()).isEqualTo(4);
        assertThat(findAllList.get(3).getPrice()).isEqualTo(14500);
        assertThat(findAllList.get(3).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(3).getOption().getId()).isEqualTo(7);
        assertThat(findAllList.get(3).getUser().getId()).isEqualTo(2); //userB
        assertThat(findAllList.get(3).getOption().getProduct().getId()).isEqualTo(2);

    }

    @Test
    @DisplayName("장바구니 아이디로 조회 성공")
    void cart_findById_test() {
        //given
        log.info(">> cart_findById_test");
        int id = 1;

        //when
        Cart cart = cartJPARepository.findById(id).get();

        //then
        assertThat(cart.getId()).isEqualTo(1);
        assertThat(cart.getPrice()).isEqualTo(10000);
        assertThat(cart.getQuantity()).isEqualTo(1);
        assertThat(cart.getOption().getId()).isEqualTo(1);
        assertThat(cart.getUser().getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("장바구니 아이디로 조회 실패")
    void cart_findById_fail_test() {
        //given
        log.info(">> cart_findById_fail_test");
        int id = 5;

        //when
        Optional<Cart> cart = cartJPARepository.findById(id);

        //then
        assertThat(cart).isEmpty();
    }

    @Test
    @DisplayName("사용자의 장바구니 조회 성공")
    void cart_findByUserId_test() {
        //given
        log.info(">> cart_findByUserId_test");
        int userId = 1;

        //when
        List<Cart> findByUserIdList = cartJPARepository.findByUserId(userId);

        //then
        assertThat(findByUserIdList).hasSize(2);
        assertThat(findByUserIdList.get(0).getId()).isEqualTo(1);
        assertThat(findByUserIdList.get(0).getPrice()).isEqualTo(10000);
        assertThat(findByUserIdList.get(0).getQuantity()).isEqualTo(1);
        assertThat(findByUserIdList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(findByUserIdList.get(0).getUser().getId()).isEqualTo(1);
        assertThat(findByUserIdList.get(1).getId()).isEqualTo(2);
        assertThat(findByUserIdList.get(1).getPrice()).isEqualTo(10900);
        assertThat(findByUserIdList.get(1).getQuantity()).isEqualTo(1);
        assertThat(findByUserIdList.get(1).getOption().getId()).isEqualTo(2);
        assertThat(findByUserIdList.get(1).getUser().getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자의 장바구니 조회 실패")
    void cart_findByUserId_fail_test() {
        //given
        log.info(">> cart_findByUserId_fail_test");
        int userId = 3;

        //when
        List<Cart> findByUserIdList = cartJPARepository.findByUserId(userId);

        //then
        assertThat(findByUserIdList).isEmpty();
    }

    @Test
    @DisplayName("장바구니 수정 성공")
    void cart_update_test() {
        //given
        log.info(">> cart_update_test");
        int id = 1;
        int quantity = 5;

        //when
        cartJPARepository.findById(id).ifPresent(
                cart -> {
                    cart.update(quantity, cart.getPrice() * quantity);
                    cartJPARepository.update(cart);
                    em.flush();
                });

        Cart updatedCart = cartJPARepository.findById(id).get();

        //then
        assertThat(updatedCart.getId()).isEqualTo(1);
        assertThat(updatedCart.getPrice()).isEqualTo(50000);
        assertThat(updatedCart.getQuantity()).isEqualTo(5);
        assertThat(updatedCart.getOption().getId()).isEqualTo(1);
        assertThat(updatedCart.getUser().getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("장바구니 수정 실패")
    void cart_update_fail_test() {
        //given
        log.info(">> cart_update_fail_test");
        int id = 100;
        int quantity = 5;

        //when
        cartJPARepository.findById(id).ifPresent(
                cart -> {
                    cart.update(quantity, cart.getPrice() * quantity);
                    cartJPARepository.update(cart);
                    em.flush();
                });

        Optional<Cart> updatedCart = cartJPARepository.findById(id);

        //then
        assertThat(updatedCart).isEmpty();
    }

    @Test
    @DisplayName("장바구니 전체 삭제")
    void cart_deleteAll_test(){
        //given
        log.info(">> cart_deleteAll_test");

        // then
        cartJPARepository.deleteAll();
        List<Cart> findAllList = cartJPARepository.findAll();

        //then
        assertThat(findAllList).isEmpty();
    }

    @Test
    @DisplayName("장바구니 개별 삭제")
    void cart_deleteById_test(){
        //given
        log.info(">> cart_deleteById_test");
        int id = 1;

        // then
        Optional<Cart> cart = cartJPARepository.findById(id);
        cart.ifPresent(c -> {
            cartJPARepository.deleteById(id);
        });

        Optional<Cart> deletedCart = cartJPARepository.findById(id);

        //then
        assertThat(deletedCart).isEmpty();
    }
}