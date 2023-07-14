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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        userJPARepository.save(newUser("temp"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        em.clear();
    }


    @Test
    public void order_save_test(){
        String email = "temp@nate.com";
        User userPS = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("이메일을 찾을 수 없습니다.")
        );
        Order orderPS = orderJPARepository.save(newOrder(userPS));
        List<Option> optionListPS = optionJPARepository.findAll();
        List<Cart> cartListPS = cartJPARepository.saveAll(cartDummyList(userPS, optionListPS));
        itemJPARepository.saveAll(orderItemDummyList(cartListPS, orderPS));

    }

    @Test
    public void order_findByUserId_test() throws JsonProcessingException {
        String email = "temp@nate.com";
        User userPS = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("이메일을 찾을 수 없습니다.")
        );
        List<Option> optionListPS = optionJPARepository.findAll();
        List<Cart> cartListPS = cartJPARepository.saveAll(cartDummyList(userPS, optionListPS));
        itemJPARepository.saveAll(orderItemDummyList(cartListPS, orderJPARepository.save(newOrder(userPS))));

        Order orderPS = orderJPARepository.findByUserId(userPS.getId());
        List<Item> itemPS = itemJPARepository.findByOrderId(orderPS.getId());

        String responseBody = om.writeValueAsString(orderPS);
        System.out.println("테스트1 : "+responseBody);

        responseBody = om.writeValueAsString(itemPS);
        System.out.println("테스트2 : "+responseBody);

    }
}
