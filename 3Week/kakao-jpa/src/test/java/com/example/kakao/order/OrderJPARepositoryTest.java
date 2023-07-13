package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    private final EntityManager em;

    private final ObjectMapper om;

    private final UserJPARepository userJPARepository;

    private final CartJPARepository cartJPARepository;

    private final OptionJPARepository optionJPARepository;

    private final ProductJPARepository productJPARepository;

    private final OrderJPARepository orderJPARepository;

    private final ItemJPARepository itemJPARepository;

    @Autowired
    public OrderJPARepositoryTest(
            EntityManager em,
            ObjectMapper om,
            UserJPARepository userJPARepository,
            OptionJPARepository optionJPARepository,
            ProductJPARepository productJPARepository,
            OrderJPARepository orderJPARepository,
            CartJPARepository cartJPARepository,
            ItemJPARepository itemJPARepository) {
        this.em = em;
        this.om = om;
        this.userJPARepository = userJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.productJPARepository = productJPARepository;
        this.orderJPARepository = orderJPARepository;
        this.cartJPARepository = cartJPARepository;
        this.itemJPARepository = itemJPARepository;
    }

    @BeforeEach
    public void setUp(){
        User ssar = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartListPS = cartJPARepository.saveAll(cartDummyList(ssar, optionListPS));
        Order order = orderJPARepository.save(newOrder(ssar));
        itemJPARepository.saveAll(itemDummyList(cartListPS, order));

        em.clear();
    }
}
