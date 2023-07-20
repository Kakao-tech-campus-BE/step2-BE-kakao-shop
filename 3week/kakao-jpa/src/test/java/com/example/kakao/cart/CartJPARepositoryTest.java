package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.ProductOption;
import com.example.kakao.product.option.ProductOptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private ProductOptionJPARepository productOptionJPARepository;

    @BeforeEach
    public void setUp() {
        User user1 = userJPARepository.save(newUser("ssar"));
        entityManager.persist(user1);
        userJPARepository.save(user1);

        Product product1 = newProduct("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000);
        entityManager.persist(product1);
        productJPARepository.save(product1);


        ProductOption productOption1 = newProductOption(product1,"01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        entityManager.persist(productOption1);
        productOptionJPARepository.save(productOption1);

        Cart cart1 = newCart(user1, productOption1, 20);
        entityManager.persist(cart1);
        cartJPARepository.save(cart1);

        entityManager.clear();
    }

    @Test
    public void findByUserIdTest() {
        // Given
        int userId = 1;

        // When
        List<Cart> result = cartJPARepository.findByUserId(userId);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void saveCartTest() {
        // Given
        User user = newUser("sohyun");
        Product product = newProduct("Test Product", 1, 100);
        ProductOption productOption = newProductOption(product,"Test Product Option", 200);

        userJPARepository.save(user);
        productJPARepository.save(product);
        productOptionJPARepository.save(productOption);

        Cart cart = newCart(user, productOption, 30);

        // When
        Cart savedCart = cartJPARepository.save(cart);

        // Then
        assertThat(savedCart.getQuantity()).isEqualTo(30);
        assertThat(savedCart.getUser().getUsername()).isEqualTo("sohyun");
    }


    @Test
    public void updateCartTest() {
        // Given
        User user = newUser("sohyun");
        Product product = newProduct("Test Product", 1, 500);
        ProductOption productOption = newProductOption(product,"Test Product Option", 200);

        userJPARepository.save(user);
        productJPARepository.save(product);
        productOptionJPARepository.save(productOption);

        Cart cart = newCart(user, productOption, 30);

        // When
        cart.update(20,1000);
        Cart updatedCart = cartJPARepository.save(cart);

        // Then
        assertThat(updatedCart.getQuantity()).isEqualTo(20);
        assertThat(updatedCart.getPrice()).isEqualTo(1000);
    }



    @Test
    public void deleteByUserIdTest() {
        // Given
        int userId = 1;

        // When
        cartJPARepository.deleteByUserId(userId);
        List<Cart> carts = entityManager.createQuery("SELECT c FROM Cart c WHERE c.user.id = :userId", Cart.class)
                .setParameter("userId", userId)
                .getResultList();

        // Then
        assertThat(carts).isEmpty();
    }


}