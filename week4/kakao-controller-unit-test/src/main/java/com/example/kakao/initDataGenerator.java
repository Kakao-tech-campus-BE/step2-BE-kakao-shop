package com.example.kakao;

import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartService;
import com.example.kakao.product.ProductRequest;
import com.example.kakao.product.ProductService;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionRequest;
import com.example.kakao.product.option.OptionService;
import com.example.kakao.user.User;
import com.example.kakao.user.UserRequest;
import com.example.kakao.user.UserResponse;
import com.example.kakao.user.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class initDataGenerator implements ApplicationRunner {
    private final FakeStore fakeStore;
    private final UserService userService;
    private final ProductService productService;
    private final OptionService optionService;
    private final CartService cartService;

    public initDataGenerator(FakeStore fakeStore, UserService userService, ProductService productService, OptionService optionService, CartService cartService) {
        this.fakeStore = fakeStore;
        this.userService = userService;
        this.productService = productService;
        this.optionService = optionService;
        this.cartService = cartService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createProducts();
        createOptions();
        createUser();
        createCarts();
    }

    private void createUser() {
        UserRequest.JoinDTO request = new UserRequest.JoinDTO();
        request.setUsername("moonseokjun");
        request.setEmail("moon@naver.com");
        request.setPassword("qwer1234!");
        userService.join(request);
    }

    private void createProducts() {
        List<ProductRequest.Insert> requests = fakeStore.getProductList().stream().map(product -> ProductRequest.Insert.builder()
                        .name(product.getProductName())
                        .description(product.getDescription())
                        .image(product.getImage())
                        .price(product.getPrice())
                        .build())
                .collect(Collectors.toList());
        productService.saveAll(requests);
    }

    private void createOptions() {
        List<OptionRequest.Insert> optionRequests = fakeStore.getOptionList().stream().map(option -> OptionRequest.Insert.builder()
                        .product(option.getProduct())
                        .name(option.getOptionName())
                        .price(option.getPrice())
                        .build())
                .collect(Collectors.toList());
        optionService.saveAll(optionRequests);
    }

    private void createCarts() {
        UserResponse.FindById userResponse = userService.findById(1);
        User user = User.builder()
                .id(userResponse.getId())
                .email(userResponse.getEmail())
                .username(userResponse.getUsername())
                .build();

        Option option1 = optionService.findById(1);
        Option option2 = optionService.findById(12);

        cartService.save(user, Cart.builder()
                .user(user)
                .option(option1)
                .quantity(5)
                .price(option1.getPrice() * 5)
                .build()
        );

        cartService.save(user, Cart.builder()
                .user(user)
                .option(option2)
                .quantity(5)
                .price(option2.getPrice() * 5)
                .build());
    }
}
