package com.example.kakao.Cart;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
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
import javax.persistence.EntityManager;
import java.util.List;


@DisplayName("장바구니 관련 JPA 테스트")
@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    private EntityManager em;
    private CartJPARepository cartJPARepository;
    private UserJPARepository userJPARepository;
    private ProductJPARepository productJPARepository;
    private OptionJPARepository optionJPARepository;

    private ObjectMapper om;

    public CartJPARepositoryTest(@Autowired EntityManager em,
                                 @Autowired CartJPARepository cartJPARepository,
                                 @Autowired UserJPARepository userJPARepository,
                                 @Autowired ObjectMapper om,
                                 @Autowired ProductJPARepository productJPARepository,
                                 @Autowired OptionJPARepository optionJPARepository) {
        this.em = em;
        this.cartJPARepository = cartJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.userJPARepository = userJPARepository;
        this.productJPARepository = productJPARepository;
        this.om = om;
    }

    @BeforeEach
    public void setUp(){
        User user = newUser("user");
        userJPARepository.save(user);
        List<Product> productList = productJPARepository.saveAll(productDummyList());
        List<Option> optionList = optionJPARepository.saveAll(optionDummyList(productList));
        cartJPARepository.saveAll(cartDummyList(user,optionList,2));
        em.clear();
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void cartSave_test() {
        // given
        User testuser = newUser("testuser");
        userJPARepository.save(testuser);
        Integer userid = testuser.getId();
        Integer optionid=1;
        Option testoption =optionJPARepository.findById(optionid).orElseThrow(
                () -> new RuntimeException("해당 옵션을 찾을 수 없습니다.")
        );
        Cart cart = newCart(testuser,testoption,3);
        long previousCount = cartJPARepository.count();
        // when
        System.out.println("======================start===================");
        cartJPARepository.save(cart);
        Integer cartid= cart.getId();
        System.out.println("======================end======================");
        // then
        Assertions.assertThat(cartJPARepository.count()).isEqualTo(previousCount+1);
        Cart savedCart = cartJPARepository.findByUserId(userid).orElseThrow(
                ()-> new RuntimeException("해당 cart를 찾을 수 없습니다."));
        Assertions.assertThat(savedCart.getId()).isEqualTo(cartid);
        Assertions.assertThat(savedCart.getUser().getId()).isEqualTo(userid);
        Assertions.assertThat(savedCart.getOption().getId()).isEqualTo(optionid);
    }

    @Test
    @DisplayName("장바구니 조회 테스트-lazy시 에러")
    public void cartOptionUser_findByUserId_lazy_error_test() {
        // given
        Integer userid=1;
        // when
        System.out.println("====================start===================");
        List<Cart> cartList = cartJPARepository.findAllByUserId(userid);
        System.out.println("========================end=====================");
        // then
        Assertions.assertThatThrownBy(()-> om.writeValueAsString(cartList)).isInstanceOf(JsonProcessingException.class);
    }




    @Test
    @DisplayName("장바구니 조회 테스트 lazy-커스텀쿼리(Cart, User, Option, Product fetchJoin)")
    public void cartOptionUser_findByUserId_lazy_test1() throws JsonProcessingException {
        // given
        Integer userid=1;
        // when
        System.out.println("====================start===================");
        List<Cart> cartList = cartJPARepository.mFindAllByUserId(userid);
        String responseBody = om.writeValueAsString(cartList);
        System.out.println("테스트 : "+responseBody);
        System.out.println("========================end=====================");
        // then
        Assertions.assertThat(cartList.get(0).getPrice()).isEqualTo(20000);
        Assertions.assertThat(cartList.get(0).getQuantity()).isEqualTo(2);
        Assertions.assertThat(cartList.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartList.get(0).getUser().getUsername()).isEqualTo("user");
    }

//    @Test
//    @DisplayName("장바구니 조회 테스트 lazy- @EntityGraph(Cart, User, Option fetchJoin)")
//    //Entity graph 사용시 product를 사용할 수 없다?
//    // 아래 코드를 실행하면 Product를 제외한 객체들은 한번에 가져온다, Product를 따로 쿼리를 날린다.
//    //-> JPQL말고 어케하지, EntityGraph에서 설정을 할수 있나? Attribute에 Product 넣으면 안돌아가는데..
//    public void cartOptionUser_findByUserId_lazy_test2() throws JsonProcessingException {
//        // given
//        Integer userid=1;
//        // when
//        System.out.println("====================start===================");
//        List<Cart> cartList = cartJPARepository.findAllByUserId(userid);
//        String responseBody = om.writeValueAsString(cartList);
//        System.out.println("테스트 : "+responseBody);
//        System.out.println("========================end=====================");
//        // then
//        Assertions.assertThat(cartList.get(0).getPrice()).isEqualTo(20000);
//        Assertions.assertThat(cartList.get(0).getQuantity()).isEqualTo(2);
//        Assertions.assertThat(cartList.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
//        Assertions.assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
//        Assertions.assertThat(cartList.get(0).getUser().getUsername()).isEqualTo("user");
//    }


    @Test
    @DisplayName("주문하기(장바구니 수정) 테스트- 업데이트후 조회(처음 조회시 fetch join)")
    public void cartUpdate1_test(){
        // given
        Integer cartid = 1;
        // when
        System.out.println("====================start===================");
        Cart cart = cartJPARepository.mfindById(cartid).orElseThrow(
                () -> new RuntimeException("장바구리르 찾을 수 없거나 비어있습니다.")
        );
        cart.update(30,900000);
        em.flush();
        System.out.println("====================end===================");
        // then
        Cart updatedCart = cartJPARepository.findById(cartid).orElseThrow(
                () -> new RuntimeException("장바구리를 찾을 수 없거나 비어있습니다.")
        );
        Assertions.assertThat(updatedCart.getQuantity()).isEqualTo(30);
        Assertions.assertThat(updatedCart.getPrice()).isEqualTo(900000);
        Assertions.assertThat(updatedCart.getOption().getId()).isEqualTo(1);
        Assertions.assertThat(updatedCart.getOption().getProduct().getId()).isEqualTo(1);
    }
}
