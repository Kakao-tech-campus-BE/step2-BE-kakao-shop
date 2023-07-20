package com.example.kakao.order;


import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;

public class ItemDTO {
    private int id;
    private OptionDTO option;
    private OrderDTO order;
    private int quantity;
    private int price;

    public ItemDTO(Item item){
        this.id = item.getId();
        this.option = new OptionDTO(item.getOption());
        this.order = new OrderDTO(item.getOrder());
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OptionDTO getOption() {
        return option;
    }

    public void setOption(OptionDTO option) {
        this.option = option;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public class OrderDTO {

        private int id;
        private UserDTO user;

        public OrderDTO(Order order) {
            this.id = order.getId();
            this.user = new UserDTO(order.getUser());
        }

        public int getId() {return id;}

        public void setId(int id) {this.id = id;}

        public UserDTO getUser() {return user;}

        public void setUser(UserDTO user) {this.user = user;}
    }

    public class UserDTO{
        private int id;
        private String email;
        private String username;
        private String roles;

        public UserDTO(User user){
            this.id = user.getId();
            this.email = user.getEmail();
            this.username = user.getUsername();
            this.roles = user.getRoles();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }
    }

    public class OptionDTO {
        private int id;
        private ProductDTO product;
        private String optionName;
        private int price;

        public OptionDTO(Option option){
            this.id = option.getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.product = new ProductDTO(option.getProduct());
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ProductDTO getProduct() {
            return product;
        }

        public void setProduct(ProductDTO product) {
            this.product = product;
        }

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    public class ProductDTO {
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price; // 톡딜가

        public ProductDTO(Product product) {
            this.id = product.getId();
            this.description = product.getDescription();
            this.productName = product.getProductName();
            this.image = product.getImage();
            this.price = product.getPrice();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
