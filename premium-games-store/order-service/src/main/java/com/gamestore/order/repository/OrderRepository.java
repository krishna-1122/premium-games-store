package com.gamestore.order.repository;

import com.gamestore.order.model.GameOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<GameOrder, Long> {
}
