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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    private final EntityManager em;

    private final CartJPARepository cartJPARepository;

    private final ObjectMapper om;

    private final UserJPARepository userJPARepository;

    private final OptionJPARepository optionJPARepository;

    private final ProductJPARepository productJPARepository;

    @Autowired
    public CartJPARepositoryTest (
            EntityManager em,
            CartJPARepository cartJPARepository,
            UserJPARepository userJPARepository,
            OptionJPARepository optionJPARepository,
            ObjectMapper om,
            ProductJPARepository productJPARepository) {
        this.em = em;
        this.cartJPARepository = cartJPARepository;
        this.userJPARepository = userJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.om = om;
        this.productJPARepository = productJPARepository;
    }

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User ssar = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        cartJPARepository.saveAll(cartDummyList(ssar, optionListPS));

        em.clear();
    }

    //영속화 되기 전 값이 null이여야 하는데 nullPointException을 방지하기 위해
    //jpa가 자동으로 0값을 넣어주는 것인가?
    @DisplayName("영속성 테스트")
    @Test
    public void join_test() {
        Cart cart = newCart(newUser("cos"), optionDummyList(productDummyList()).get(1), 1);

        //id 값이 0이 나온다
        System.out.println("영속화 되기 전 id : " + cart.getId());
        cartJPARepository.save(cart);
        System.out.println("영속화 된 후 id : " + cart.getId());

        assertEquals(3, cart.getId());
    }

    @DisplayName("데이터 삽입 테스트")
    @Test
    public void create_test() {
        int userId = 1;
        int optionId = 2;

        User user = userJPARepository.findById(userId).orElseThrow(RuntimeException::new);
        Option option = optionJPARepository.findById(optionId).orElseThrow(RuntimeException::new);

        Cart cart = newCart(user, option, 2);
        cartJPARepository.save(cart);

        //삽입에 대한 결과를 확인할 때, 이렇게 모든 결과를 확인해야 하는 것인가?
        //갯수나 정렬하여 예상된 순서를 검증하는 것도 생각해봤는데 그게 결과에 대한 보증이 된다고는 생각안해서..
        //더 좋은 방법이 있을까요?
        assertEquals(3, cart.getId());
        assertEquals(2, cart.getQuantity());
        assertEquals(option.getPrice() * cart.getQuantity(), cart.getPrice());
        assertEquals(user.getId(), cart.getUser().getId());
        assertEquals(user.getEmail(), cart.getUser().getEmail());
        assertEquals(user.getPassword(), cart.getUser().getPassword());
        assertEquals(user.getUsername(), cart.getUser().getUsername());
        assertEquals(user.getRoles(), cart.getUser().getRoles());
        assertEquals(option.getId(), cart.getOption().getId());
        assertEquals(option.getOptionName(), cart.getOption().getOptionName());
        assertEquals(option.getPrice(), cart.getOption().getPrice());
        assertEquals(option.getProduct().getId(), cart.getOption().getProduct().getId());
        assertEquals(option.getProduct().getProductName(), cart.getOption().getProduct().getProductName());
        assertEquals(option.getProduct().getImage(), cart.getOption().getProduct().getImage());
        assertEquals(option.getProduct().getPrice(), cart.getOption().getProduct().getPrice());
    }

    @DisplayName("데이터 조회 테스트")
    @Test
    public void read_test() throws JsonProcessingException {
//      3중 join fetch를 쓰는 것이 맞는 것일까?
//      만약 쓰지 않는다면 아래의 주석 코드와 같이 Lazy Loading으로 통해 proxy 객체에 담아 assertEquals 메서드를 통해
//      하나씩 꺼내어 비교하는 방법이 있을 것 같다 대신 데이터가 많아졌을 시, select문이 대량으로 발생하는 단점이 있을 것 같다
        int userId = 1;

        List<Cart> carts = cartJPARepository.mFindAllByUserId(userId);
        String result = om.writeValueAsString(carts);
        System.out.println(result);

//        carts.forEach(cart -> {
//            cart.getOption().getProduct();
//        });

//      then을 어떻게 구현해야하는가
    }

    @DisplayName("전체 데이터 업데이트 테스트")
    @Test
    public void update_test(){
        int id = 1;
        int quantity = 11;
        int price = 2000;

        Cart cart = cartJPARepository.findById(id).orElseThrow(RuntimeException::new);
        cart.update(quantity, price);
        em.flush();

        assertEquals(quantity, cart.getQuantity());
        assertEquals(price, cart.getPrice());
    }

    @DisplayName("수량 데이터 업데이트 테스트")
    @Test
    public void quantity_update_test(){
        int id = 1;
        int quantity = 10;


        Cart cart = cartJPARepository.findById(id).orElseThrow(RuntimeException::new);
        cart.update(quantity, cart.getPrice());
        em.flush();

        assertEquals(quantity, cart.getQuantity());
    }

    @DisplayName("가격 데이터 업데이트 테스트")
    @Test
    public void price_update_test(){
        int id = 1;
        int price = 1000;

        Cart cart = cartJPARepository.findById(id).orElseThrow(RuntimeException::new);
        cart.update(cart.getQuantity(), price);
        em.flush();

        assertEquals(price, cart.getPrice());
    }

    @DisplayName("데이터 삭제 테스트")
    @Test
    public void delete_test() {
        int id = 1;

        List<Cart> beforeCarts = cartJPARepository.findAll();
        cartJPARepository.deleteById(id);
        em.flush();
        List<Cart> afterCarts = cartJPARepository.findAll();

        assertEquals(beforeCarts.size()-1, afterCarts.size());
    }

    @DisplayName("없는 id에 대한 데이터 삭제 테스트")
    @Test
    public void null_delete_test(){
        int id = Integer.MAX_VALUE;

        Assertions.assertThatThrownBy( () -> {
            cartJPARepository.deleteById(id);
        }).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("전체 데이터 삭제 테스트")
    @Test
    public void all_delete_test(){
        List<Cart> carts = cartJPARepository.findAll();
        List<Integer> cartIds = carts.stream()
                .map(Cart::getId)
                .collect(Collectors.toList());

        cartJPARepository.deleteAllById(cartIds);

        Assertions.assertThat(cartJPARepository.count()).isZero();
    }
}
