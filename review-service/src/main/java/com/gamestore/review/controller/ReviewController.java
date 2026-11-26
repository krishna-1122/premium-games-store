package com.gamestore.review.controller;

import com.gamestore.review.model.Review;
import com.gamestore.review.repository.ReviewRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewRepository repo;

    public ReviewController(ReviewRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return repo.findAllByOrderByCreatedAtDesc();
    }

    @PostMapping
    public Review addReview(@RequestBody Map<String, Object> payload) {
        String gameTitle = payload.get("gameTitle").toString();
        String reviewer = payload.get("reviewer").toString();
        Integer stars = Integer.valueOf(payload.get("stars").toString());
        String comment = payload.get("comment").toString();
        return repo.save(new Review(gameTitle, reviewer, stars, comment));
    }
}
