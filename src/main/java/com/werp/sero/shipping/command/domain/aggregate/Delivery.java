package com.werp.sero.shipping.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "delivery")
@NoArgsConstructor
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tracking_number", nullable = false, unique = true)
    private String trackingNumber;

    @Column(name = "driver_name", nullable = false)
    private String driverName;

    @Column(name = "driver_contact", nullable = false)
    private String driverContact;

    @Column(nullable = false, columnDefinition = "varchar(100) default 'SHIP_ISSUED'")
    private String status;

    @Column(name = "departed_at")
    private String departedAt;

    @Column(name = "arrived_at")
    private String arrivedAt;

    @Column(name = "so_code", nullable = false)
    private String soCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gi_id", nullable = false)
    private GoodsIssue goodsIssue;
}