package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c join fetch c.option o where c.user.id = :userId")
    List<Cart>findAllByUserIdJoinOption(int userId);

    @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.user.id = :userId")
    List<Cart>findByUserIdJoinOptionJoinProduct(int userId);

    @Query("select c from Cart c join fetch c.option o join  fetch  o.product p where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdJoinOptionJoinProductOrderByOptionIdAsc(int userId);

    void deleteByUserId(int userId);

    @Query("select c from Cart c  where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);


    List<Integer> findCartIdsByUserId(int userId);
}
