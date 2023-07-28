package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartResponse;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter
    @Setter
    public static class SaveDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public SaveDTO(List<Item> items) {
            this.id = items.get(0).getOrder().getId();
            this.products = items.stream().map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product,items)).collect(Collectors.toList());
            this.totalPrice = items.stream().mapToInt(item -> item.getPrice() * item.getQuantity()).sum();
        }

        @Getter
        @Setter
        public class ProductDTO {
            private int id;
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product,List<Item> items){
                this.id = product.getId();
                this.productName = product.getProductName();
                this.items = items.stream().map(item -> new ItemDTO(item.getId(),item.getOption().getOptionName(),
                        item.getQuantity(),item.getPrice())).collect(Collectors.toList());
            }

            @Getter
            @Setter
            public class ItemDTO {
                private int id;
                private String OptionName;
                private int quantity;
                private int price;


                public ItemDTO(int id, String optionName, int quantity, int price) {
                    this.id = id;
                    OptionName = optionName;
                    this.quantity = quantity;
                    this.price = price;
                }
            }
        }
    }

    @Getter
    @Setter
    public static class FindByIdDTO{
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public FindByIdDTO(List<Item> items) {
            this.id = items.get(0).getOrder().getId();
            this.products = items.stream().map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product,items)).collect(Collectors.toList());
            this.totalPrice = items.stream().mapToInt(item -> item.getPrice() * item.getQuantity()).sum();
        }

        @Getter
        @Setter
        public class ProductDTO {
            private int id;
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product,List<Item> items){
                this.id = product.getId();
                this.productName = product.getProductName();
                this.items = items.stream().map(item -> new ItemDTO(item.getId(),item.getOption().getOptionName(),
                        item.getQuantity(),item.getPrice())).collect(Collectors.toList());
            }

            @Getter
            @Setter
            public class ItemDTO {
                private int id;
                private String OptionName;
                private int quantity;
                private int price;


                public ItemDTO(int id, String optionName, int quantity, int price) {
                    this.id = id;
                    OptionName = optionName;
                    this.quantity = quantity;
                    this.price = price;
                }
            }
        }
    }


}
