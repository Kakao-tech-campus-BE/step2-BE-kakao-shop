package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter
    @Setter
    public static class FindAllDTO {

        private int id;
        private List<productDTO> products;
        private int totalPrice;



        public FindAllDTO(List<Item> itemList,int id){
            this.id = id;
            this.products = itemList.stream()
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(products -> new productDTO(products, itemList)).distinct().collect(Collectors.toList());
            this.totalPrice = itemList.stream().mapToInt(item -> item.getPrice() * item.getQuantity()).sum();
        }

        @Getter
        @Setter
        public class productDTO {
            private String productName;
            private List<ItemsDTO> items;

            public productDTO(Product product, List<Item> itemList){
                this.productName = product.getProductName();
                this.items = itemList.stream()
                        .map(item -> new ItemsDTO(item)).collect(Collectors.toList());
            }

            @Getter
            @Setter
            public class ItemsDTO {
                private int id;
                private String optionName;
                private int quantity;
                private int price;

                public ItemsDTO(Item item){
                    this.id = item.getId();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getPrice();
                }
            }
        }


    }

}