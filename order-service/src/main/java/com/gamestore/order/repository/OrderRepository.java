package com.gamestore.order.repository;

import com.gamestore.order.model.GameOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<GameOrder, Long> {
    List<GameOrder> findByUsernameOrderByOrderedAtDesc(String username);
}
