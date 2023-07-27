//package com.example.kakao.cart;
//
//import com.example.kakao._core.utils.FakeStore;
//import com.example.kakao.product.Product;
//import com.example.kakao.product.ProductJPARepository;
//import com.example.kakao.product.option.OptionJPARepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//@Import({ObjectMapper.class, FakeStore.class})
//@DataJpaTest
//public class CartJPARepositoryTest {
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private FakeStore fakeStore;
//
//    @Autowired
//    private ProductJPARepository productJPARepository;
//
//    @Autowired
//    private CartJPARepository cartJPARepository;
//
//    @Autowired
//    private ObjectMapper om;
//
//    @BeforeEach
//    public void setUp(){
//        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
//
//        em.clear();
//    }
//
//    @Test
//    public  void test(){
//        cartJPARepository.saveAll(fakeStore.getCartList());
//    }
//}
