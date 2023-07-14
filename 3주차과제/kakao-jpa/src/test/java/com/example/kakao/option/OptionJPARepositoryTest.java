package com.example.kakao.option;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Optional;


@DataJpaTest
public class OptionJPARepositoryTest extends DummyEntity {

    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private ProductJPARepository productJPARepository;



    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        Product product = productJPARepository.save(newProduct("product1", 1, 10000));
        Option option = optionJPARepository.save(newOption(product,"optionName",1000));
        em.clear();
    }

    @Test
    public void option_mfindById_test(){
        // given
        int optionId =1;

        // when
        Optional<Option> option = optionJPARepository.mfindById(1);

        // then
        Assertions.assertThat(option.get().getId()).isEqualTo(1);
        Assertions.assertThat(option.get().getOptionName()).isEqualTo("optionName");
        Assertions.assertThat(option.get().getPrice()).isEqualTo(1000);

        Assertions.assertThat(option.get().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(option.get().getProduct().getProductName()).isEqualTo("product1");
        Assertions.assertThat(option.get().getProduct().getDescription()).isEqualTo("");
        Assertions.assertThat(option.get().getProduct().getImage()).isEqualTo("/images/1.jpg");
        Assertions.assertThat(option.get().getProduct().getPrice()).isEqualTo(10000);


    }


}
