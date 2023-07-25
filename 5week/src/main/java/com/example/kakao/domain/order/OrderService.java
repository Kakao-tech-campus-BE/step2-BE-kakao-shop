package com.example.kakao.domain.order;

import com.example.kakao._core.errors.exception.NotFoundException;
import com.example.kakao._core.errors.exception.UnAuthorizedException;
import com.example.kakao.domain.order.dto.OrderDetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {
  private final OrderJPARepository orderRepository;

  public OrderDetailResponseDTO findById(int id, int userId) {
    Order order = orderRepository.findById(id).orElseThrow(
      () -> new NotFoundException("주문 내역을 찾을 수 없습니다. : " + id)
    );

    if(order.getUser().getId() != userId) throw new UnAuthorizedException("주문내역을 확인할 권한이 없습니다.");

    return new OrderDetailResponseDTO();
  }
}
