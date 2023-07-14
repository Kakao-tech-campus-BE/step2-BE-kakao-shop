package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em; //테스트 쿼리 작성하기 위해서 추가하였음

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        List<User> userListPS = userJPARepository.saveAll(userDummyList());
        orderJPARepository.saveAll(orderDummyList(userListPS));
        em.clear();

        System.out.println("----------추가 완료---------");
    }

    @DisplayName("유저의 주문리스트(상세정보X) 확인(최신순)")
    @Test
    public void user_readOrders_test() throws JsonProcessingException {
        // given
        int userId = 1; // 설정해야 하는 상품 ID

        // when
        List<Order> orderListPS = orderJPARepository.findByUserIdWithJoinFetch(userId);

        String responseBody = om.writeValueAsString(orderListPS);
        System.out.println("테스트 --------" +responseBody);

        //영속성 컨텍스트 비워서 DB에서 가져오기

        // then
        Assertions.assertThat(orderListPS.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(orderListPS.get(0).getUser().getUsername()).isEqualTo("user1");
        Assertions.assertThat(orderListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(orderListPS.get(1).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(orderListPS.get(1).getUser().getUsername()).isEqualTo("user1");
        Assertions.assertThat(orderListPS.get(1).getId()).isEqualTo(2);


    }

    @DisplayName("유저의 주문 추가")
    @Test
    public void newOrder_addedToUser_checkPersistence() {
        // given
        int userId = 1;

        User user = userJPARepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Order newOrder = newOrder(user);

        // when
        orderJPARepository.save(newOrder);

        //영속성 컨텍스트 비워서 DB에서 가져오기
        em.clear();

        // then

        User orderAddedUser = userJPARepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Order addedOrder = orderJPARepository.findByUserAndId(orderAddedUser,newOrder.getId());

        Assertions.assertThat(addedOrder.getId()).isEqualTo(newOrder.getId());

    }


    /*결제 모듈도 고려해야할듯하다
    만들다가 느낀사실이지만.. OrderItem이 모여야 Order이 구성이 되는건데 이러한 테스트가 유효한가?
    사용되지 않을 로직같다. 이 테스트를 보더라도 OrderItem이 먼저 삭제되지 않으면 오류를 일으켜야 한다(cascade 옵션도 없음, 삭제되어도 쓸모없어지는 orderItem이 DB 남아있음)
    option-product의 경우와는 다르다고 생각한다.(판매자 입장에서 상품을 관리하는 경우에 사용된다고 생각함)
    생각하기에는 마이페이지 - 주문관리(간략하게 보여지는 주문) 에서만 사용될 맨 위의 조회 테스트만 필요하다고 생각한다.
    또한 주문 변경은 왜 없느냐? = 결제가 진행되었을텐데 이를 수정하는 로직을 구현하는건 위험할것 같다. */

    @DisplayName("유저의 주문 삭제")
    @Test
    public void Order_deletedToUser_checkPersistence() {
        // given
        int userId = 1;
        List<Order> orderListPS = orderJPARepository.findByUserIdWithJoinFetch(userId);

        // when
        orderListPS.forEach(order -> {
            orderJPARepository.deleteById(order.getId());
        });

        //영속성 컨텍스트 비워서 DB에서 가져오기
        em.flush();
        em.clear();

        // then
        List<Order> deletedOrders = orderJPARepository.findByUserIdWithJoinFetch(userId);

        Assertions.assertThat(deletedOrders).isEmpty();
    }






}
