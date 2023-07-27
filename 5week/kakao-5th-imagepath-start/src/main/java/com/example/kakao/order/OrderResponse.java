package com.example.kakao.order;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.example.kakao.cart.Cart;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class OrderResponse {

	// 결제하기 버튼을 눌렀을 때 response로 나갈 DTO
    @Getter @Setter @ToString
    public static class SaveDTO {
        @NotNull
        private Integer id;
        
        @NotNull
    	private List<ProductDTO> products;
        
        @NotNull
        private Integer totalPrice;
        
        public SaveDTO(List<Cart> cartList) {
            this.products = cartList.stream()
            		.map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, cartList)).collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity()).sum();
        }
        
        
        @Getter
        @Setter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Cart> cartList) {
                this.productName = product.getProductName();
                this.items = cartList.stream()
                		.filter(cart -> cart.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }
            
            @Getter
            @Setter
            public class ItemDTO {
                private int optionId;
                private String optionName;
                private int quantity;
                private int price;
                
                public ItemDTO(Cart cart) {
                	this.optionId = cart.getOption().getId();
                	this.optionName = cart.getOption().getOptionName();
                	this.quantity = cart.getQuantity();
                	this.price = cart.getPrice();
                }
                
            }
            
        }
    }
	
    // 결제 내역들에 대한 정보 DTO
    @Getter @Setter @ToString
    public static class findOrderDTO {
        @NotNull
        private Integer id;
        
        @NotNull
    	private List<ProductDTO> products;
        
        @NotNull
        private Integer totalPrice;
        
        public findOrderDTO(List<Item> itemList) {
            this.products = itemList.stream()
            		.map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, itemList)).collect(Collectors.toList());
            this.totalPrice = itemList.stream().mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity()).sum();
        }
        
        
        @Getter
        @Setter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> itemList) {
                this.productName = product.getProductName();
                this.items = itemList.stream()
                		.filter(item -> item.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }
            
            @Getter
            @Setter
            public class ItemDTO {
                private int optionId;
                private String optionName;
                private int quantity;
                private int price;
                
                public ItemDTO(Item item) {
                	this.optionId = item.getOption().getId();
                	this.optionName = item.getOption().getOptionName();
                	this.quantity = item.getQuantity();
                	this.price = item.getPrice();
                }
                
            }
            
        }
    }
    
    
}
