package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OptionDTO;
import com.example.kakaoshop.order.response.OrderRespFindAllDTO;
import com.example.kakaoshop.order.response.ProductDTO;
import com.example.kakaoshop.product.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class OrderRestController {
	@PostMapping("/orders/save")
	public ResponseEntity<?> save() {
		List<OptionDTO> optionDTOList = new ArrayList<>();
		OptionDTO option1 = OptionDTO.builder()
				.id(4)
				.optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
				.quantity(10)
				.price(100000)
				.build();
		optionDTOList.add(option1);
		OptionDTO option2 = OptionDTO.builder()
				.id(5)
				.optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
				.quantity(10)
				.price(109000)
				.build();
		optionDTOList.add(option2);
		ProductDTO product1 = ProductDTO.builder()
				.productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
				.items(optionDTOList)
				.build();
		OrderRespFindAllDTO orderRespFindAllDTO = new OrderRespFindAllDTO(1, product1, 209000);
		return ResponseEntity.ok(ApiUtils.success(orderRespFindAllDTO));
	}
	@GetMapping("/orders/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		List<OptionDTO> optionDTOList = new ArrayList<>();
		OptionDTO option1 = OptionDTO.builder()
				.id(4)
				.optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
				.quantity(10)
				.price(100000)
				.build();
		OptionDTO option2 = OptionDTO.builder()
				.id(5)
				.optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
				.quantity(10)
				.price(109000)
				.build();
		optionDTOList.add(option1);
		optionDTOList.add(option2);
		ProductDTO product1 = ProductDTO.builder()
				.productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
				.items(optionDTOList)
				.build();
		OrderRespFindAllDTO orderRespFindAllDTO = new OrderRespFindAllDTO(1, product1, 209000);

		return ResponseEntity.ok(ApiUtils.success(orderRespFindAllDTO));
	}

}
