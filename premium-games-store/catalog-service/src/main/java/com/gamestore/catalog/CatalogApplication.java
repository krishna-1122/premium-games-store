package com.gamestore.catalog;

import com.gamestore.catalog.model.Game;
import com.gamestore.catalog.repository.GameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class CatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogApplication.class, args);
    }

    @Bean
    CommandLineRunner seedGames(GameRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.saveAll(List.of(
                    new Game("Cyberpunk 2077: Phantom Liberty", "Action RPG", "PC / PS5", new BigDecimal("59.99"), 4.9, "https://images.unsplash.com/photo-1542751371-adc38448a05e?w=600"),
                    new Game("Elden Ring: Shadow of the Erdtree", "Dark Fantasy", "PC / Xbox / PS5", new BigDecimal("69.99"), 5.0, "https://images.unsplash.com/photo-1538481199705-c710c4e965fc?w=600"),
                    new Game("Ghost of Tsushima Director's Cut", "Action Adventure", "PC / PS5", new BigDecimal("49.99"), 4.8, "https://images.unsplash.com/photo-1511512578047-dfb367046420?w=600"),
                    new Game("Black Myth: Wukong", "Action RPG", "PC / PS5", new BigDecimal("59.99"), 4.9, "https://images.unsplash.com/photo-1552820728-8b83bb6b773f?w=600")
                ));
            }
        };
    }
}
