package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {


    @Getter
    @Setter
    public static class saveDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public saveDTO(int id, List<ProductDTO> products) {
            this.id = id;
            this.products = products;
            this.totalPrice = products.stream().map(ProductDTO::getItems)
                    .flatMap(List<ItemDTO>::stream)
                    .mapToInt(ItemDTO::getPrice).sum();
        }

        @Getter
        @Setter
        public static class ProductDTO {
            String productName;
            List<ItemDTO> items;
            //int productTotalPrice;

            public ProductDTO(String productName, List<Item> items) {
                this.productName = productName;
                this.items = items.stream().map(ItemDTO::new).collect(Collectors.toList());
                //this.productTotalPrice = items.stream().mapToInt(Item::getPrice).sum();
            }
        }

        @Getter
        @Setter
        public static class ItemDTO {
            int id;
            String optionName;
            int quantity;
            int price;


            public ItemDTO(Item item) {
                this.id = item.getId();
                this.optionName = item.getOption().getOptionName();
                this.quantity = item.getQuantity();
                this.price = item.getPrice();
            }
        }
    }

    @Getter
    @Setter
    public static class findByIdDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        findByIdDTO(int id, List<ProductDTO> products) {
            this.id = id;
            this.products = products;
            this.totalPrice = products.stream().map(findByIdDTO.ProductDTO::getItems)
                    .flatMap(List<findByIdDTO.ItemDTO>::stream)
                    .mapToInt(findByIdDTO.ItemDTO::getPrice).sum();
        }

        @Getter
        @Setter
        public static class ProductDTO {
            String productName;
            List<findByIdDTO.ItemDTO> items;

            public ProductDTO(String productName, List<Item> items) {
                this.productName = productName;
                this.items = items.stream().map(findByIdDTO.ItemDTO::new).collect(Collectors.toList());
            }
        }

        @Getter
        @Setter
        public static class ItemDTO {
            int id;
            String optionName;
            int quantity;
            int price;

            public ItemDTO(Item item) {
                this.id = item.getId();
                this.optionName = item.getOption().getOptionName();
                this.quantity = item.getQuantity();
                this.price = item.getPrice();
            }
        }
    }
}
