package com.example.kakao.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {

    @Getter
    @Setter
    @ToString
    public static class SaveDTO {
        @NotNull
        private int id;

        @NotNull
        private List<ProductDTO> products;

        @NotNull
        private int totalPrice;
    }

    @Getter
    @Setter
    @ToString
    public static class ProductDTO {

        @NotNull
        private String productName;

        @NotNull
        private List<ItemDTO> items;

    }

    @Getter
    @Setter
    @ToString
    public static class ItemDTO {
        @NotNull
        private int id;

        @NotNull
        private String optionName;

        @NotNull
        private int quantity;

        @NotNull
        private int price;
    }
}
