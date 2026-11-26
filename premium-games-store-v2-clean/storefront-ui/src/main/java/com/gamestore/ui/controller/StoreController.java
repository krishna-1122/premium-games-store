package com.gamestore.ui.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
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
    private final RestClient reviewClient;

    public StoreController(
            @Value("${services.catalog.url}") String catalogUrl,
            @Value("${services.order.url}") String orderUrl,
            @Value("${services.review.url}") String reviewUrl) {
        this.catalogClient = RestClient.builder().baseUrl(catalogUrl).build();
        this.orderClient = RestClient.builder().baseUrl(orderUrl).build();
        this.reviewClient = RestClient.builder().baseUrl(reviewUrl).build();
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false, defaultValue = "ALL") String genre,
                       Authentication auth,
                       Model model) {
        String currentUser = auth.getName();

        List<?> games = catalogClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/games").queryParam("genre", genre).build())
                .retrieve()
                .body(List.class);

        List<?> orders = orderClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/orders").queryParam("username", currentUser).build())
                .retrieve()
                .body(List.class);

        List<?> reviews = reviewClient.get()
                .uri("/api/reviews")
                .retrieve()
                .body(List.class);

        model.addAttribute("username", currentUser);
        model.addAttribute("selectedGenre", genre);
        model.addAttribute("games", games);
        model.addAttribute("orders", orders);
        model.addAttribute("reviews", reviews);
        return "index";
    }

    @PostMapping("/buy")
    public String buyGame(@RequestParam Long gameId,
                          @RequestParam(required = false, defaultValue = "") String promoCode,
                          Authentication auth) {
        orderClient.post()
                .uri("/api/orders")
                .body(Map.of(
                        "gameId", gameId,
                        "username", auth.getName(),
                        "promoCode", promoCode
                ))
                .retrieve()
                .toBodilessEntity();
        return "redirect:/";
    }

    @PostMapping("/review")
    public String postReview(@RequestParam String gameTitle,
                             @RequestParam Integer stars,
                             @RequestParam String comment,
                             Authentication auth) {
        reviewClient.post()
                .uri("/api/reviews")
                .body(Map.of(
                        "gameTitle", gameTitle,
                        "reviewer", auth.getName(),
                        "stars", stars,
                        "comment", comment
                ))
                .retrieve()
                .toBodilessEntity();
        return "redirect:/";
    }
}
