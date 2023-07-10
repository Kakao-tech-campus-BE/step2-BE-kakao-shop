package com.example.kakaoshop.cart;

import com.example.kakaoshop.cart.request.OptionDTO;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class CartRequest {
  @Getter
  @Setter
  public static class AddDTO extends ArrayList<OptionDTO> {}

  @Getter
  @Setter
  public static class UpdateDTO extends ArrayList<OptionDTO> {}
}
