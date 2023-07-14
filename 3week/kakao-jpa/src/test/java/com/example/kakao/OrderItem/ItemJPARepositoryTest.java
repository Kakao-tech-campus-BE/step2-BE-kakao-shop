package com.example.kakao.OrderItem;

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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
public class ItemJPARepositoryTest extends DummyEntity {
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private ItemJPARepository itemJPARepository;
    @Autowired
    private EntityManager em;

    private User user;
    private Option option;
    private Cart cart;
    private Order order;

    @BeforeEach
    public void setUp(){
        user = newUser("jiyoon");
        userJPARepository.save(user);
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        option=optionJPARepository.findById(1).get();
        cart = newCart(user,option, 5);
        cartJPARepository.save(cart);
        order=newOrder(user);
        orderJPARepository.save(order);
        Item item=newItem(cart,order);
        itemJPARepository.save(item);
        em.clear();
    }

    @Test // id(pk)로 Order Item 검색
    public void item_findById_test(){
        //given
        int id=1;
        //when
        Item findItem=itemJPARepository.findById(id).get();
        //then
        Assertions.assertThat(findItem.getId()).isEqualTo(id);
        Assertions.assertThat(findItem.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(findItem.getOrder().getUser().getUsername()).isEqualTo("jiyoon");
        Assertions.assertThat(findItem.getPrice()).isEqualTo(50000);
    }

    @Test // Order id로 Order Item 검색
    public void item_findByOrderId_Test(){
        //given
        int orderId=1;
        //when
        List<Item> findItems= itemJPARepository.findByOrderId(orderId);
        //then
        Assertions.assertThat(findItems.get(0).getId()).isEqualTo(orderId);
        Assertions.assertThat(findItems.get(0).getOrder().getUser().getUsername()).isEqualTo("jiyoon");
        Assertions.assertThat(findItems.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(findItems.get(0).getPrice()).isEqualTo(50000);
    }
}