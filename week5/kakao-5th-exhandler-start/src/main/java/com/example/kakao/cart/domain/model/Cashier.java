package com.example.kakao.cart.domain.model;

import com.example.kakao.product.domain.model.Product;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@Component
@Builder
public class Cashier {
    private List<Cart>existingCarts;

    public Cashier(List<Cart>carts) {
        this.existingCarts = carts;
    }

    public Set<Map.Entry<Product, List<Cart>>> groupByProduct() {
        return existingCarts.stream()
                .collect(Collectors.groupingBy(x -> x.getProductOption().getProduct()))
                .entrySet();
    }

    private List<OrderDetail> getOrderDetails() {
        return existingCarts.stream()
                .map(c -> OrderDetail.newInstance(c.getProductOption().getPrice(), c.getQuantity()))
                .collect(Collectors.toList());
    }

    public int calculateTotalPrice() {
        return getOrderDetails()
                .stream()
                .mapToInt(OrderDetail::calculate)
                .sum();
    }

    public boolean validateExisting(Long optionId){
        return existingCarts.stream()
                .anyMatch(x->x.isSameOption(optionId));
    }
}
