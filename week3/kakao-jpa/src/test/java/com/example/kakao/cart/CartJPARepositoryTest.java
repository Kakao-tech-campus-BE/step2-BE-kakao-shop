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
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
    public void setUp(){
        User userPS = userJPARepository.save(newUser("ssar"));
        System.out.println(userPS.getId());
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        cartJPARepository.saveAll(cartDummyList(userPS, optionListPS));

        em.clear();
    }

    //후행작업 - 인메모리db가 초기화되지 않아서 추가함. id는 한개를 추가했는데 테스트 3개 각각 1개씩 추가되는 버그 발생
    @AfterEach
    public void cleanup(){
        cartJPARepository.deleteAll();
        userJPARepository.deleteAll();
        productJPARepository.deleteAll();
        optionJPARepository.deleteAll();
    }

    //시나리오 : 1. ssar@nate.com 이메일을 가진 유저의 id로 카트를 찾는다. 2. 카트번호로 카트를 찾는다.
    @Test
    public void findByUserId_findById_test() throws JsonProcessingException {
        // given
        //email을 통해 user를 찾는다.
        String email = "ssar@nate.com";
        User user = userJPARepository.findByEmail(email).orElseThrow();
        int cartId = 1;
        // when
        List<Cart> cartList = cartJPARepository.findByUserId(user.getId());
        Cart cart = cartJPARepository.findById(cartId).get();

        // then (상태 검사)
        //1. 유저별 카트(cartList) 검사
        Assertions.assertThat(cartList.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getQuantity()).isEqualTo(10);
        Assertions.assertThat(cartList.get(0).getPrice()).isEqualTo(169000);
        Assertions.assertThat(cartList.get(0).getOption().getId()).isEqualTo(4);
        Assertions.assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("뽑아쓰는 키친타올 130매 12팩");
        Assertions.assertThat(cartList.get(0).getOption().getPrice()).isEqualTo(16900);
        Assertions.assertThat(cartList.get(0).getOption().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");

        Assertions.assertThat(cartList.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(cartList.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(1).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartList.get(1).getPrice()).isEqualTo(44500);
        Assertions.assertThat(cartList.get(1).getOption().getId()).isEqualTo(5);
        Assertions.assertThat(cartList.get(1).getOption().getOptionName()).isEqualTo("2겹 식빵수세미 6매");
        Assertions.assertThat(cartList.get(1).getOption().getPrice()).isEqualTo(8900);
        Assertions.assertThat(cartList.get(1).getOption().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(1).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");

        //2. id로 찾은 카트(cart) 검사
        Assertions.assertThat(cart.getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(cart.getQuantity()).isEqualTo(10);
        Assertions.assertThat(cart.getPrice()).isEqualTo(169000);
        Assertions.assertThat(cart.getOption().getId()).isEqualTo(4);
        Assertions.assertThat(cart.getOption().getOptionName()).isEqualTo("뽑아쓰는 키친타올 130매 12팩");
        Assertions.assertThat(cart.getOption().getPrice()).isEqualTo(16900);
        Assertions.assertThat(cart.getOption().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(cart.getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
    }


    //시나리오: 유저의 id에 속한 cart중 첫번째 카트에서 양을 10에서 20으로 변경함.
    @Test
    public void updateCart_test() throws JsonProcessingException{
        //수정할 때, 옵션의 가격을 수정하는 것은 불가능하므로 quantity만을 인자로 받는다는 가정을한다. cart와 option은 일대일 관계로
        //option의 가격을 유지한 채로 수정하기 위해 option의 가격에 * quantity를 새롭게 cart entity의 price로 지정한다.
        // given
        //email을 통해 user를 찾는다.
        String email = "ssar@nate.com";
        User user = userJPARepository.findByEmail(email).orElseThrow();
        List<Cart> cartList = cartJPARepository.findByUserId(user.getId());
        int quantity = 20;
        int id = cartList.get(0).getId();
        // when
        Optional<Cart> cartOP = cartJPARepository.findById(id);
        Cart cart = null;
        if (cartOP.isPresent()) {
            cart = cartOP.get();
            cart.update(quantity, cart.getOption().getPrice() * quantity);
        }
        em.flush();

        // then (상태 검사)
        // 카트가 원하는대로 업데이트 되었는지 검사 - 계획대로라면 quantity가 20, 가격은 2배인 338000이 되어야 한다.
        Assertions.assertThat(cartList.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getQuantity()).isEqualTo(20);
        Assertions.assertThat(cartList.get(0).getPrice()).isEqualTo(338000);
        Assertions.assertThat(cartList.get(0).getOption().getId()).isEqualTo(4);
        Assertions.assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("뽑아쓰는 키친타올 130매 12팩");
        Assertions.assertThat(cartList.get(0).getOption().getPrice()).isEqualTo(16900);
        Assertions.assertThat(cartList.get(0).getOption().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");

    }

    //시나리오: 유저의 장바구니에서 두번째 요소를 제거한다.
    @Test
    public void deleteCart_test() throws JsonProcessingException{
        //given
        String email = "ssar@nate.com";
        User user = userJPARepository.findByEmail(email).orElseThrow();
        List<Cart> cartList = cartJPARepository.findByUserId(user.getId());
        int id = cartList.get(1).getId();
        //when
        Optional<Cart> cart = cartJPARepository.findById(id);
        if(cart.isPresent()){
            cartJPARepository.deleteById(id);
        }
        em.flush();

        // then (상태 검사)
        // delete작업을 진행했기 때문에 동일한 id로 optional했을 때 if문을 통과하지 못하는 false가 된다.
        Assertions.assertThat(cartJPARepository.findById(id).isPresent()).isFalse();
    }
}
