package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity{

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        User ssar = userJPARepository.save(newUser("ssar"));
        Cart cart = cartJPARepository.save(newCart(ssar, optionJPARepository.findById(1), 5));
        cartJPARepository.save(newCart(ssar, optionJPARepository.findById(2), 5));
        Order order = orderJPARepository.save(newOrder(ssar));
        itemJPARepository.save(newItem(cart, order));
        em.clear();
    }

    @Test
    public void orders_save_test() throws JsonProcessingException {
        // given
        Optional<User> userOP = userJPARepository.findById(1);

        //when
        if(userOP.isPresent()){
            User userPS = userOP.get();
            Order order = orderJPARepository.save(newOrder(userPS));
            List<Cart> cartList = cartJPARepository.findByUserId(userPS.getId());
            List<Item> itemList = new ArrayList<>();
            for(Cart cart : cartList){
                System.out.println(itemList);
                itemList.add(itemJPARepository.save(newItem(cart, order)));
            }

            List<ItemDTO> itemDTOList = itemList.stream().map(item -> new ItemDTO(item)).collect(Collectors.toList());
            System.out.println("json 직렬화 직전========================");
            String responseBody = om.writeValueAsString(itemDTOList);
            System.out.println("테스트 : "+responseBody);
        }

        //then
    }

    @Test
    public void order_findByOrderId_test() throws JsonProcessingException{
        //given
        int id = 1;

        //when
        Order order = orderJPARepository.findById(id);
        List<Item> itemList = itemJPARepository.findByOrderId(id);
        List<ItemDTO> itemDTOList = itemList.stream().map(item -> new ItemDTO(item)).collect(Collectors.toList());
        System.out.println("json 직렬화 직전========================");
        String responseBody1 = om.writeValueAsString(order);
        String responseBody2 = om.writeValueAsString(itemDTOList);
        System.out.println("테스트 : "+responseBody1);
        System.out.println("테스트 : "+responseBody2);

    }

}
