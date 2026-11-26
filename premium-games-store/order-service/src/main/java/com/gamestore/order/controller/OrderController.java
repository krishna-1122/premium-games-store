package com.gamestore.order.controller;

import com.gamestore.order.model.GameOrder;
import com.gamestore.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderRepository repo;
    private final RestClient restClient;

    public OrderController(OrderRepository repo, @Value("${catalog.service.url}") String catalogUrl) {
        this.repo = repo;
        this.restClient = RestClient.builder().baseUrl(catalogUrl).build();
    }

    @GetMapping
    public List<GameOrder> getAllOrders() {
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Map<String, Object> payload) {
        Long gameId = Long.valueOf(payload.get("gameId").toString());
        String email = payload.get("customerEmail").toString();

        Map game = restClient.get()
                .uri("/api/games/{id}", gameId)
                .retrieve()
                .body(Map.class);

        if (game == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Game not found in catalog"));
        }

        String title = game.get("title").toString();
        BigDecimal price = new BigDecimal(game.get("price").toString());

        GameOrder saved = repo.save(new GameOrder(gameId, title, email, price));
        return ResponseEntity.ok(saved);
    }
}
