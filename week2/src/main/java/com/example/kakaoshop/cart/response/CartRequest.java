package com.example.kakaoshop.cart.response;

import lombok.Getter;
import lombok.Setter;

public class CartRequest {
	@Getter
	@Setter
	public static class UpdateDTO {
		private int cartId;
		private int quantity;
	}
}
