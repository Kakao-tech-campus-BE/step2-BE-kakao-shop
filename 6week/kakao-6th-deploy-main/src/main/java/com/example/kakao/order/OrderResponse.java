package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter
    @Setter
    public static class SaveDTO{
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;
        public SaveDTO(Order order, List<Cart> cartList){
            this.id = order.getId();
            this.products = cartList.stream()
                    .map(c -> c.getOption().getProduct()).distinct()
                    .map(p -> new ProductDTO(p, cartList.stream()
                            .filter(c -> c.getOption().getProduct().getId() == p.getId())
                            .collect(Collectors.toList())))
                    .collect(Collectors.toList());
            this.totalPrice = cartList.stream()
                    .mapToInt(Cart::getPrice).sum();
        }
        @Getter
        @Setter
        public class ProductDTO{
            private String productName;
            private List<OptionDTO> items;
            public ProductDTO(Product product, List<Cart> cartList){
                this.productName = product.getProductName();
                this.items = cartList.stream()
                        .map(OptionDTO::new).collect(Collectors.toList());
            }
            @Getter
            @Setter
            public class OptionDTO{
                private int id;
                private String optionName;
                private int quantity;
                private int price;
                public OptionDTO(Cart cart){
                    this.id = cart.getOption().getId();
                    this.optionName = cart.getOption().getOptionName();
                    this.price = cart.getPrice();
                    this.quantity = cart.getQuantity();
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

        public FindByIdDTO(Order order, List<Item> itemList){
            this.id = order.getId();
            this.products = itemList.stream()
                    .map(i -> i.getOption().getProduct()).distinct()
                    .map(p -> new ProductDTO(p, itemList))
                    .collect(Collectors.toList());
            this.totalPrice = itemList.stream()
                    .mapToInt(Item::getPrice).sum();
        }

        @Getter
        @Setter
        public class ProductDTO{
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> itemList){
                this.productName = product.getProductName();
                this.items = itemList.stream()
                        .filter(i -> i.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            @Setter
            public class ItemDTO{
                private int id;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Item item){
                    this.id = item.getOption().getId();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getPrice();
                }
            }
        }
    }
}
