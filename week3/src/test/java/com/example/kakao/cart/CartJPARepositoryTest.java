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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProductJPARepository productJPARepository;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("Alter TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("Alter TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("Alter TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        User user = userJPARepository.save(newUser("ssal"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        Cart cart1 = cartJPARepository.save(newCart(user,  optionListPS.get(4),3));
        Cart cart2 = cartJPARepository.save(newCart(user,  optionListPS.get(9),4));
        Cart cart3 = cartJPARepository.save(newCart(user,  optionListPS.get(10),5));
        em.clear();
     }

     // 기본 save : 3.392ms
    @Test
    public void save(){
        //given
        int quantity = 5;
        int id=1;
        User user = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException(userJPARepository.findByUsername("ssal").get().getId()+"해당 유저를 찾을 수 없습니다.")
        );

        List<Integer> optionIds = Arrays.asList(1, 2);

        List<Cart> carts = new ArrayList<>();

        //when
        List<Option> options = optionJPARepository.findAllById(optionIds);

        if(options.size() != optionIds.size()) {
            throw new RuntimeException("해당 옵션을 찾을 수 없습니다.");
        }

        for (Option option : options) {
            Cart newCart = newCart(user, option, quantity);
            carts.add(newCart);
        }

        List<Cart> savedCarts = cartJPARepository.saveAll(carts);

        //then
        for (int i = 0; i < savedCarts.size(); i++) {
            Cart savedCart = savedCarts.get(i);
            Assertions.assertThat(savedCart.getUser().getUsername()).isEqualTo(user.getUsername());
            Assertions.assertThat(savedCart.getOption().getId()).isEqualTo(optionIds.get(i));
            Assertions.assertThat(savedCart.getQuantity()).isEqualTo(quantity);
        };
    }

    // join fetch : 2.742ms
    @Test
    public void findAll_with_join_fetch() throws JsonProcessingException {
        //given

        //when
        List<Cart> cartList = cartJPARepository.findAllWithUserAndOption();
        String responseBody = om.writeValueAsString(cartList);

        //then
        Assertions.assertThat(cartList).hasSize(3);


        Assertions.assertThat(cartList.get(0).getUser().getUsername()).isEqualTo("ssal");
        Assertions.assertThat(cartList.get(0).getOption().getId()).isEqualTo(5);
        Assertions.assertThat(cartList.get(0).getOption().getPrice()).isEqualTo(8900);
        Assertions.assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("2겹 식빵수세미 6매");

        Assertions.assertThat(cartList.get(1).getOption().getId()).isEqualTo(10);
        Assertions.assertThat(cartList.get(1).getOption().getPrice()).isEqualTo(49900);
        Assertions.assertThat(cartList.get(1).getOption().getOptionName()).isEqualTo("JR310BT (무선 전용) - 레드");

        Assertions.assertThat(cartList.get(2).getOption().getId()).isEqualTo(11);
        Assertions.assertThat(cartList.get(2).getOption().getPrice()).isEqualTo(49900);
        Assertions.assertThat(cartList.get(2).getOption().getOptionName()).isEqualTo("JR310BT (무선 전용) - 그린");



    }
    @Test
    public void findAllByUserId() throws JsonProcessingException {
        //given
        int id = 1;
        //when
        List<Cart> cartList = cartJPARepository.findByUserId(id);
        List<CartDTO> cartDtoList = cartList.stream()
                .map(CartDTO::new)
                .collect(Collectors.toList());

        String responseBody = om.writeValueAsString(cartDtoList);
        System.out.println("cartDtoList : "+ responseBody);

        //then
        Assertions.assertThat(cartDtoList).hasSize(3);

        Assertions.assertThat(cartDtoList.get(0).getUser().getName()).isEqualTo("ssal");
        Assertions.assertThat(cartDtoList.get(0).getOption().getId()).isEqualTo(5);
        Assertions.assertThat(cartDtoList.get(0).getOption().getPrice()).isEqualTo(8900);
        Assertions.assertThat(cartDtoList.get(0).getOption().getOptionName()).isEqualTo("2겹 식빵수세미 6매");

        Assertions.assertThat(cartDtoList.get(1).getOption().getId()).isEqualTo(10);
        Assertions.assertThat(cartDtoList.get(1).getOption().getPrice()).isEqualTo(49900);
        Assertions.assertThat(cartDtoList.get(1).getOption().getOptionName()).isEqualTo("JR310BT (무선 전용) - 레드");

        Assertions.assertThat(cartDtoList.get(2).getOption().getId()).isEqualTo(11);
        Assertions.assertThat(cartDtoList.get(2).getOption().getPrice()).isEqualTo(49900);
        Assertions.assertThat(cartDtoList.get(2).getOption().getOptionName()).isEqualTo("JR310BT (무선 전용) - 그린");
    }


    // 기본 findAll : 3.194ms
    // Option과 User를 Join하느라 느림
    @Test
    public void findAll() throws JsonProcessingException {
        //given

        //when
        List<Cart> cartList = cartJPARepository.findAll();
        List<CartDTO> cartDtoList = cartList.stream()
                .map(CartDTO::new)
                .collect(Collectors.toList());

        String responseBody = om.writeValueAsString(cartDtoList);
        System.out.println("cartDtoList : "+ responseBody);

        //then
        Assertions.assertThat(cartDtoList).hasSize(3);

        Assertions.assertThat(cartDtoList.get(0).getUser().getName()).isEqualTo("ssal");
        Assertions.assertThat(cartDtoList.get(0).getOption().getId()).isEqualTo(5);
        Assertions.assertThat(cartDtoList.get(0).getOption().getPrice()).isEqualTo(8900);
        Assertions.assertThat(cartDtoList.get(0).getOption().getOptionName()).isEqualTo("2겹 식빵수세미 6매");

        Assertions.assertThat(cartDtoList.get(1).getOption().getId()).isEqualTo(10);
        Assertions.assertThat(cartDtoList.get(1).getOption().getPrice()).isEqualTo(49900);
        Assertions.assertThat(cartDtoList.get(1).getOption().getOptionName()).isEqualTo("JR310BT (무선 전용) - 레드");

        Assertions.assertThat(cartDtoList.get(2).getOption().getId()).isEqualTo(11);
        Assertions.assertThat(cartDtoList.get(2).getOption().getPrice()).isEqualTo(49900);
        Assertions.assertThat(cartDtoList.get(2).getOption().getOptionName()).isEqualTo("JR310BT (무선 전용) - 그린");

    }

    // 실행시간 : 275ms
    @Test
    public void update() {
        //given
        int cartId = 1;
        int quantity = 10;

        Cart cart = cartJPARepository.findById(cartId).orElseThrow(
                () -> new RuntimeException("해당 카트를 찾을 수 없습니다.")
        );
        //when

        cart.update(quantity, cart.getOption().getPrice() * quantity);
        em.flush();
        //then
        Assertions.assertThat(cart.getQuantity()).isEqualTo(quantity);
        Assertions.assertThat(cart.getPrice()).isEqualTo(cart.getOption().getPrice() * quantity);
    }
}
