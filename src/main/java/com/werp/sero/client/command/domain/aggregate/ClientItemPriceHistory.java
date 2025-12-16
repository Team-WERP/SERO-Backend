package com.werp.sero.client.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "client_item_price_history")
@NoArgsConstructor
@Entity
public class ClientItemPriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "unit_price", nullable = false)
    private int unitPrice;

    @Column(name = "contract_price", nullable = false)
    private int contract_price;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "creator_id", nullable = false)
    private int creatorId;

    @Column(name = "client_id", nullable = false)
    private int clientId;

    @Column(name = "client_item_id", nullable = false)
    private int clientItemId;
}