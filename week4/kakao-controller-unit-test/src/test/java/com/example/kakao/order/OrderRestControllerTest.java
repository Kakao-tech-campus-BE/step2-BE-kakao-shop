package com.example.kakao.order;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.ProductRestController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
class OrderRestControllerTest {

    @Test
    void save() {
    }

    @Test
    void findById() {
    }
}