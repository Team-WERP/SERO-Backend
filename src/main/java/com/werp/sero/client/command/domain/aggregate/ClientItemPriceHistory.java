package com.werp.sero.client.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Table(name = "client_item_price_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ClientItemPriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "unit_price", nullable = false)
    private int unitPrice;

    @Column(name = "contract_price", nullable = false)
    private int contractPrice;

    @Column(nullable = false, length = 255)
    private String reason;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", nullable = false, length = 30)
    private String createdAt;

    @Column(name = "creator_id", nullable = false)
    private int creatorId;

    @Column(name = "client_id", nullable = false)
    private int clientId;

    @Column(name = "client_item_id", nullable = false)
    private int clientItemId;

    @Builder
    public ClientItemPriceHistory(int unitPrice, int contractPrice, String reason,
                                 String name, String status, int creatorId,
                                 int clientId, int clientItemId) {
        this.unitPrice = unitPrice;
        this.contractPrice = contractPrice;
        this.reason = reason;
        this.name = name;
        this.status = status;
        this.creatorId = creatorId;
        this.clientId = clientId;
        this.clientItemId = clientItemId;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}