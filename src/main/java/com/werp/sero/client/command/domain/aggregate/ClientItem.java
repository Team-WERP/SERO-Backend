package com.werp.sero.client.command.domain.aggregate;

import com.werp.sero.material.command.domain.aggregate.Material;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Table(name = "client_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ClientItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "contract_price", nullable = false, columnDefinition = "int default 0")
    private int contractPrice;

    @Column(nullable = false, columnDefinition = "varchar(100) default 'TRADE_NORMAL'")
    private String status;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Material material;

    @Builder
    public ClientItem(Client client, Material material, int contractPrice) {
        this.client = client;
        this.material = material;
        this.contractPrice = contractPrice;
        this.status = "TRADE_NORMAL";
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void updateContractPrice(int newPrice) {
        this.contractPrice = newPrice;
        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}