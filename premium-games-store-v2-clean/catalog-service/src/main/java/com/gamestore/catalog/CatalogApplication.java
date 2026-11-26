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
                    new Game("Cyberpunk 2077: Phantom Liberty", "RPG", "PC / PS5", new BigDecimal("59.99"), 4.9, "https://images.unsplash.com/photo-1542751371-adc38448a05e?w=600", "Night City espionage-thriller expansion with ray-traced overdrive."),
                    new Game("Elden Ring: Shadow of the Erdtree", "RPG", "PC / Xbox / PS5", new BigDecimal("69.99"), 5.0, "https://images.unsplash.com/photo-1538481199705-c710c4e965fc?w=600", "Explore the Land of Shadow in FromSoftware's monumental expansion."),
                    new Game("Ghost of Tsushima Director's Cut", "Action", "PC / PS5", new BigDecimal("49.99"), 4.8, "https://images.unsplash.com/photo-1511512578047-dfb367046420?w=600", "Forge a new path and wage an unconventional war for the freedom of Tsushima."),
                    new Game("Black Myth: Wukong", "Action", "PC / PS5", new BigDecimal("59.99"), 4.9, "https://images.unsplash.com/photo-1552820728-8b83bb6b773f?w=600", "Rooted in Chinese mythology, confront legendary foes as the Destined One."),
                    new Game("Valorant Tactical Vault Bundle", "Shooter", "PC", new BigDecimal("39.99"), 4.7, "https://images.unsplash.com/photo-1542751110-97427bbecf20?w=600", "Exclusive protocol weapon skins, radianite points, and battle pass."),
                    new Game("Forza Horizon 5 Premium Edition", "Racing", "PC / Xbox", new BigDecimal("44.99"), 4.8, "https://images.unsplash.com/photo-1511919884226-fd3cad34687c?w=600", "Open-world racing across vibrant Mexican landscapes with VIP car pass.")
                ));
            }
        };
    }
}
