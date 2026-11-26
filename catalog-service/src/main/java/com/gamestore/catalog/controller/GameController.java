package com.gamestore.catalog.controller;

import com.gamestore.catalog.model.Game;
import com.gamestore.catalog.repository.GameRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameRepository repo;

    public GameController(GameRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Game> getAllGames(@RequestParam(required = false) String genre) {
        if (genre != null && !genre.isBlank() && !genre.equalsIgnoreCase("ALL")) {
            return repo.findByGenreIgnoreCase(genre);
        }
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/promo/{code}")
    public ResponseEntity<Map<String, Object>> validatePromo(@PathVariable String code) {
        return switch (code.toUpperCase()) {
            case "NEXUS20" -> ResponseEntity.ok(Map.of("code", "NEXUS20", "discountPercent", 20, "valid", true));
            case "GAMER10" -> ResponseEntity.ok(Map.of("code", "GAMER10", "discountPercent", 10, "valid", true));
            default -> ResponseEntity.ok(Map.of("code", code, "discountPercent", 0, "valid", false));
        };
    }

    @PostMapping
    public Game addGame(@RequestBody Game game) {
        return repo.save(game);
    }
}
