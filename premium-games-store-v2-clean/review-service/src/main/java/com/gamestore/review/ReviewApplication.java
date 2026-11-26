package com.gamestore.review;

import com.gamestore.review.model.Review;
import com.gamestore.review.repository.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewApplication.class, args);
    }

    @Bean
    CommandLineRunner seedReviews(ReviewRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.saveAll(List.of(
                    new Review("Cyberpunk 2077: Phantom Liberty", "krishna", 5, "Dogtown storyline and combat overhaul are peak next-gen RPG!"),
                    new Review("Elden Ring: Shadow of the Erdtree", "pro_gamer", 5, "Messmer boss fight is brutal and unforgettable. 10/10 masterpiece."),
                    new Review("Black Myth: Wukong", "admin", 5, "Unreal Engine 5 visuals run silky smooth and boss designs are insane.")
                ));
            }
        };
    }
}
