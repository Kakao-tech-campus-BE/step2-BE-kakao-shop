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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;// JPA를 직접 사용하기 위해 주입

    @Autowired
    private CartJPARepository cartJPARepository;// 테스트할 Repository

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @BeforeEach// 테스트 전에 실행되는 메소드
    public void setUp(){
        User user = userJPARepository.save(newUser("ssal"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        Cart cart1 = cartJPARepository.save(newCart(user,  optionListPS.get(4),3));
        Cart cart2 = cartJPARepository.save(newCart(user,  optionListPS.get(9),4));
        Cart cart3 = cartJPARepository.save(newCart(user,  optionListPS.get(10),5));
        em.clear();
     }
    @Test
    public void cart_add_test(){
        //given
        int optionId1 = 1;
        int optionId2 = 2;
        int quantity = 5;
        String username = "ssal";
        //when
        User user = userJPARepository.findByUsername(username);
        Option option1 = optionJPARepository.findById(optionId1).orElseThrow(
                () -> new RuntimeException("해당 옵션을 찾을 수 없습니다.")
        );
        Cart cart1 = cartJPARepository.save(newCart(user, option1, quantity));

        Option option2 = optionJPARepository.findById(optionId2).orElseThrow(
                () -> new RuntimeException("해당 옵션을 찾을 수 없습니다.")
        );
        Cart cart2 = cartJPARepository.save(newCart(user, option2, quantity));
        //then
        Assertions.assertThat(cart1.getUser().getUsername()).isEqualTo(username);
        Assertions.assertThat(cart1.getOption().getId()).isEqualTo(optionId1);

        Assertions.assertThat(cart2.getUser().getUsername()).isEqualTo(username);
        Assertions.assertThat(cart2.getOption().getId()).isEqualTo(optionId2);
        Assertions.assertThat(cart1.getQuantity()).isEqualTo(quantity);
    }

    @Test
    public void cart_findAll_test(){
        //given

        //when
        List<Cart> cartList = cartJPARepository.findAll();
        //then
        Assertions.assertThat(cartList.size()).isEqualTo(3);


        Assertions.assertThat(cartList.get(0).getUser().getUsername()).isEqualTo("ssal");
        Assertions.assertThat(cartList.get(0).getOption().getId()).isEqualTo(5);//왜
        Assertions.assertThat(cartList.get(0).getOption().getPrice()).isEqualTo(8900);
        Assertions.assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("2겹 식빵수세미 6매");

        Assertions.assertThat(cartList.get(1).getOption().getId()).isEqualTo(10);//왜
        Assertions.assertThat(cartList.get(1).getOption().getPrice()).isEqualTo(49900);
        Assertions.assertThat(cartList.get(1).getOption().getOptionName()).isEqualTo("JR310BT (무선 전용) - 레드");

        Assertions.assertThat(cartList.get(2).getOption().getId()).isEqualTo(11);//왜
        Assertions.assertThat(cartList.get(2).getOption().getPrice()).isEqualTo(49900);
        Assertions.assertThat(cartList.get(2).getOption().getOptionName()).isEqualTo("JR310BT (무선 전용) - 그린");


    }

    @Test
    public void cart_update_test(){
        //given
        int cartId = 1;
        int quantity = 10;

        //when
        Cart cart = cartJPARepository.findById(cartId).orElseThrow(
                () -> new RuntimeException("해당 카트를 찾을 수 없습니다.")
        );

        cart.update(quantity, cart.getOption().getPrice()*quantity); //수량과 가격을 업데이트,카트에는 수량만큼의 가격이 들어가야 한다
        em.flush();// 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영
        //then

    }

}
