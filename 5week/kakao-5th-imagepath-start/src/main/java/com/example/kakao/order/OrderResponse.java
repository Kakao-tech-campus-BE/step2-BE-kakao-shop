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

        private int orderId;
        private List<ProductDTO> products;
        private int totalPrice;

        public SaveDTO(Order order, List<Item> itemList){
            this.orderId = order.getId();
            this.products = itemList.stream()
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, itemList)).collect(Collectors.toList());
            this.totalPrice = itemList.stream().mapToInt(item -> item.getOption().getPrice() * item.getQuantity()).sum();
        }
        @Getter
        @Setter
        public class ProductDTO{
            public String productName;
            public List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> itemList){
                this.productName = product.getProductName();
                this.items = itemList.stream()
                        .filter(item -> item.getOption().getProduct().getId() ==  product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            @Setter
            public class ItemDTO{
                public int itemId;
                public String optionName;
                public int quantity;
                public int price;

                public ItemDTO(Item item){
                    this.itemId = item.getId();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getPrice();
                }
            }
        }
    }

    @Getter
    @Setter
    public static class FindByIdDTO{

        private int orderId;
        private List<ProductDTO> products;
        private int totalPrice;

        public FindByIdDTO(int orderId, List<Item> itemList){
            this.orderId = orderId;
            this.products = itemList.stream()
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, itemList)).collect(Collectors.toList());
            this.totalPrice = itemList.stream().mapToInt(item -> item.getOption().getPrice() * item.getQuantity()).sum();
        }
        @Getter
        @Setter
        public class ProductDTO{
            public String productName;
            public List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> itemList){
                this.productName = product.getProductName();
                this.items = itemList.stream()
                        .filter(item -> item.getOption().getProduct().getId() ==  product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            @Setter
            public class ItemDTO{
                public int itemId;
                public String optionName;
                public int quantity;
                public int price;

                public ItemDTO(Item item){
                    this.itemId = item.getId();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getPrice();
                }
            }
        }
    }
}
