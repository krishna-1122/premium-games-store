package com.gamestore.review.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gameTitle;
    private String reviewer;
    private Integer stars;
    private String comment;
    private LocalDateTime createdAt;

    public Review() {}

    public Review(String gameTitle, String reviewer, Integer stars, String comment) {
        this.gameTitle = gameTitle;
        this.reviewer = reviewer;
        this.stars = stars;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getGameTitle() { return gameTitle; }
    public String getReviewer() { return reviewer; }
    public Integer getStars() { return stars; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
