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
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

@DisplayName("주문 JPA Test")
@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp()  {
        User user = newUser("ssar");
        userJPARepository.save(user);

        List<Product> productList = productJPARepository.saveAll(productDummyList());
        List<Option> optionList = optionJPARepository.saveAll(optionDummyList(productList));

        List<Cart> cartList = new ArrayList<>();
        cartList.add(newCart(user, optionList.get(0),5));
        cartList.add(newCart(user, optionList.get(1),5));
        cartJPARepository.saveAll(cartList);

        Order order = orderJPARepository.save(newOrder(user));
        itemJPARepository.save(newItem(cartList.get(0),order));
        itemJPARepository.save(newItem(cartList.get(1),order));
        em.clear();
    }

    @Test
    public void SaveOrderTest() throws JsonProcessingException{
        //given
        int id = 1;
        User user = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 이메일을 찾을 수 없습니다.")
        );

        //when
        List<Cart> cartList = cartJPARepository.mFindByUserId(id);

        Order order = newOrder(user);
        orderJPARepository.save(order);
        itemJPARepository.save(newItem(cartList.get(0), order));
        itemJPARepository.save(newItem(cartList.get(1), order));

        List<Item> itemList = itemJPARepository.findAll();

        /*String responseBody = om.writeValueAsString(itemList);
        System.out.println("테스트 : " + responseBody);


        List<Item> itemList = new ArrayList<>();
        itemList.add(newItem(cartList.get(0),order));
        itemList.add(newItem(cartList.get(1),order));
        itemJPARepository.saveAll(itemList);

        String responseBody = om.writeValueAsString(itemList);
        System.out.println("테스트 : " + responseBody);

         */
        //then
        Assertions.assertThat(itemList.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(itemList.get(1).getOption().getPrice()).isEqualTo(10900);
        Assertions.assertThat(itemList.get(0).getPrice()).isEqualTo(50000);
        Assertions.assertThat(itemList.get(1).getPrice()).isEqualTo(54500);
        Assertions.assertThat(itemList.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(itemList.get(1).getQuantity()).isEqualTo(5);
        Assertions.assertThat(itemList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(itemList.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");

        em.flush();

    }

    @Test
    public void findByOrderId(){
        int id = 1;

        List<Item> itemList = itemJPARepository.mFindByUserId(id);

        Assertions.assertThat(itemList.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(itemList.get(0).getPrice()).isEqualTo(50000);
        Assertions.assertThat(itemList.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(itemList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(itemList.get(0).getOption().getPrice()).isEqualTo(10000);
    }


}
