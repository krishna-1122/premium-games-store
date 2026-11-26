package com.gamestore.catalog.repository;

import com.gamestore.catalog.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByGenreIgnoreCase(String genre);
}
