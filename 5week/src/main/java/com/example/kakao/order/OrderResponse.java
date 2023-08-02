package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    public static class FindByIdDTO {

        private List<Order> user;
        private int id;
        private List<Cart> carts;
        private List<ProductDTO> products;
        private List<Order> orders;
        private int totalPrice;
        private List<SaveDTO> saves;


        public FindByIdDTO(int id, List<Order> orderList,List<Item> items) {
            this.id = orders.get();
            this.orders = orderList;
            this.products = items.stream()
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(items, product)).collect(Collectors.toList());
            this.totalPrice = items.stream().mapToInt(Item::getPrice).sum();
        }


        @Getter
        @Setter

        public class ProductDTO {
            private int productId;
            private String productName;
            private int price;
            private int quantity;
            private List<Item> items;

            public ProductDTO(List<Item> items, Product product) {
                this.productId = product.getId();
                this.productName = product.getProductName();
                this.price = product.getPrice();
            }
        }

        @Getter
        @Setter
        public class CartDTO {
            private int id;
            private int optionName;
            private int quantity;
            private int price;

            public CartDTO(Cart cart) {
                this.id = cart.getId();
                this.optionName = cart.getOption().getId();
                this.quantity = cart.getQuantity();
                this.price = cart.getOption().getPrice() * cart.getQuantity();
            }

            @Getter
            @Setter
            public class ItemDTO {
                private int id;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Item item) {
                    this.id = item.getId();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getPrice();


                }
            }
        }
    }

    public class SaveDTO {
        private int id;
        private User user;
        private List<FindByIdDTO.ProductDTO> productDTOS;

        public SaveDTO(Order order, User user, List<Item> itemList) {
            this.id = order.getId();
            this.productDTOS = itemList.stream()
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new FindByIdDTO.ProductDTO(item -> item.getOption().getPrice * item.getQuantity()).sum());
        }
        @Getter
        @Setter
        public class UserDTO{
            private int userid;

            public UserDTO(User user) {
                this.userid = user.getId();
            }
        }
    }

}
