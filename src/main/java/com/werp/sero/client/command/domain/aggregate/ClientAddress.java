package com.werp.sero.client.command.domain.aggregate;

import com.werp.sero.client.command.domain.repository.ClientAddressCommandRepository;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Table(name = "client_address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Builder
public class ClientAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(columnDefinition = "decimal(10,7)")
    private Double latitude;

    @Column(columnDefinition = "decimal(10,7)")
    private Double longitude;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "recipient_contact", nullable = false)
    private String recipientContact;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public void update(String name, String address, String recipientName,
                       String recipientContact, boolean isDefault, String updatedAt) {
        this.name = name;
        this.address = address;
        this.recipientName = recipientName;
        this.recipientContact = recipientContact;
        this.isDefault = isDefault;
        this.updatedAt = updatedAt;
    }

    // 기본 배송지 해제 (다른 배송지가 기본 배송지 체크 되었을때)
    public void unsetDefault() {
        this.isDefault = false;
    }

}