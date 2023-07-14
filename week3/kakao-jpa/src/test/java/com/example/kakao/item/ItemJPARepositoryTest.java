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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Import(ObjectMapper.class)
@DataJpaTest
public class ItemJPARepositoryTest extends DummyEntity {
    private final OrderJPARepository orderJPARepository;
    private final ProductJPARepository productJPARepository;
    private final UserJPARepository userJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final EntityManager em;
    private final ObjectMapper om;

    @Autowired
    public ItemJPARepositoryTest(OrderJPARepository orderJPARepository, ProductJPARepository productJPARepository, UserJPARepository userJPARepository, OptionJPARepository optionJPARepository, CartJPARepository cartJPARepository, ItemJPARepository itemJPARepository, EntityManager em, ObjectMapper om) {
        this.orderJPARepository = orderJPARepository;
        this.productJPARepository = productJPARepository;
        this.userJPARepository = userJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.cartJPARepository = cartJPARepository;
        this.itemJPARepository = itemJPARepository;
        this.em = em;
        this.om = om;
    }

    @BeforeEach
    public void setUp() {
        User user = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartListPS = Arrays.asList(
                newCart(user, optionListPS.get(0), 5),
                newCart(user, optionListPS.get(1), 5),
                newCart(user, optionListPS.get(2), 10)
        );
        cartJPARepository.saveAll(cartListPS);
        Order order = orderJPARepository.save(newOrder(user));
        itemJPARepository.saveAll(cartListPS.stream()
                .map(x->newItem(x,order))
                .collect(Collectors.toList()));
        em.clear();
        System.out.println("-----before 완료-----");
    }
    @AfterEach
    public void close() {
        System.out.println("-----after 시작-----");
        em.clear();
        cartJPARepository.deleteAll();
        userJPARepository.deleteAll();
        productJPARepository.deleteAll();
        optionJPARepository.deleteAll();
        orderJPARepository.deleteAll();
        itemJPARepository.deleteAll();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.clear();
        System.out.println("-----after 완료-----");
    }

    @Test
    @DisplayName("OrderItem 아이디로 조회")
    public void item_findItemByItemId() throws JsonProcessingException {
        int id = 1;
        Item itemList = itemJPARepository.findById(id).orElse(null);


//        String responseBody = om.writeValueAsString(itemList);
//        System.out.println(responseBody);
    }

    @Test
    @DisplayName("Order 아이디로 조회")
    public void item_findByOrderId() throws  JsonProcessingException{
        int orderId = 1;
        List<Item> itemList = itemJPARepository.findByOrderId(orderId);


    }

}
