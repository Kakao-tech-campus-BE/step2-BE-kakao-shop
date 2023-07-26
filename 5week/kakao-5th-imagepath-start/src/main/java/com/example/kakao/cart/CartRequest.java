package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        @NotNull
        private int optionId;
        @NotNull
        private int quantity;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof SaveDTO) {
                SaveDTO s = (SaveDTO) obj;
                    if (this.optionId == s.getOptionId()) return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(optionId);
        }
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        @NotNull
        private int cartId;
        @NotNull
        private int quantity;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof UpdateDTO) {
                UpdateDTO s = (UpdateDTO) obj;
                if (this.cartId == s.getCartId()) return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(cartId);
        }
    }


}
