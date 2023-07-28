package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.SimpleTimeZone;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter
    @Setter
    public static class SaveDTO{
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;
        public SaveDTO(List<Cart> cartList){
            this.products = cartList.stream()
                    .map(c -> c.getOption().getProduct()).distinct()
                    .map(p -> new ProductDTO(p, cartList.stream()
                            .filter(c -> c.getOption().getProduct().getId() == p.getId())
                            .map(c -> c.getOption())
                            .collect(Collectors.toList())))
                    .collect(Collectors.toList());
        }
        @Getter
        @Setter
        public class ProductDTO{
            private String productName;
            private List<OptionDTO> items;
            public ProductDTO(Product product, List<Option> optionList){
                this.productName = product.getProductName();
                this.items = optionList.stream()
                        .map(OptionDTO::new).collect(Collectors.toList());
            }
            @Getter
            @Setter
            public class OptionDTO{
                private int id;
                private String optionName;
                private int quantity;
                private int price;
                public OptionDTO(Option option){
                    this.id = option.getId();
                    this.optionName = option.getOptionName();
                    this.price = option.getPrice();
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
                    this.price = item.getOption().getPrice();
                }
            }
        }
    }
}
