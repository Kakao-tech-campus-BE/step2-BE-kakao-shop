package com.example.kakao.item;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.Order;
import com.example.kakao.order.OrderJPARepository;
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

import javax.persistence.EntityManager;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class ItemJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @BeforeEach
    public void setUp(){
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionDummy = optionJPARepository.saveAll(optionDummyList(productListPS));
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.clear();
    }

    @Test
    public void item_test() throws JsonProcessingException {
        // given
        int id = 1;
        int quantity = 5;
        int optionDummyId = 0;

        // when
        List<Option> optionDummy = optionJPARepository.mFindByProductId(id); // fetch join으로 N+1 문제 해결
        User user = userJPARepository.save(newUser("gijun"));
        Cart cart = cartJPARepository.save(newCart(user,optionDummy.get(optionDummyId),quantity));
        Order order = orderJPARepository.save(newOrder(user));
        Item item = itemJPARepository.save(newItem(cart,order));
        String responseBody = om.writeValueAsString(item);
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertThat(item.getId()).isEqualTo(1);
        Assertions.assertThat(item.getQuantity()).isEqualTo(5);
        Assertions.assertThat(item.getOption().getId()).isEqualTo(1);
        Assertions.assertThat(item.getPrice()).isEqualTo(50000);


    }



}
