package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cart JPA 연결 테스트")
@Import(ObjectMapper.class)
@DataJpaTest
class CartJPARepositoryTest extends DummyEntity {

    private final EntityManager em;
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final UserJPARepository userJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ObjectMapper om;


    public CartJPARepositoryTest(
            @Autowired EntityManager em,
            @Autowired ProductJPARepository productJPARepository,
            @Autowired OptionJPARepository optionJPARepository,
            @Autowired UserJPARepository userJPARepository,
            @Autowired CartJPARepository cartJPARepository,
            @Autowired ObjectMapper om
    ){
        this.em = em;
        this.productJPARepository = productJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.userJPARepository = userJPARepository;
        this.cartJPARepository = cartJPARepository;
        this.om = om;
    }


    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        userJPARepository.save(newUser("donu"));

        em.clear();

    }
    /**
     * 1. 장바구니 담기 테스트
     * 2. 장바구니 조회 테스트
     * 3. 장바구니 수정 테스트
     * 4. 장바구니 삭제 테스트 (이것도 있으면 좋을 듯 ??)
     */

    @DisplayName("장바구니 담기 테스트")
    @Test
    public void saveCart() throws JsonProcessingException {
        // given
        String email = "donu@nate.com";
        User userPS = userJPARepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 이메일을 찾을 수 없습니다."));
        int optionNumber = 1;
        int optionNumber2 = 2;


        // when
        List<Cart> carts = Arrays.asList(newCart(userPS, optionJPARepository.findById(optionNumber).get(), 5),
                newCart(userPS,optionJPARepository.findById(optionNumber2).get() , 5)
        );
        cartJPARepository.saveAll(carts);
        em.flush();

        // then
        List<Cart> userCarts = cartJPARepository.findByUserId(userPS.getId());
        assertEquals(userCarts.size(),2);
    }

    @DisplayName("장바구니 조회 테스트")
    @Test
    public void findCart() {
        // given
        String email = "donu@nate.com";
        User userPS = userJPARepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 이메일을 찾을 수 없습니다."));
        int optionNumber = 1;
        int optionNumber2 = 2;


        // when
        List<Cart> carts = Arrays.asList(newCart(userPS, optionJPARepository.findById(optionNumber).get(), 5),
                newCart(userPS,optionJPARepository.findById(optionNumber2).get() , 5)
        );
        cartJPARepository.saveAll(carts);
        em.flush();

        // then
        cartJPARepository.findById(1).orElseThrow(()-> new RuntimeException("해당 Id의 Cart가 존재하지 않습니다."));
        cartJPARepository.findById(2).orElseThrow(()-> new RuntimeException("해당 Id의 Cart가 존재하지 않습니다."));
    }


    @DisplayName("장바구니 수정 테스트")
    @Test
    public void updateCart() {
        // given
        String email = "donu@nate.com";
        User userPS = userJPARepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 이메일을 찾을 수 없습니다."));
        int optionNumber = 1;
        int optionNumber2 = 2;
        List<Cart> carts = Arrays.asList(newCart(userPS, optionJPARepository.findById(optionNumber).get(), 5),
                newCart(userPS,optionJPARepository.findById(optionNumber2).get() , 5)
        );
        cartJPARepository.saveAll(carts);
        em.flush();

        // when
        Cart cart = cartJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 Id의 Cart가 존재하지 않습니다."));
        cart.update(6,cart.getOption().getPrice()*6);
        em.flush();

        // then
        Cart reCart = cartJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 Id의 Cart가 존재하지 않습니다."));
        assertEquals(reCart.getQuantity(),6);

    }

    @DisplayName("fetch join을 활용한 장바구니 조회 테스트")
    @Test
    public void saveCartOM() throws JsonProcessingException {
        // given
        String email = "donu@nate.com";
        User userPS = userJPARepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 이메일을 찾을 수 없습니다."));
        int optionNumber = 1;
        int optionNumber2 = 2;


        // when
        List<Cart> carts = Arrays.asList(newCart(userPS, optionJPARepository.findById(optionNumber).get(), 5),
                newCart(userPS,optionJPARepository.findById(optionNumber2).get() , 5)
        );
        cartJPARepository.saveAll(carts);
        em.flush();
        em.clear();
        System.out.println("================================================");

        // then
        List<Cart> userCarts = cartJPARepository.mfindByUserId(userPS.getId());
        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(userCarts);
        System.out.println("테스트 : "+responseBody);
        /*
            fetch join을 활용하여 하나의 쿼리만 날라감!
         */
    }


    @DisplayName("장바구니 삭제 테스트")
    @Test
    public void deleteCart(){
        // given
        String email = "donu@nate.com";
        User userPS = userJPARepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 이메일을 찾을 수 없습니다."));
        int userId = userPS.getId();
        int optionNumber = 1;
        int optionNumber2 = 2;
        List<Cart> carts = Arrays.asList(newCart(userPS, optionJPARepository.findById(optionNumber).get(), 5),
                newCart(userPS,optionJPARepository.findById(optionNumber2).get() , 5)
        );
        cartJPARepository.saveAll(carts);
        em.flush();
        em.clear();

        // when
        List<Cart> userCarts = cartJPARepository.findByUserId(userId);
        cartJPARepository.delete(userCarts.get(0));
        cartJPARepository.delete(userCarts.get(1));
        em.flush();
        em.clear();

        // then
        List<Cart> userCarts2 = cartJPARepository.findByUserId(userId);
        assertEquals(userCarts2.size(),0);
    }

}






