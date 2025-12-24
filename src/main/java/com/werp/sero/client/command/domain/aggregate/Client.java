package com.werp.sero.client.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "client")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "ceo_name", nullable = false)
    private String ceoName;

    @Column(name = "business_no", nullable = false, unique = true)
    private String businessNo;

    @Column(name = "business_type", nullable = false)
    private String businessType;

    @Column(name = "business_item", nullable = false)
    private String businessItem;

    @Column(nullable = false)
    private String address;

    @Column(name = "manager_name", nullable = false)
    private String managerName;

    @Column(name = "manager_contact", nullable = false)
    private String managerContact;

    @Column(name = "manager_email", nullable = false, unique = true)
    private String managerEmail;

    @Column(name = "credit_limit", nullable = false, columnDefinition = "bigint default 0")
    private long creditLimit;

    @Column(nullable = false)
    private long receivables;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    public void addReceivables(long orderAmount) {
        this.receivables += orderAmount;
    }
}