package com.werp.sero.shipping.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "delivery")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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

    public void startDelivery() {
        this.status = "SHIP_ING";
        this.departedAt = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void completeDelivery() {
        this.status = "SHIP_DONE";
        this.arrivedAt = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}