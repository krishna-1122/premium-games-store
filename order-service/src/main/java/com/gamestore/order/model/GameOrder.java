package com.gamestore.order.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "game_orders")
public class GameOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gameId;
    private String gameTitle;
    private String username;
    private BigDecimal amountPaid;
    private String promoApplied;
    private String activationKey;
    private String status;
    private LocalDateTime orderedAt;

    public GameOrder() {}

    public GameOrder(Long gameId, String gameTitle, String username, BigDecimal amountPaid, String promoApplied) {
        this.gameId = gameId;
        this.gameTitle = gameTitle;
        this.username = username;
        this.amountPaid = amountPaid;
        this.promoApplied = (promoApplied == null || promoApplied.isBlank()) ? "NONE" : promoApplied.toUpperCase();
        this.activationKey = "NXV-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        this.status = "COMPLETED";
        this.orderedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getGameId() { return gameId; }
    public String getGameTitle() { return gameTitle; }
    public String getUsername() { return username; }
    public BigDecimal getAmountPaid() { return amountPaid; }
    public String getPromoApplied() { return promoApplied; }
    public String getActivationKey() { return activationKey; }
    public String getStatus() { return status; }
    public LocalDateTime getOrderedAt() { return orderedAt; }
}
