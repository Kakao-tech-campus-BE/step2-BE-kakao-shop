package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    /*
        TODO :
            1. 주문 추가 테스트
            2. 주문 조회 테스트
     */

    @Autowired
    private EntityManager em;
    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private ItemJPARepository itemJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private CartJPARepository cartJPARepository;

    @BeforeEach
    public void setUp(){
        User userPS = userJPARepository.save(newUser("ikyeong"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        Cart cartPS1 = newCart(userPS, optionListPS.get(0), 5);
        Cart cartPS2 = newCart(userPS, optionListPS.get(1), 5);
        Order orderPS = orderJPARepository.save(newOrder(userPS));
        itemJPARepository.save(newItem(cartPS1, orderPS));
        itemJPARepository.save(newItem(cartPS2, orderPS));
        em.clear();
    }

    @Test
    public void item_findByOrderId_test(){
        //given
        int id = 1;
        //when
        List<Item> itemListPS = itemJPARepository.findAllByOrderId(id);
        //then
        Assertions.assertThat(itemListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getPrice()).isEqualTo(50000);
        Assertions.assertThat(itemListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(itemListPS.get(0).getOrder().getId()).isEqualTo(1);
    }

    @Test
    public void order_save_test(){
        //given
        User user = userJPARepository.findById(1).get();
        cartJPARepository.save(newCart(user, optionJPARepository.findById(1).get(), 5));
        cartJPARepository.save(newCart(user, optionJPARepository.findById(2).get(), 5));
        //when
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
        List<Item> itemList = new ArrayList<>();
        Order order = orderJPARepository.save(newOrder(user));
        for (Cart cart : cartList){
            itemList.add(newItem(cart, order));
        }
        itemJPARepository.saveAll(itemList);
        cartJPARepository.deleteAllByUserId(user.getId());
        //then
        System.out.println(cartJPARepository.findAllByUserId(user.getId()).size());
    }
}
