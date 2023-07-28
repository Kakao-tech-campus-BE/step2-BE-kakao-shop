package com.example.kakao.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kakao.user.User;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

	// Order는 User를 Lazy로딩하고 있다. 하지만 이 쿼리를 사용하는 OrderService에서 id만을 필요로하기에 기본 쿼리도 괜찮을 것 같다.
	List<Order> findAllByUser(User sessionUser);

}
