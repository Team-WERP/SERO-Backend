package com.werp.sero.client.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "client_item")
@NoArgsConstructor
@Entity
public class ClientItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "contract_price", nullable = false, columnDefinition = "int default 0")
    private int contractPrice;

    @Column(nullable = false, columnDefinition = "VARCHAR(100) default 'TRADE_NORMAL'")
    private String status;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}