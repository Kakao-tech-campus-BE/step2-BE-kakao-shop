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
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
public class ItemJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private ItemJPARepository itemJPARepository;

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
    private ObjectMapper om;

    @org.junit.jupiter.api.Order(1)
    @BeforeEach
    public void set_up1() {
        // cart, option, order 엔티티 생성 후 저장
        User user1 = userJPARepository.save(newUser("kevin"));
        User user2 = userJPARepository.save(newUser("dodo"));

        Product product = productJPARepository.save(newProduct("짱맛", 1, 24000));

        orderJPARepository.save(newOrder(user1)); // 왜 order는 바로 merge로 넘어 가는거야??

        // 왜 merge가 되는거지?? 또 다른 save가 아니라?
        orderJPARepository.save(newOrder(user2));

        Option option = optionJPARepository.save(newOption(product, "옵션1", 2000));

        cartJPARepository.save(newCart(user1, option, 2));

    }

    @org.junit.jupiter.api.Order(2)
    @BeforeEach
    public void set_up2() {
        Optional<Cart> cart = cartJPARepository.findById(1);

        Optional<Order> order1 = orderJPARepository.findById(1);
        Optional<Order> order2 = orderJPARepository.findById(2);

        Item item1 = newItem(cart.get(), order1.get());
        Item item2 = newItem(cart.get(), order2.get());

        List<Item> itemList = new ArrayList<>();

        itemList.add(item1);
        itemList.add(item2);

        itemJPARepository.saveAll(itemList);
    }

    // (기능12) 결재 -> saveAll
    @Test
    public void save_all_item_test() {
        // give
        Optional<Cart> cart = cartJPARepository.findById(1);

        Optional<Order> order1 = orderJPARepository.findById(1);
        Optional<Order> order2 = orderJPARepository.findById(2);

        Item item1 = newItem(cart.get(), order1.get());
        Item item2 = newItem(cart.get(), order2.get());

        List<Item> itemList = new ArrayList<>();

        itemList.add(item1);
        itemList.add(item2);

        // when
        List<Item> itemOPList = itemJPARepository.saveAll(itemList);

        // then
        Assertions.assertThat(itemOPList.size()).isEqualTo(2);
    }

    // (기능13) 주문 결과 확인
    @Test
    public void find_items_test() {
        // give
        Optional<Order> order1 = orderJPARepository.findById(1);

        // when
        List<Item> items = itemJPARepository.findAllByOrder(order1.get());

        // then
        Assertions.assertThat(items.get(0).getId()).isEqualTo(1);
    }
}
