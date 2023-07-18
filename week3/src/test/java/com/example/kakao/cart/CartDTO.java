package com.example.kakao.cart;

import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;

public class CartDTO {
    private int id;
    private UserDTO user   ;
    private OptionDTO option;
    private int quantity;
    private int price;

    public UserDTO getUser(){
        return this.user;
    }
    public OptionDTO getOption(){
        return this.option;
    }
    public CartDTO(Cart cart){
        this.id = cart.getId();
        this.user = new UserDTO(cart.getUser());
        this.option = new OptionDTO(cart.getOption());
        this.quantity = cart.getQuantity();
        this.price = cart.getPrice();
    }


    public static class UserDTO{
        private int id;
        private String name;
        private String email;
        public UserDTO(User user){
            this.id = user.getId();
            this.name = user.getUsername();
            this.email = user.getEmail();
        }

        public String getName(){
            return this.name;
        }

    }
    public static class OptionDTO{
        private int id;
        private int price;
        private String optionName;
        public OptionDTO(Option option){
            this.id = option.getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
        }
        public String getOptionName(){
            return this.optionName;
        }
        public int getPrice(){
            return this.price;
        }
        public int getId(){
            return this.id;
        }
    }
}
