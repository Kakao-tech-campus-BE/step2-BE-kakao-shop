package com.example.kakao.order.item;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.option.Option;
import com.example.kakao.order.Order;
import com.example.kakao.product.Product;
import com.example.kakao.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ItemRepositoryTest {
    @Autowired
    private ItemJPARepository itemJPARepository;

    @Test
    public void testSaveAndFind() {
        DummyEntity dummy = new DummyEntity();

        User user = dummy.newUser("testUser");
        Product product = dummy.newProduct("testProduct", 1, 1000);
        Option option = dummy.newOption(product, "testOption", 1000);
        Order order = dummy.newOrder(user);

        Item item = Item.builder()
                .order(order)
                .option(option)
                .quantity(2)
                .price(2000)
                .build();

        itemJPARepository.save(item);

        List<Item> items = itemJPARepository.findAll();
        assertThat(items).hasSize(1);

        Item retrievedItem = items.get(0);
        assertThat(retrievedItem.getQuantity()).isEqualTo(2);
        assertThat(retrievedItem.getPrice()).isEqualTo(2000);
        assertThat(retrievedItem.getOption()).isEqualTo(option);
        assertThat(retrievedItem.getOrder()).isEqualTo(order);
    }
}
