package com.example.kakao.item;

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
import com.example.kakao._core.util.DummyEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ItemJPARepositoryTest extends DummyEntity {

    @Autowired
    private ItemJPARepository itemJPARepository;
    
    @Autowired
    private OrderJPARepository orderJPARepository;
    
    @Autowired
    private CartJPARepository cartJPARepository;
    
    @Autowired
    private OptionJPARepository optionJPARepository;
    
    @Autowired
    private ProductJPARepository productJPARepository;
    
    @Autowired
    private UserJPARepository userJPARepository;

    @Test
    public void itemsFindByOrderIdTest() {
        // given
        User user = newUser("user1");
        Product product = newProduct("testProduct", 1, 10000);
        Option option = newOption(product, "testOption", 5000);
        Cart cart = newCart(user, option, 5);
        Order order = newOrder(user);
        Item item1 = newItem(cart, order);
        Item item2 = newItem(cart, order);

        userJPARepository.save(user);
        productJPARepository.save(product);
        optionJPARepository.save(option);
        cartJPARepository.save(cart);
        orderJPARepository.save(order);
        itemJPARepository.save(item1);
        itemJPARepository.save(item2);

        // when
        List<Item> foundItems = itemJPARepository.findByOrderId(order.getId());
        
        try {
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(foundItems);
            System.out.println("테스트: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // then
        assertEquals(2, foundItems.size());
        assertTrue(foundItems.contains(item1));
        assertTrue(foundItems.contains(item2));
    }
}
