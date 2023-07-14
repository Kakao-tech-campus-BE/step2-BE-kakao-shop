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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.assertj.core.api.Assertions;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest  extends DummyEntity{


    @Autowired
    private EntityManager em;
    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;

    private User user=newUser("ssar");

    @BeforeEach
    public void setUp(){
        userJPARepository.save(user);
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List <Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List <Cart> carts = Arrays.asList(
                newCart(user,optionListPS.get(0),5),
                newCart(user,optionListPS.get(2),3),
                newCart(user,optionListPS.get(7),1)
        );
        cartJPARepository.saveAll(carts);
        em.clear();

    }

    // 1. cart find all
    // 2. cart find by cart_id
    // 3. cart insert

    @Test
    public void cartFindAll() throws JsonProcessingException {
        // given

        // when
        List<Cart> cartItems = cartJPARepository.cartFindAll();


        // then
        String responseBody = om.writeValueAsString(cartItems);
        System.out.println("테스트 : " + responseBody);
    }

    //cartID로 검색
    @Test
    public void cartFindById() throws JsonProcessingException {
        //given
        int id = 1;

        //when
        Cart cart = cartJPARepository.findById(id);

        //then
        String responseBody = om.writeValueAsString(cart);
        System.out.println("테스트 : " + responseBody);
        Assertions.assertThat(cart.getId()).isEqualTo(1);
        Assertions.assertThat(cart.getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(cart.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cart.getQuantity()).isEqualTo(5);
    }

    //cart insert
    @Test
    public void cartInsert() throws JsonProcessingException {

        //given
        Option newOption =  optionJPARepository.findById(10).orElseThrow();
        Cart cart = newCart(user,newOption,10);

        //when
        Cart newCart = cartJPARepository.save(cart);

        //then
        String responseBody = om.writeValueAsString(newCart);
        System.out.println("테스트 : " + responseBody);
        Assertions.assertThat(cart.getId()).isEqualTo(4);
        Assertions.assertThat(cart.getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(cart.getOption().getOptionName()).isEqualTo("JR310BT (무선 전용) - 레드");
        Assertions.assertThat(cart.getQuantity()).isEqualTo(10);

    }

}
