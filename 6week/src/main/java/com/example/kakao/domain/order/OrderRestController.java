package com.example.kakao.domain.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiResponse;
import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@RequestMapping("/orders")
@RestController
@Validated
public class OrderRestController {
  private final OrderService orderService;


  // (기능9) 결제하기 - (주문 인서트) POST
  @PostMapping("/save")
  public ResponseEntity<ApiResponse> save(@AuthenticationPrincipal CustomUserDetails userDetails) {

    return ResponseEntity.ok(ApiUtils.success( orderService.save(userDetails.getUser().getId()) ));
  }

  // (기능10) 주문 결과 확인 GET
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse> findById(
    @PathVariable(value = "id", required = true) @Positive int id,
    @AuthenticationPrincipal CustomUserDetails userDetails) {

    return ResponseEntity.ok(ApiUtils.success( orderService.findById( id, userDetails.getUser().getId()) ) );
  }
}
