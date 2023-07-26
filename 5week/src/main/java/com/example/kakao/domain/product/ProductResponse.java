package com.example.kakao.domain.product;

import com.example.kakao.domain.product.option.Option;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {
  @Getter
  @Setter
  public static class FindAllDTO {
    private int id;
    private String productName;
    private String description;
    private String image;
    private long price;
    public FindAllDTO(Product product) {
      this.id = product.getId();
      this.productName = product.getProductName();
      this.description = product.getDescription();
      this.image = product.getImage();
      this.price = product.getPrice();
    }
  }

  @Getter @Setter
  public static class FindByIdDTO {
    private int id;
    private String productName;
    private String description;
    private String image;
    private long price;
    private int starCount; // 0~5
    private List<OptionDTO> options;

    public FindByIdDTO(Product product, List<Option> optionList) {
      this.id = product.getId();
      this.productName = product.getProductName();
      this.description = product.getDescription();
      this.image = product.getImage();
      this.price = product.getPrice();
      this.starCount = 5; // 요구사항에는 없는 Dummy Implementation
      this.options =
        optionList.stream().map(OptionDTO::new).collect(Collectors.toList());
    }
  }

  @Getter @Setter
  public static class OptionDTO {
    private int id;
    private String optionName;
    private long price;
    public OptionDTO(Option option) {
      this.id = option.getId();
      this.optionName = option.getOptionName();
      this.price = option.getPrice();
    }
  }




}
