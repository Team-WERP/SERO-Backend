package com.werp.sero.delivery.command.domain.aggregate;

import com.werp.sero.delivery.exception.UnauthorizedDeliveryUpdateException;
import com.werp.sero.shipping.command.domain.aggregate.GoodsIssue;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Table(name = "delivery")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    @Builder
    private Delivery(String trackingNumber,String driverName, String driverContact,
                     String status, String departedAt, String arrivedAt, String soCode) {

        this.trackingNumber = trackingNumber;
        this.driverName = driverName;
        this.driverContact = driverContact;
        this.status = status;
        this.departedAt = departedAt;
        this.arrivedAt = arrivedAt;
        this.soCode = soCode;
    }

    public void startDelivery() {
        this.status = "SHIP_ING";
        this.departedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void completeDelivery() {
        this.status = "SHIP_DONE";
        this.departedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}