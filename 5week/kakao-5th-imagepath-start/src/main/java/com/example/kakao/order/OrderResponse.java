package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.kakao.product.Product;


public class OrderResponse {

    @Getter @Setter
    public static class SaveDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public SaveDTO(Order order, List<Item> items){
            this.id = order.getId();
            this.products = items.stream()
                    //중복 상품 거르기
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new OrderResponse.SaveDTO.ProductDTO(product, items)).collect(Collectors.toList());
            this.totalPrice = items.stream().mapToInt(item -> item.getPrice()).sum();
        }

        @Getter @Setter
        public class ProductDTO {
            private String ProductName;
            private List<ItemDTO> items;

            public ProductDTO (Product product, List<Item> items){
                this.ProductName=product.getProductName();
                // 현재 상품과 동일한 장바구니 내역만 담기
                this.items = items.stream()
                        .filter(item -> item.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }
        }

        @Getter @Setter
        public class ItemDTO{
            private int id;
            private String OptionName;
            private int quantitiy;
            private int price;

            public ItemDTO (Item item){
                this.id = item.getId();
                this.OptionName=item.getOption().getOptionName();
                this.quantitiy=item.getQuantity();
                this.price=item.getPrice();
            }
        }
    }


    @Getter @Setter
    public static class findByIdDTO{
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;
        public findByIdDTO(Optional<Order> order, List<Item> items) {
            this.id = order.get().getId();
            this.products = items.stream()
                    //중복 상품 거르기
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, items)).collect(Collectors.toList());
            this.totalPrice = items.stream().mapToInt(item -> item.getPrice()).sum();
        }

        @Getter @Setter
        public class ProductDTO {
            private String ProductName;
            private List<ItemDTO> items;
            public ProductDTO (Product product, List<Item> items){
                this.ProductName=product.getProductName();
                // 현재 상품과 동일한 장바구니 내역만 담기
                this.items = items.stream()
                        .filter(item -> item.getOption().getProduct().getId() == product.getId())
                        .map(findByIdDTO.ItemDTO::new)
                        .collect(Collectors.toList());
            }
        }

        @Getter @Setter
        public class ItemDTO {
            private int id;
            private String OptionName;
            private int quantitiy;
            private int price;

            public ItemDTO(Item item) {
                this.id = item.getId();
                this.OptionName = item.getOption().getOptionName();
                this.quantitiy = item.getQuantity();
                this.price = item.getPrice();
            }
        }
    }
}
