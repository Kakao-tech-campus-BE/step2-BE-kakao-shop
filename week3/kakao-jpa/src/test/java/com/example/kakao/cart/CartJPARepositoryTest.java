package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;


@DataJpaTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD) // 클래스의 모든 테스트 케이스마다 시작하기 이전에 context 재생성
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


    @BeforeEach
    @DisplayName("더미 장바구니 준비")
    public void setUp() {

        User user = newUser("heechan");
        userJPARepository.save(user);

        List<Product> productList = productJPARepository.saveAll(productDummyList());
        List<Option> optionList = optionJPARepository.saveAll(optionDummyList(productList));

        Cart cart1 = newCart(user, optionList.get(0), 1);
        cartJPARepository.save(cart1);

        Cart cart2 = newCart(user, optionList.get(2), 1);
        cartJPARepository.save(cart2);

        em.clear(); // 영속성 컨텍스트 비우기
    }

    @Test
    @DisplayName("장바구니에 없던 옵션 담기")
    void cart_insert_test() {

        // given
        int optionId = 10;
        int userId = 1;
        int previousCartSize = cartJPARepository.findAll().size();
        int newQuantity = 1;

        User user = userJPARepository.findById(userId).orElseThrow();
        Option option = optionJPARepository.findById(optionId).orElseThrow();
        Cart cart = newCart(user, option, newQuantity);

        // when
        // 장바구니에 해당 옵션이 없다는 것에 대한 validation 검증 후, save
        assertThat(cartJPARepository.findByUserIdAndOptionId(userId, optionId)).isEmpty();
        cartJPARepository.save(cart);

        // then
        Cart savedCart = cartJPARepository.findByUserIdAndOptionId(userId, optionId).orElseThrow(
                () -> new NoSuchElementException("해당 optionId에 해당하는 옵션이 없습니다."));

        assertThat(cartJPARepository.findAll()).hasSize(previousCartSize + 1);
        assertThat(savedCart.getQuantity()).isEqualTo(newQuantity);
        assertThat(savedCart.getUser().getUsername()).isEqualTo(user.getUsername());
    }


    @Test
    @DisplayName("장바구니 수정")
    void cart_update_test() {

        // given
        int optionId = 1;
        int userId = 1;
        Cart existingCartItem = cartJPARepository.findByUserIdAndOptionId(userId, optionId).orElseThrow(() ->
                new NoSuchElementException("userId, optionId에 해당하는 장바구니 아이템이 없습니다."));

        int optionPrice = existingCartItem.getOption().getPrice();
        int previousCartSize = cartJPARepository.findAll().size();
        int previousQuantity = existingCartItem.getQuantity();
        int newQuantity = 100;

        // when
        existingCartItem.update(previousQuantity + newQuantity, (previousQuantity + newQuantity) * optionPrice);
        cartJPARepository.saveAndFlush(existingCartItem);

        // then
        Cart savedCartItem = cartJPARepository.findByUserIdAndOptionId(userId, optionId).orElseThrow(() ->
                new NoSuchElementException("userId, optionId에 해당하는 장바구니 아이템이 없습니다."));

        assertThat(cartJPARepository.findAll()).hasSize(previousCartSize); // 전체 cart의 사이즈는 같아야 한다.
        assertThat(savedCartItem.getQuantity()).isEqualTo(previousQuantity + newQuantity); // 추가되는 수량만큼 더해져야 한다.
        assertThat(savedCartItem.getPrice()).isEqualTo((previousQuantity + newQuantity) * optionPrice);
    }


    @Test
    @DisplayName("사용자 id로 장바구니 조회")
    void cart_findByUserId_test() {

        // given
        int userId = 1;

        // when
        // 존재하는 사용자라는 것에 대한 validation
        assertThat(userJPARepository.findById(userId)).isPresent();
        List<Cart> carts = cartJPARepository.findAllCartsWithOptionsAndUserAndProductByUserId(userId);

        // then
        assertThat(carts).hasSize(2);
        assertThat(carts.get(0).getQuantity()).isEqualTo(1);
        assertThat(carts.get(1).getQuantity()).isEqualTo(1);
        assertThat(carts.get(0).getOption().getProduct().getProductName()).isEqualTo(
                "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"
        );
        assertThat(carts.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        assertThat(carts.get(1).getOption().getOptionName()).isEqualTo("고무장갑 베이지 S(소형) 6팩");
        assertThat(carts.get(0).getOption().getPrice()).isEqualTo(10000);
        assertThat(carts.get(1).getOption().getPrice()).isEqualTo(9900);
        assertThat(carts.stream().mapToInt(Cart::getPrice).sum()).isEqualTo(19900);
    }


    @Test
    @DisplayName("장바구니 삭제 성공")
    void cart_deleteById_success_test() {

        // given
        int cartId = 1;
        int userId = 1;
        int previousCartSize = cartJPARepository.findByUserId(userId).size();

        // when
        // 존재하는 장바구니라는 것에 대한 validation
        assertThat(cartJPARepository.findById(cartId)).isPresent();
        cartJPARepository.deleteById(cartId);

        // then
        assertThat(cartJPARepository.findAll()).hasSize(previousCartSize - 1);
        assertThat(cartJPARepository.findById(cartId)).isEmpty();
    }


    @Test
    @DisplayName("장바구니 삭제 실패: 존재하지 않는 장바구니")
    void cart_deleteById_failed_test() {

        // given
        int cartId = 10;

        // when
        // 존재하지 않는 장바구니라는 것에 대한 validation
        assertThat(cartJPARepository.findById(cartId)).isEmpty();
        Throwable thrown = catchThrowable(() -> cartJPARepository.deleteById(cartId));

        // then
        // 존재하지 않는 장바구니 아이템을 삭제하려고 시도 시, 오류 발생 catch
        assertThat(thrown).isInstanceOf(EmptyResultDataAccessException.class);
    }

}