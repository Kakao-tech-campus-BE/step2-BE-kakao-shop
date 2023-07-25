package com.example.kakao.product;
import lombok.Getter;
import lombok.Setter;
public class ProductResponse {
    @Getter
    @Setter
    public static class FindAllDTO {
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;
        
        // 깊은 복사
        public FindAllDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }
    }

    
}
