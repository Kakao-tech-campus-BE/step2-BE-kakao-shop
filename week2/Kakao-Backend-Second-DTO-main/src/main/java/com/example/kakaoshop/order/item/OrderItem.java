package com.example.kakaoshop.order.item;

import com.example.kakaoshop.order.Order;
import com.example.kakaoshop.product.Product;
import com.example.kakaoshop.product.option.ProductOption;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
/*@Table(name="order_item_tb",
        indexes = {
                @Index(name = "order_item_option_id_idx", columnList = "option_id"),
                @Index(name = "order_item_order_id_idx", columnList = "order_id")
        })*/


/*
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is org.hibernate.AnnotationException: Unable to create index (option_id) on table order_item_tb: database column 'option_id' not found. Make sure that you use the correct column name which depends on the naming strategy in use (it may not be the same as the property name in the entity, especially for relational types)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1804) ~[spring-beans-5.3.27.jar:5.3.27]

    이 부분이 FK로 데이터베이스에 넣을 방식을 조정하는것 같은데 오류를 냅니다.
    ProductOption 코드를 참고해서 짰는데 어느부분이 문제인지 모르겠습니다.
	오류명을 보면 option_id가 데이터베이스에 없어서? 인것같은데
	어떻게 해결해야 되는지 궁금합니다.
 */

public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductOption option_id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order_id;

    @Builder
    public OrderItem(int id, int quantity, int price, ProductOption option_id, Order order_id) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.option_id = option_id;
        this.order_id = order_id;
    }

}
