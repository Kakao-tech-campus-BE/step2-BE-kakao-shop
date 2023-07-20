package com.example.kakao.product.option;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.order.item.OrderItem;
import com.example.kakao.order.item.OrderItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductOptionJPARepositoryTest extends DummyEntity {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductOptionJPARepository productOptionRepository;

    @BeforeEach
    public void setUp() {
        Product product = newProduct("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000);
        entityManager.persist(product);

        ProductOption productOption1 = newProductOption(product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        entityManager.persist(productOption1);

        ProductOption productOption2 = newProductOption(product, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        entityManager.persist(productOption2);

        entityManager.flush();
    }

    @Test
    public void findByProductIdTest() {
        // Given
        int productId = 1;

        // When
        List<ProductOption> result = productOptionRepository.findByProductId(productId);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
    }


    @Test
    public void mFindByProductIdTest() {
        // Given
        int productId = 1;

        // When
        List<ProductOption> result = productOptionRepository.mFindByProductId(productId);
        //productRe.findById(productId)
        System.out.println(result.size());

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
    }


}
