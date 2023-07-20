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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private ItemJPARepository itemJPARepository;
    @BeforeEach
    public void setUp(){
        User ssar = userJPARepository.save(newUser("ssar"));
        List<Product> productList = productJPARepository.saveAll(productDummyList());
        List<Option> optionList = optionJPARepository.saveAll(optionDummyList(productList));
        List<Cart> cartList = cartJPARepository.saveAll(cartDummyList(ssar, optionList));
        Order order = orderJPARepository.save(newOrder(ssar));

        itemJPARepository.saveAll(itemDummyList(cartList , order));
        em.clear();
    }
    @DisplayName("OrderId별 주문 결과 조회")
    @Test
    public void item_findByOrderId_test() throws JsonProcessingException {
        // given
        int orderId = 1;

        // when
        List<Item> itemList = itemJPARepository.findByOrderId(orderId);

        System.out.println("사용자별 주문 결과 조회========================");
        String responseBody = om.writeValueAsString(itemList);
        System.out.println("결과값 : " +responseBody);

        // then
        Assertions.assertThat(itemList.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(itemList.get(0).getQuantity()).isEqualTo(1);
        Assertions.assertThat(itemList.get(0).getPrice()).isEqualTo(10000);
        Assertions.assertThat(itemList.get(0).getOrder().getUser()).isEqualTo(new User(1, "ssar@nate.com", "meta1234!", "ssar", "ROLE_USER"));
        Assertions.assertThat(itemList.get(0).getOrder().getId()).isEqualTo(1);
        Assertions.assertThat(itemList.get(0).getOption().getProduct()).isEqualTo(new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000));
        Assertions.assertThat(itemList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(itemList.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(itemList.get(0).getOption().getId()).isEqualTo(1);
    }
    @DisplayName("아이템 id별 주문 결과 조회")
    @Test
    public void item_findByItemId_test() throws JsonProcessingException {
        // given
        int itemId = 1;

        // when
        Optional<Item> item = itemJPARepository.findByItemId(itemId);

        if (item.isPresent()) {
            Item myItem = item.get();
            System.out.println("아이템 id별  주문 결과 조회========================");
            String responseBody = om.writeValueAsString(myItem);
            System.out.println("결과값 : " + responseBody);

            // then
            Assertions.assertThat(myItem.getId()).isEqualTo(1);
            Assertions.assertThat(myItem.getQuantity()).isEqualTo(1);
            Assertions.assertThat(myItem.getPrice()).isEqualTo(10000);
            Assertions.assertThat(myItem.getOrder().getUser()).isEqualTo(new User(1, "ssar@nate.com", "meta1234!", "ssar", "ROLE_USER"));
            Assertions.assertThat(myItem.getOrder().getId()).isEqualTo(1);
            Assertions.assertThat(myItem.getOption().getProduct()).isEqualTo(new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000));
            Assertions.assertThat(myItem.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
            Assertions.assertThat(myItem.getOption().getPrice()).isEqualTo(10000);
            Assertions.assertThat(myItem.getOption().getId()).isEqualTo(1);
        }
        else {
            System.out.println("아이템이 존재하지 않습니다.");
        }
    }
}
