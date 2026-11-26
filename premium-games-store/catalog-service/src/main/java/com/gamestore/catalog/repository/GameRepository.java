package com.gamestore.catalog.repository;

import com.gamestore.catalog.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
