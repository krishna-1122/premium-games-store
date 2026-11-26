package com.gamestore.order.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_orders")
public class GameOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gameId;
    private String gameTitle;
    private String customerEmail;
    private BigDecimal amountPaid;
    private String status;
    private LocalDateTime orderedAt;

    public GameOrder() {}

    public GameOrder(Long gameId, String gameTitle, String customerEmail, BigDecimal amountPaid) {
        this.gameId = gameId;
        this.gameTitle = gameTitle;
        this.customerEmail = customerEmail;
        this.amountPaid = amountPaid;
        this.status = "COMPLETED";
        this.orderedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getGameId() { return gameId; }
    public String getGameTitle() { return gameTitle; }
    public String getCustomerEmail() { return customerEmail; }
    public BigDecimal getAmountPaid() { return amountPaid; }
    public String getStatus() { return status; }
    public LocalDateTime getOrderedAt() { return orderedAt; }
}
