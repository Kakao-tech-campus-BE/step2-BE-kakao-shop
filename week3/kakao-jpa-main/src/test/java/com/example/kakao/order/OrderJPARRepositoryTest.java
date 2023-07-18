package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
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
public class OrderJPARRepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    //
    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        User user = newUser("ssar");
        userJPARepository.save(user);
        Order order = newOrder(user);
        orderJPARepository.save(order);

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        Option option = optionJPARepository.findById(1).get();
        Cart cart = newCart(user,option,2);
        cartJPARepository.save(cart);
        Item item = newItem(cart, order);
        itemJPARepository.save(item);

        em.clear();
    }

    @Test
    public void order_findById_test() throws JsonProcessingException {
        //given
        int id=1;
        //when
        List<Order> findOrder=orderJPARepository.findByUserId(id);
        //then
        Assertions.assertThat(findOrder.get(0).getUser().getUsername()).isEqualTo("ssar");
    }

    @Test
    public void orderitem_findById_test() throws JsonProcessingException {
        //given
        int id=1;
        //when
         Item item= itemJPARepository.findById(id).get();
        //then
        Assertions.assertThat(item.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    }
}
