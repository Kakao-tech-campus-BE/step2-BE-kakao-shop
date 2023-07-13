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

    @Autowired
    private EntityManager em;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;


    @BeforeEach
    public void setUp(){

        User user = newUser("sonny");
        userJPARepository.save(user);

        Product product = newProduct("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000);
        productJPARepository.save(product);

        Option option1 = newOption(product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = newOption(product, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);

        optionJPARepository.save(option1);
        optionJPARepository.save(option2);

        Cart cart1 = newCart(user, option1, 10);
        Cart cart2 = newCart(user, option2, 10);

        List<Cart> carts = List.of(cart1,cart2);
        cartJPARepository.saveAll(carts);

        em.clear();
    }

    @Test
    public void order_save_test(){
        User user = userJPARepository.findByEmail("sonny@nate.com").orElseThrow(
                () -> new RuntimeException("회원이 존재하지 않습니다.")
        );

        Order order = newOrder(user);
        Order savedOrder = orderJPARepository.save(order);

        List<Cart> carts = cartJPARepository.findAllWithOptionsUsingFetchJoinByEmail("sonny@nate.com");

        List<Item> items = new ArrayList<>();
        for (Cart cart : carts) {
            items.add(newItem(cart, savedOrder));
        }

        List<Item> savedItems = itemJPARepository.saveAll(items);

        //then
        Assertions.assertThat(carts.size()).isEqualTo(savedItems.size());
        Assertions.assertThat(savedItems.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(savedItems.stream().mapToInt(s -> s.getPrice()).sum()).isEqualTo(209000);
    }

    @Test
    public void order_find_test(){

        // given
        int orderId = 1;

        User user = userJPARepository.findByEmail("sonny@nate.com").orElseThrow(
                () -> new RuntimeException("회원이 존재하지 않습니다.")
        );

        Order order = newOrder(user);
        Order savedOrder = orderJPARepository.save(order);

        List<Cart> carts = cartJPARepository.findAllWithOptionsUsingFetchJoinByEmail("sonny@nate.com");

        List<Item> items = new ArrayList<>();
        for (Cart cart : carts) {
            items.add(newItem(cart, savedOrder));
        }

        List<Item> savedItems = itemJPARepository.saveAll(items);
        // 2
        // when

        List<Item> findItems = itemJPARepository.findItemsWithOptionUsingFetchJoinById(orderId);
        // 1??
        //then

        Assertions.assertThat(savedItems.size()).isEqualTo(findItems.size());
        Assertions.assertThat(findItems.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(findItems.stream().mapToInt(s -> s.getPrice()).sum()).isEqualTo(209000);
    }














}




