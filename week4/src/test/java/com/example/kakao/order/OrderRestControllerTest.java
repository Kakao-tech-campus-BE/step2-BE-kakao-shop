package com.example.kakao.order;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.Item;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {
    private final FakeStore fakeStore;

    public OrderRestControllerTest(FakeStore fakeStore) {
        this.fakeStore = fakeStore;
    }

    public void save_test() throws Exception {
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
    }

    public void findById_test() throws Exception {
        // 뭐가 뭔지 잘 모르겠다. 기초부터 공부는 계속 하는 중... ㅠㅠㅠ
    }
}