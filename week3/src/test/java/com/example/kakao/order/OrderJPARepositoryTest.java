package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.CartJPARepositoryTest;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
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
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        User user = userJPARepository.save(newUser("ssal"));

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));

        Cart cart1 = cartJPARepository.save(newCart(user,  optionListPS.get(0),10));
        Cart cart2 = cartJPARepository.save(newCart(user,  optionListPS.get(1),10));

        em.clear();

    }

    @Test
    public void save_test(){
        //given
        User user = userJPARepository.findByUsername("ssal");
        Order order = orderJPARepository.save(newOrder(user));

        List<Cart> cartList = cartJPARepository.findByUserId(user.getId());
        //when
        // cart에 들어 있는 데이터를 지울 수 잇어야 함
        Item item1 = itemJPARepository.save(newItem( cartList.get(0),order));
        Item item2 = itemJPARepository.save(newItem( cartList.get(1),order));

        System.out.println("item1 : "+item1.getOrder().getUser().getUsername()+item1.getOption().getOptionName());
        System.out.println("item2 : "+item2.getOrder().getUser().getUsername()+item2.getOption().getOptionName());

        cartJPARepository.deleteAll(cartList);
        //then
        Assertions.assertThat(cartJPARepository.findByUserId(user.getId()).size()).isEqualTo(0);
        //item 확인
        Assertions.assertThat(item1.getOrder().getUser().getUsername()).isEqualTo("ssal");
        Assertions.assertThat(item1.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(item2.getOrder().getUser().getUsername()).isEqualTo("ssal");
        Assertions.assertThat(item2.getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");

    }
    @Test
    public void findById(){
        //given
        User user = userJPARepository.findByUsername("ssal");
        Order order = orderJPARepository.save(newOrder(user));
        List<Cart> cartList = cartJPARepository.findByUserId(user.getId());
        Item item1 = itemJPARepository.save(newItem( cartList.get(0),order));
        Item item2 = itemJPARepository.save(newItem( cartList.get(1),order));

        //when

        List<Item> itemList = itemJPARepository.findByOrderId(order.getId());
        //then
        Assertions.assertThat(itemList.get(0).getOrder().getUser().getUsername()).isEqualTo("ssal");
        Assertions.assertThat(itemList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(itemList.get(1).getOrder().getUser().getUsername()).isEqualTo("ssal");
        Assertions.assertThat(itemList.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
 }

}
