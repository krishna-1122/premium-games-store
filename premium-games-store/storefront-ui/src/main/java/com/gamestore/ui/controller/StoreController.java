package com.gamestore.ui.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Controller
public class StoreController {

    private final RestClient catalogClient;
    private final RestClient orderClient;

    public StoreController(
            @Value("${services.catalog.url}") String catalogUrl,
            @Value("${services.order.url}") String orderUrl) {
        this.catalogClient = RestClient.builder().baseUrl(catalogUrl).build();
        this.orderClient = RestClient.builder().baseUrl(orderUrl).build();
    }

    @GetMapping("/")
    public String home(Model model) {
        List<?> games = catalogClient.get().uri("/api/games").retrieve().body(List.class);
        List<?> orders = orderClient.get().uri("/api/orders").retrieve().body(List.class);
        model.addAttribute("games", games);
        model.addAttribute("orders", orders);
        return "index";
    }

    @PostMapping("/buy")
    public String buyGame(@RequestParam Long gameId, @RequestParam String customerEmail) {
        orderClient.post()
                .uri("/api/orders")
                .body(Map.of("gameId", gameId, "customerEmail", customerEmail))
                .retrieve()
                .toBodilessEntity();
        return "redirect:/";
    }
}
